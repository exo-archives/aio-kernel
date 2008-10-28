/*
 * Copyright (C) 2003-2007 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package org.exoplatform.services.cache.concurrent;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Really the cache state (we need it because of the clear cache consistency).
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
class CacheState {

  private final ConcurrentFIFOExoCache config;
  final ConcurrentHashMap<Serializable, ObjectRef> map;
  private final Item first;
  private final Item last;
  volatile int queueSize; // The queue size cached (which can be an estimate)

  CacheState(ConcurrentFIFOExoCache config) {
    this.config = config;
    this.first = new Item();
    this.last = new Item();
    this.map = new ConcurrentHashMap<Serializable, ObjectRef>();
    this.queueSize = 0;

    //
    first.next = last;
    last.previous = first;
  }

  public Object get(Serializable name) {
    ObjectRef entry = map.get(name);
    if (entry != null) {
      Object o = entry.getObject();
      if (entry.isValid()) {
        config.hits.incrementAndGet();
        config.onGet(name, o);
        return o;
      } else {
        config.misses.incrementAndGet();
        if (map.remove(name, entry)) {
          remove(entry);
        }
        config.onExpire(name, o);
      }
    }
    return null;
  }

  /**
   * Attempt to remove an item from the queue.
   *
   * @param item the item to remove
   * @return true if the item was removed by this thread
   */
  private boolean remove(Item item) {
    Item previous;
    Item next;
    synchronized (item) {
      previous = item.previous;
      next = item.next;
    }

    //
    if (previous != null && next != null) {
      synchronized (previous) {
        synchronized (item) {
          synchronized (next) {
            if (item.previous == previous && item.next == next) {
              previous.next = next;
              next.previous = previous;
              item.previous = null;
              item.next = null;
              queueSize--;
              return true;
            }
          }
        }
      }
    }

    //
    return false;
  }

  /**
   * Add the item to the head of the list.
   *
   * @param item the item to add
   */
  private void add(Item item) {
    synchronized (first) {
      Item next = first.next;
      synchronized (next) {
        item.next = next;
        next.previous = item;
        first.next = item;
        item.previous = first;
        queueSize++;
      }
    }
  }

  /**
   * Attempt to remove the last item from the list
   *
   * @return the removed item or null if no item could be removed
   */
  private Item removeLast() {
    Item item = null;
    synchronized (last) {
      if (last.previous != first) {
        item = last.previous;
      }
    }

    //
    if (item != null && remove(item)) {
      return item;
    } else {
      return null;
    }
  }

  /**
   * Do a put with the provided expiration time.
   *
   * @param expirationTime the expiration time
   * @param name the cache key
   * @param obj the cached value
   */
  void put(long expirationTime, Serializable name, Object obj) {
    ObjectRef nextRef = new SimpleObjectRef(expirationTime, name, obj);
    ObjectRef previousRef = map.put(name, nextRef);

    // Remove previous (promoted as first element)
    if (previousRef != null) {
      remove(previousRef);
    }

    // Add to the queue
    add(nextRef);

    // Perform eviction from queue
    while (queueSize > config.maxSize) {
      ObjectRef evicted = (ObjectRef)removeLast();
      if (evicted != null) {

        // We remove it from the map only if it was the same entry
        map.remove(evicted.name, evicted);

        // Expiration callback
        config.onExpire(evicted.name, evicted.getObject());
      }
    }

    // Put callback
    config.onPut(name, obj);
  }

  public Object remove(Serializable name) {
    ObjectRef entry = map.remove(name);
    if (entry != null) {
      remove(entry);
      boolean valid = entry.isValid();
      Object object = entry.getObject();

      //
      if (valid) {
        config.onRemove(name, object);
        return object;
      } else {
        config.onExpire(name, object);
        return null;
      }
    } else {
      return null;
    }
  }
}
