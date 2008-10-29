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

import org.apache.commons.logging.Log;

import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.ArrayList;

/**
 * Really the cache state (we need it because of the clear cache consistency).
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
class CacheState {

  private final Log log;
  private final ConcurrentFIFOExoCache config;
  final ConcurrentHashMap<Serializable, ObjectRef> map;
  private final Item head;
  private final Item tail;
  volatile int queueSize; // The queue size cached (which can be an estimate)
  private final Lock queueLock = new ReentrantLock();
  private volatile AtomicBoolean trimming = new AtomicBoolean();

  CacheState(ConcurrentFIFOExoCache config, Log log) {
    this.log = log;
    this.config = config;
    this.head = new Item();
    this.tail = new Item();
    this.map = new ConcurrentHashMap<Serializable, ObjectRef>();
    this.queueSize = 0;

    //
    head.next = tail;
    tail.previous = head;

    //
    if (isTraceEnabled()) {
      trace("Queue initialized with first=" + head.serial + " and last=" + tail.serial);
    }
  }

  public void assertConsistency() {
    int cachedQueueSize = queueSize;
    int effectiveQueueSize = 0;
    for (Item item = head.next;item != tail;item = item.next) {
      effectiveQueueSize++;
    }

    //
    if (effectiveQueueSize != cachedQueueSize) {
      throw new AssertionError("The cached queue size " + cachedQueueSize + "  is different from the effective queue size" + effectiveQueueSize);
    }

    //
    int mapSize = map.size();
    if (effectiveQueueSize != mapSize) {
      throw new AssertionError("The map size is " + mapSize + " is different from the queue size " + effectiveQueueSize);
    }
  }

  public Object get(Serializable name) {
    ObjectRef entry = map.get(name);
    if (entry != null) {
      Object o = entry.getObject();
      if (entry.isValid()) {
        config.hits++;
        config.onGet(name, o);
        return o;
      } else {
        config.misses++;
        if (map.remove(name, entry)) {
          remove(entry);
        }
        config.onExpire(name, o);
      }
    }
    return null;
  }

  private boolean isTraceEnabled() {
    return log != null && log.isTraceEnabled();
  }

  private void trace(String message) {
    log.trace(message + " [" + Thread.currentThread().getName() + "]");
  }

  /**
   * Attempt to remove an item from the queue.
   *
   * @param item the item to remove
   * @return true if the item was removed by this thread
   */
  private boolean remove(Item item) {
    boolean trace = isTraceEnabled();
    queueLock.lock();
    try {
      Item previous = item.previous;
      Item next = item.next;
      if (previous != null && next != null) {
        previous.next = next;
        next.previous = previous;
        item.previous = null;
        item.next = null;
        int newSize = --queueSize;
        if (trace) {
          trace("Removed item=" + item.serial + " with previous=" + previous.serial + " and next=" + next.serial +
            " with queue=" + newSize + "");
        }
        return true;
      } else {
        if (trace) {
          trace("Attempt to remove item=" + item.serial + " concurrently removed");
        }
        return false;
      }
    }
    finally {
      queueLock.unlock();
    }
  }

  /**
   * Add the item to the head of the list.
   *
   * @param item the item to add
   */
  private void add(Item item) {
    queueLock.lock();
    try {
      Item next = head.next;
      item.next = next;
      next.previous = item;
      head.next = item;
      item.previous = head;
      int newSize = ++queueSize;
      if (isTraceEnabled()) {
        trace("Added item=" + item.serial + " with next=" + next.serial + " and queue=" + newSize);
      }
    }
    finally {
      queueLock.unlock();
    }
  }

  /**
   * Attempt to trim the queue. Trim will occur if no other thread is already performing a trim
   * and the queue size is greater than the provided size.
   *
   * @param size the wanted size
   * @return the list of evicted items
   */
  private ArrayList<Item> trim(int size) {
    if (trimming.compareAndSet(false, true)) {
      try {
        queueLock.lock();
        try {
          if (queueSize > size) {
            ArrayList<Item> evictedItems = new ArrayList<Item>(queueSize - size);
            while (queueSize > size) {
              Item last = tail.previous;
              remove(last);
              evictedItems.add(last);
            }
            return evictedItems;
          }
        }
        finally {
          queueLock.unlock();
        }
      } finally {
        trimming.set(false);
      }
    }

    //
    return null;
  }

  /**
   * Do a put with the provided expiration time.
   *
   * @param expirationTime the expiration time
   * @param name the cache key
   * @param obj the cached value
   */
  void put(long expirationTime, Serializable name, Object obj) {
    boolean trace = isTraceEnabled();
    ObjectRef nextRef = new SimpleObjectRef(expirationTime, name, obj);
    ObjectRef previousRef = map.put(name, nextRef);

    // Remove previous (promoted as first element)
    if (previousRef != null) {
      remove(previousRef);
      if (trace) {
        trace("Replaced item=" + previousRef.serial + " with item=" + nextRef.serial + " in the map");
      }
    } else if (trace) {
      trace("Added item=" + nextRef.serial + " to map");
    }

    // Add to the queue
    add(nextRef);

    // Perform eviction from queue
    ArrayList<Item> evictedRefs = trim(config.maxSize);
    if (evictedRefs != null) {
      for (int i = 0;i < evictedRefs.size();i++) {
        ObjectRef evictedRef = (ObjectRef)evictedRefs.get(i);

        // We remove it from the map only if it was the same entry
        // it could have been removed concurrently by an explicit remove
        // or by a promotion
        map.remove(evictedRef.name, evictedRef);

        // Expiration callback
        config.onExpire(evictedRef.name, evictedRef.getObject());
      }
    }

    // Put callback
    config.onPut(name, obj);
  }

  public Object remove(Serializable name) {
    boolean trace = isTraceEnabled();
    ObjectRef item = map.remove(name);
    if (item != null) {
      if (trace) {
        trace("Removed item=" + item.serial + " from the map going to remove it");
      }
      boolean removed = remove(item);
      boolean valid = removed && item.isValid();
      Object object = item.getObject();
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
