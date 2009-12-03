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
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import javax.transaction.UserTransaction;

import org.apache.commons.logging.Log;
import org.exoplatform.services.cache.CacheListener;
import org.exoplatform.services.cache.CachedObjectSelector;
import org.exoplatform.services.cache.ExoCache;
import org.exoplatform.services.cache.ExoCacheConfig;
import org.exoplatform.services.cache.ObjectCacheInfo;
import org.exoplatform.services.log.ExoLogger;
import org.jboss.cache.AbstractTreeCacheListener;
import org.jboss.cache.CacheException;
import org.jboss.cache.Fqn;
import org.jboss.cache.Node;
import org.jboss.cache.TreeCache;
import org.jboss.cache.transaction.DummyTransactionManager;
import org.jboss.cache.transaction.DummyUserTransaction;
import org.jgroups.View;


/**
 * An {@link org.exoplatform.services.cache.ExoCache} implementation based on {@link org.jboss.cache.Node}.
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * 20 juil. 2009  
 */
public abstract class AbstractExoCache implements ExoCache {
  
  /**
   * Logger.
   */
  private static final Log LOG  = ExoLogger.getLogger(AbstractExoCache.class);
  
  private final AtomicInteger hits = new AtomicInteger(0);
  private final AtomicInteger misses = new AtomicInteger(0);
  private String label;
  private String name;
  private boolean distributed;
  private boolean replicated;
  private boolean logEnabled;
  
  private final CopyOnWriteArrayList<CacheListener> listeners;

  protected final TreeCache cache;
  
  public AbstractExoCache(ExoCacheConfig config, TreeCache cache) {
    this.cache = cache;
    this.listeners = new CopyOnWriteArrayList<CacheListener>();
    setDistributed(config.isDistributed());
    setLabel(config.getLabel());
    setName(config.getName());
    setLogEnabled(config.isLogEnabled());
    setReplicated(config.isRepicated());
    cache.addTreeCacheListener(new CacheEventListener());
  }
  
