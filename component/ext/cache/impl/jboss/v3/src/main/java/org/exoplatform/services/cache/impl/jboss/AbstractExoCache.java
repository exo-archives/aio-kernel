/*
 * Copyright (C) 2003-2009 eXo Platform SAS.
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
package org.exoplatform.services.cache.impl.jboss;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import org.exoplatform.services.cache.CacheListener;
import org.exoplatform.services.cache.CachedObjectSelector;
import org.exoplatform.services.cache.ExoCache;
import org.exoplatform.services.cache.ExoCacheConfig;
import org.exoplatform.services.cache.ObjectCacheInfo;
import org.jboss.cache.Cache;
import org.jboss.cache.Fqn;
import org.jboss.cache.Node;
import org.jboss.cache.notifications.annotation.NodeCreated;
import org.jboss.cache.notifications.annotation.NodeEvicted;
import org.jboss.cache.notifications.annotation.NodeRemoved;
import org.jboss.cache.notifications.event.NodeEvent;

/**
 * An {@link org.exoplatform.services.cache.ExoCache} implementation based on {@link org.jboss.cache.Node}.
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * 20 juil. 2009  
 */
@org.jboss.cache.notifications.annotation.CacheListener
public abstract class AbstractExoCache implements ExoCache {
  
  private final AtomicInteger size = new AtomicInteger();
  
  private volatile int hits;
  private volatile int misses;
  private String label;
  private String name;
  private boolean distributed;
  private boolean replicated;
  private boolean logEnabled;
  
  private ArrayList<CacheListener> listeners_;

  protected final Cache<Serializable, Object> cache;
  
  public AbstractExoCache(ExoCacheConfig config, Cache<Serializable, Object> cache) {
    this.cache = cache;
    setDistributed(config.isDistributed());
    setLabel(config.getLabel());
    setName(config.getName());
    setLogEnabled(config.isLogEnabled());
    setReplicated(config.isRepicated());
    cache.getConfiguration().setInvocationBatchingEnabled(true);
    cache.addCacheListener(new SizeManager());
  }
  
  /**
   * {@inheritDoc}
   */
  public synchronized void addCacheListener(CacheListener listener) {
    if (listener == null) {
      return;
    }
    if (listeners_ == null) {
      listeners_ = new ArrayList<CacheListener>();
    }
    listeners_.add(listener);  
  }

  /**
   * {@inheritDoc}
   */
  public void clearCache() throws Exception {
    final Node<Serializable, Object> rootNode = cache.getRoot();
    for (Node<Serializable, Object> node : rootNode.getChildren()) {
      if (node == null) {
        continue;
      }      
      remove(getKey(node));
    }
  }

  /**
   * {@inheritDoc}
   */
  public Object get(Serializable name) throws Exception {
    final Object result = cache.get(Fqn.fromElements(name), name);
    if (result == null) {
      misses++;
    } else {
      hits++;
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public int getCacheHit() {
    return hits;
  }

  /**
   * {@inheritDoc}
   */
  public int getCacheMiss() {
    return misses;
  }

  /**
   * {@inheritDoc}
   */
  public int getCacheSize() {
    return size.intValue();
  }

  /**
   * {@inheritDoc}
   */
  public List<Object> getCachedObjects() {
    final LinkedList<Object> list = new LinkedList<Object>();
    for (Node<Serializable, Object> node : cache.getRoot().getChildren()) {
      if (node == null) {
        continue;
      }      
      final Object value = node.get(getKey(node));
      if (value != null) {
        list.add(value);        
      }
    }
    return list;
  }

  /**
   * {@inheritDoc}
   */
  public String getLabel() {
    return label;
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {
    return name;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isDistributed() {
    return distributed;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isLogEnabled() {
    return logEnabled;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isReplicated() {
    return replicated;
  }

  /**
   * {@inheritDoc}
   */
  public void put(Serializable name, Object obj) throws Exception {
    final Object oldValue = cache.put(Fqn.fromElements(name), name, obj);
    if (oldValue == null) {
      size.incrementAndGet();      
    }
  }

  /**
   * {@inheritDoc}
   */
  public void putMap(Map<Serializable, Object> objs) throws Exception {
    cache.startBatch();
    int total = 0;
    try {
      for (Entry<Serializable, Object> entry : objs.entrySet()) {
        put(entry.getKey(), entry.getValue());
        total++;
      }
    } catch (Exception e) {
      cache.endBatch(false);
      size.addAndGet(-total);
      throw e;
    }
    cache.endBatch(true);
  }

  /**
   * {@inheritDoc}
   */
  public Object remove(Serializable name) throws Exception {
    final Fqn<Serializable> fqn = Fqn.fromElements(name);
    final Object result = cache.getNode(fqn);
    if (result != null && cache.removeNode(fqn)) {
      size.decrementAndGet();      
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public List<Object> removeCachedObjects() throws Exception {
    final List<Object> list = getCachedObjects();
    clearCache();
    return list;
  }

  /**
   * {@inheritDoc}
   */
  public void select(CachedObjectSelector selector) throws Exception {
    for (Node<Serializable, Object> node : cache.getRoot().getChildren()) {
      if (node == null) {
        continue;
      }      
      final Serializable key = getKey(node);
      final Object value = node.get(key); 
      ObjectCacheInfo info = new ObjectCacheInfo() {
        public Object get() {
          return value;
        }

        public long getExpireTime() {
          // Cannot know: The expire time is managed by JBoss Cache itself
          return -1;
        }
      };
      if (selector.select(key, info)) {
        selector.onSelect(this, key, info);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  public void setDistributed(boolean distributed) {
    this.distributed = distributed;
  }

  /**
   * {@inheritDoc}
   */
  public void setLabel(String label) {
    this.label = label;
  }

  /**
   * {@inheritDoc}
   */
  public void setLogEnabled(boolean logEnabled) {
    this.logEnabled = logEnabled;
  }

  /**
   * {@inheritDoc}
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * {@inheritDoc}
   */
  public void setReplicated(boolean replicated) {
    this.replicated = replicated;
  }
  
  private Serializable getKey(Node<Serializable, Object> node) {
    return (Serializable) node.getFqn().get(0);
  }
  
  @org.jboss.cache.notifications.annotation.CacheListener
  public class SizeManager {
 
    @NodeEvicted
    public void nodeEvicted(NodeEvent ne) {
      if (!ne.isPre()) {
        size.decrementAndGet();        
      }
    }    
    
    @NodeRemoved
    public void nodeRemoved(NodeEvent ne) {
      if (!ne.isPre() && !ne.isOriginLocal()) {
        size.decrementAndGet();        
      }
    }
    
    @NodeCreated
    public void nodeCreated(NodeEvent ne) {
      if (!ne.isPre() && !ne.isOriginLocal()) {
        size.incrementAndGet();        
      }
    }
  }
}
