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
  final Queue<ObjectRef> queue;

  CacheState(ConcurrentFIFOExoCache config, Log log) {
    this.log = log;
    this.config = config;
    this.map = new ConcurrentHashMap<Serializable, ObjectRef>();
    this.queue = new SynchronizedQueue<ObjectRef>(log);
  }

  public void assertConsistency() {
    if (queue instanceof SynchronizedQueue) {
      ((SynchronizedQueue)queue).assertConsistency();
    }
    int mapSize = map.size();
    int effectiveQueueSize  = queue.size();
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
          queue.remove(entry);
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
      queue.remove(previousRef);
      if (trace) {
        trace("Replaced item=" + previousRef.serial + " with item=" + nextRef.serial + " in the map");
      }
    } else if (trace) {
      trace("Added item=" + nextRef.serial + " to map");
    }

    // Add to the queue
    queue.add(nextRef);

    // Perform eviction from queue
    ArrayList<ObjectRef> evictedRefs = queue.trim(config.maxSize);
    if (evictedRefs != null) {
      for (ObjectRef evictedRef : evictedRefs) {
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
      boolean removed = queue.remove(item);
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