  /**
   * {@inheritDoc}
   */
  public void addCacheListener(CacheListener listener) {
    if (listener == null) {
      return;
    }
    listeners.add(listener);  
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public void clearCache() throws Exception {
    try {
      Set names = cache.getChildrenNames(Fqn.ROOT);
      if (names != null && !names.isEmpty()) {
        for (Object name : names) {
          Node node = cache.get(new Fqn(name));    
          if (node == null) {
            continue;
          }      
          remove(getKey(node));
        }      
      }
    } catch (Exception e) {
      LOG.warn("Cannot excecute clearCache properly", e);
    }    
    onClearCache();
  }

  /**
   * {@inheritDoc}
   */
  public Object get(Serializable name) throws Exception {
    final Object result = cache.get(new Fqn(name), name);
    if (result == null) {
      misses.incrementAndGet();
    } else {
      hits.incrementAndGet();
    }
    onGet(name, result);
    return result;
  }

  /**
   * {@inheritDoc}
   */
  public int getCacheHit() {
    return hits.get();
  }

  /**
   * {@inheritDoc}
   */
  public int getCacheMiss() {
    return misses.get();
  }

  /**
   * {@inheritDoc}
   */
  public int getCacheSize() {
    return cache.getNumberOfNodes();
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings("unchecked")
  public List<Object> getCachedObjects() {
    final LinkedList<Object> list = new LinkedList<Object>();
    try {
      Set names = cache.getChildrenNames(Fqn.ROOT);
      if (names == null || names.isEmpty()) {
        return list;
      }
      for (Object name : names) {
        Node node = cache.get(new Fqn(name));    
        if (node == null) {
          continue;
        }      
        final Object value = node.get(getKey(node));
        if (value != null) {
          list.add(value);        
        }
      }      
    } catch (Exception e) {
      LOG.warn("Cannot excecute getCachedObjects properly", e);
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
    putOnly(name, obj);
    onPut(name, obj);
  }

  /**
   * Only puts the data into the cache nothing more
   */
  private Object putOnly(Serializable name, Object obj) throws Exception {
    return cache.put(new Fqn(name), name, obj);
  }
  
  /**
   * {@inheritDoc}
   */
  public void putMap(Map<Serializable, Object> objs) throws Exception {
    UserTransaction tx = new DummyUserTransaction(DummyTransactionManager.getInstance());
    try {
      tx.begin();
      // Start transaction
      for (Entry<Serializable, Object> entry : objs.entrySet()) {
        putOnly(entry.getKey(), entry.getValue());      
      }
      tx.commit();
      // End transaction
      for (Entry<Serializable, Object> entry : objs.entrySet()) {
        onPut(entry.getKey(), entry.getValue());       
      }
    } catch (Exception e) {
      try { tx.rollback(); } catch(Throwable t) {}
      throw e;
    }
  }

  /**
   * {@inheritDoc}
   */
  public Object remove(Serializable name) throws Exception {
    final Fqn fqn = new Fqn(name);
    final Node node = cache.get(fqn);
    if (node != null) {
      final Object result = node.get(name);
      cache.remove(fqn);
      onRemove(name, result);
      return result;
    }
    return null;
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
  @SuppressWarnings("unchecked")
  public void select(CachedObjectSelector selector) throws Exception {
    Set names = cache.getChildrenNames(Fqn.ROOT);
    if (names == null || names.isEmpty()) {
      return;
    }
    for (Object name : names) {
      Node node = cache.get(new Fqn(name));
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
  
  /**
   * Returns the key related to the given node
   */
  private Serializable getKey(Node node) {
    return getKey(node.getFqn());
  }

  /**
   * Returns the key related to the given Fqn
   */  
  private Serializable getKey(Fqn fqn) {
    return (Serializable) fqn.get(0);
  }
  
  void onExpire(Serializable key, Object obj) {
    if (listeners.isEmpty()) {
      return;      
    }
    for (CacheListener listener : listeners) {
      try {
        listener.onExpire(this, key, obj);
      }
      catch (Exception e) {
        if (LOG.isWarnEnabled()) LOG.warn("Cannot execute the CacheListener properly", e);
      }
    }
  }

  void onRemove(Serializable key, Object obj) {
    if (listeners.isEmpty()) {
      return;      
    }
    for (CacheListener listener : listeners) {
      try {
        listener.onRemove(this, key, obj);
      }
      catch (Exception e) {
        if (LOG.isWarnEnabled()) LOG.warn("Cannot execute the CacheListener properly", e);
      }
    }
  }

  void onPut(Serializable key, Object obj) {
    if (listeners.isEmpty()) {
      return;      
    }
    for (CacheListener listener : listeners)
      try {
        listener.onPut(this, key, obj);
      }
      catch (Exception e) {
        if (LOG.isWarnEnabled()) LOG.warn("Cannot execute the CacheListener properly", e);
      }
  }

  void onGet(Serializable key, Object obj) {
    if (listeners.isEmpty()) {
      return;      
    }
    for (CacheListener listener : listeners)
      try {
        listener.onGet(this, key, obj);
      }
      catch (Exception e) {
        if (LOG.isWarnEnabled()) LOG.warn("Cannot execute the CacheListener properly", e);
      }
  }

  void onClearCache() {
    if (listeners.isEmpty()) {
      return;      
    }
    for (CacheListener listener : listeners)
      try {
        listener.onClearCache(this);
      }
      catch (Exception e) {
        if (LOG.isWarnEnabled()) LOG.warn("Cannot execute the CacheListener properly", e);
      }
  }
  
  public class CacheEventListener extends AbstractTreeCacheListener {
 
    public void cacheStarted(TreeCache cache) {}
    public void cacheStopped(TreeCache cache) {}
    
    public void nodeEvicted(Fqn fqn) {
      // Cannot give the value since
      // since it is not available
      onExpire(getKey(fqn), null);
    }    
    
    public void nodeRemoved(Fqn fqn) {}
    
    public void nodeRemove(Fqn fqn, boolean pre, boolean isLocal) {
      if (pre && !isLocal) {
        final Serializable key = getKey(fqn);
        Object value = null;
        try {
          value = cache.get(fqn, key);
        } catch (CacheException e) {
          // Ignore exception
        }
        onRemove(key, value);          
      }      
    }
    
    public void nodeCreated(Fqn fqn) {}    
    public void nodeLoaded(Fqn fqn) {}
    public void nodeVisited(Fqn fqn) {}
    
    public void nodeModified(Fqn fqn) {}
    
    public void nodeModify(Fqn fqn, boolean pre, boolean isLocal) {
      if (!isLocal && !pre) {
        final Serializable key = getKey(fqn);
        Object value = null;
        try {
          value = cache.get(fqn, key);
        } catch (CacheException e) {
          // Ignore exception
        }
        onPut(key, value);       
      }      
    }
    
    public void viewChange(View paramView) {}    
  }
}
