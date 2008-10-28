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

import org.exoplatform.services.cache.ExoCache;
import org.exoplatform.services.cache.CachedObjectSelector;
import org.exoplatform.services.cache.CacheListener;

import java.io.Serializable;
import java.util.Map;
import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * An {@link org.exoplatform.services.cache.ExoCache} implementation based on {@link java.util.concurrent.ConcurrentHashMap}
 * that minimize locking. Cache entries are maintained in a fifo list that is used for the fifo eviction policy.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class ConcurrentFIFOExoCache implements ExoCache {

  private static int DEFAULT_MAX_SIZE = 50;

  private volatile long liveTimeMillis = -1;
  volatile int maxSize;
  private CacheListener[] listeners;
  private CacheState state;
  final AtomicInteger hits = new AtomicInteger(0);
  final AtomicInteger misses = new AtomicInteger(0);
  private String label;
  private String name;
  private boolean distributed = false;
  private boolean replicated = false;
  private boolean logEnabled = false;

  public ConcurrentFIFOExoCache() {
    this(DEFAULT_MAX_SIZE);
  }

  public ConcurrentFIFOExoCache(int maxSize) {
    this(null, maxSize);
  }

  public ConcurrentFIFOExoCache(String name, int maxSize) {
    this.maxSize = maxSize;
    this.name = name;
    this.state = new CacheState(this);
  }

  public String getName() {
    return name;
  }

  public void setName(String s) {
    name = s;
  }

  public String getLabel() {
    if (label == null) {
      if (name.length() > 30) {
        String shortLabel = name.substring(name.lastIndexOf(".") + 1);
        setLabel(shortLabel);
        return shortLabel;
      }
      return name;
    }
    return label;
  }

  public void setLabel(String name) {
    label = name;
  }

  public long getLiveTime() {
    return liveTimeMillis / 1000;
  }

  public void setLiveTime(long period) {
    this.liveTimeMillis = period >= 0 ? period * 1000 : -1;
  }

  public long getLiveTimeMillis() {
    return liveTimeMillis;
  }

  public void setLiveTimeMillis(long liveTimeMillis) {
    this.liveTimeMillis = liveTimeMillis;
  }

  public int getMaxSize() {
    return maxSize;
  }

  public void setMaxSize(int max) {
    this.maxSize = max;
  }

  public Object get(Serializable name) {
    return state.get(name);
  }

  public void put(Serializable name, Object obj) {
    long expirationTime = liveTimeMillis > 0 ? System.currentTimeMillis() + liveTimeMillis : Long.MAX_VALUE;
    state.put(expirationTime, name, obj);
  }

  public void putMap(Map<Serializable, Object> objs) {
    long expirationTime = liveTimeMillis > 0 ? System.currentTimeMillis() + liveTimeMillis : Long.MAX_VALUE;
    for (Map.Entry<Serializable, Object> entry : objs.entrySet()) {
      state.put(expirationTime, entry.getKey(), entry.getValue());
    }
  }

  public Object remove(Serializable name) {
    return state.remove(name);
  }

  public List getCachedObjects() {
    LinkedList<Object> list = new LinkedList<Object>();
    for (ObjectRef objectRef : state.map.values()) {
      Object object = objectRef.getObject();
      if (objectRef.isValid()) {
        list.add(object);
      }
    }
    return list;
  }

  public List removeCachedObjects() throws Exception {
    List list = getCachedObjects();
    clearCache();
    return list;
  }

  public void clearCache() throws Exception {
    state = new CacheState(this);
  }

  public void select(CachedObjectSelector selector) throws Exception {
    for (Map.Entry<Serializable, ObjectRef> entry : state.map.entrySet()) {
      Serializable key = entry.getKey();
      ObjectRef info = entry.getValue();
      if (selector.select(key, info)) {
        selector.onSelect(this, key, info);
      }
    }
  }

  public int getCacheSize() {
    return state.queueSize;
  }

  public int getCacheHit() {
    return hits.get();
  }

  public int getCacheMiss() {
    return misses.get();
  }

  public synchronized void addCacheListener(CacheListener listener) {
    if (listener == null) {
      return;
    }
    if (listeners == null) {
      listeners = new CacheListener[]{listener};
    } else {
      CacheListener[] tmp = new CacheListener[listeners.length + 1];
      System.arraycopy(listeners, 0, tmp, 0, listeners.length);
      tmp[listeners.length] = listener;
      listeners = tmp;
    }
  }

  public boolean isDistributed() {
    return distributed;
  }

  public void setDistributed(boolean distributed) {
    this.distributed = distributed;
  }

  public boolean isReplicated() {
    return replicated;
  }

  public void setReplicated(boolean replicated) {
    this.replicated = replicated;
  }

  public boolean isLogEnabled() {
    return logEnabled;
  }

  public void setLogEnabled(boolean logEnabled) {
    this.logEnabled = logEnabled;
  }

  //

  void onExpire(Serializable key, Object obj) {
    if (listeners == null)
      return;
    for (CacheListener listener : listeners)
      try {
        listener.onExpire(this, key, obj);
      }
      catch (Exception e) {
      }
  }

  void onRemove(Serializable key, Object obj) {
    if (listeners == null)
      return;
    for (CacheListener listener : listeners)
      try {
        listener.onRemove(this, key, obj);
      }
      catch (Exception e) {
      }
  }

  void onPut(Serializable key, Object obj) {
    if (listeners == null)
      return;
    for (CacheListener listener : listeners)
      try {
        listener.onPut(this, key, obj);
      }
      catch (Exception e) {
      }
  }

  void onGet(Serializable key, Object obj) {
    if (listeners == null)
      return;
    for (CacheListener listener : listeners)
      try {
        listener.onGet(this, key, obj);
      }
      catch (Exception e) {
      }
  }

  void onClearCache() {
    if (listeners == null)
      return;
    for (CacheListener listener : listeners)
      try {
        listener.onClearCache(this);
      }
      catch (Exception e) {
      }
  }
}