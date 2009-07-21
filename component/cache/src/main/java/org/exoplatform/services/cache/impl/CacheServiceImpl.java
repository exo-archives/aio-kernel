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
package org.exoplatform.services.cache.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.exoplatform.container.component.ComponentPlugin;
import org.exoplatform.container.xml.InitParams;
import org.exoplatform.services.cache.BaseExoCache;
import org.exoplatform.services.cache.CacheService;
import org.exoplatform.services.cache.ExoCache;
import org.exoplatform.services.cache.ExoCacheConfig;
import org.exoplatform.services.cache.ExoCacheConfigPlugin;
import org.exoplatform.services.cache.ExoCacheFactory;
import org.exoplatform.services.cache.ExoCacheInitException;
import org.exoplatform.services.cache.SimpleExoCache;
import org.exoplatform.management.annotations.ManagedBy;

/**
 * Created by The eXo Platform SAS. Author : Tuan Nguyen
 * tuan08@users.sourceforge.net Sat, Sep 13, 2003 @ Time: 1:12:22 PM
 */
@ManagedBy(CacheServiceManaged.class)
public class CacheServiceImpl implements CacheService {
  private final HashMap<String, ExoCacheConfig> configs_  = new HashMap<String, ExoCacheConfig>();

  private final HashMap<String, ExoCache>       cacheMap_ = new HashMap<String, ExoCache>();

  private final ExoCacheConfig                  defaultConfig_;
  
  private final LoggingCacheListener            loggingListener_;
  
  private final ExoCacheFactory                 factory_;

  private DistributedCacheListener        distrbutedListener_;

  CacheServiceManaged managed;

  public CacheServiceImpl(InitParams params) throws Exception {
    this(params, null);
  }
  
  public CacheServiceImpl(InitParams params, ExoCacheFactory factory) throws Exception {
    List configs = params.getObjectParamValues(ExoCacheConfig.class);
    for (int i = 0; i < configs.size(); i++) {
      ExoCacheConfig config = (ExoCacheConfig) configs.get(i);
      configs_.put(config.getName(), config);
    }
    defaultConfig_ = configs_.get("default");
    loggingListener_ = new LoggingCacheListener();
    factory_ = factory == null ? new SimpleExoCacheFactory() : factory;
  }

  public void addExoCacheConfig(ComponentPlugin plugin) {
    addExoCacheConfig((ExoCacheConfigPlugin) plugin);
  }

  public void addExoCacheConfig(ExoCacheConfigPlugin plugin) {
    List<ExoCacheConfig> configs = plugin.getConfigs();
    for (ExoCacheConfig config : configs) {
      configs_.put(config.getName(), config);
    }
  }

  public void setDistributedCacheListener(DistributedCacheListener listener) {
    distrbutedListener_ = listener;
  }

  public ExoCache getCacheInstance(String region) {
    if (region == null) {
      throw new NullPointerException("region cannot be null");
    }
    if (region.length() == 0) {
      throw new IllegalArgumentException("region cannot be empty");
    }
    ExoCache cache = cacheMap_.get(region);
    if (cache == null) {
      synchronized (cacheMap_) {
        ExoCacheConfig config = configs_.get(region);
        if (config == null)
          config = defaultConfig_;
        try {
          cache = createCacheInstance(region);
          cacheMap_.put(region, cache);
        }
        catch (Exception e) {
          e.printStackTrace();
        }
      }
    }
    return cache;
  }

  synchronized private ExoCache createCacheInstance(String region) throws Exception {
    ExoCacheConfig config = configs_.get(region);
    if (config == null)
      config = defaultConfig_;
    // Ensure the configuration integrity
    final ExoCacheConfig safeConfig = config.clone();
    // Set the region as name 
    safeConfig.setName(region);
    final ExoCache simple = factory_.createCache(safeConfig);
    //
    if (managed != null) {
      managed.registerCache(simple);
    }

    //
    return simple;
  }

  public Collection getAllCacheInstances() {
    return cacheMap_.values();
  }

  synchronized public void synchronize(String region, Serializable key, Object value) throws Exception {
    BaseExoCache cache = (BaseExoCache) getCacheInstance(region);
    // System.out.println("Synchronize key : " + key + " , value: " + value) ;
    if (key == null) { // invalidate all cache if key is null ;
      cache.localClear();
    } else if (value == null) { // remove the key if value is null ;
      cache.localRemove(key);
    } else { // replicate the data
      cache.localPut(key, value);
    }
  }
  
  /**
   * Default implementation of an {@link org.exoplatform.services.cache.ExoCacheFactory}
   */
  private class SimpleExoCacheFactory implements ExoCacheFactory {
    
    /**
     * {@inheritDoc}
     */
    public ExoCache createCache(ExoCacheConfig config) throws ExoCacheInitException {
      final ExoCache simple = createCacheInstance(config);
      simple.setName(config.getName());
      simple.setLabel(config.getLabel());
      simple.setMaxSize(config.getMaxSize());
      simple.setLiveTime(config.getLiveTime());
      simple.setReplicated(config.isRepicated());
      simple.setDistributed(config.isDistributed());
      if (simple.isDistributed()) {
        simple.addCacheListener(distrbutedListener_);
      }
      simple.setLogEnabled(config.isLogEnabled());
      if (simple.isLogEnabled()) {
        simple.addCacheListener(loggingListener_);
      }
      return simple;
    }

    /**
     * Create a new instance of ExoCache according to the given configuration
     * @param config the ExoCache configuration
     * @return a new instance of ExoCache
     * @throws ExoCacheInitException if any exception happens while initializing the cache
     */
    @SuppressWarnings("unchecked")
    private ExoCache createCacheInstance(ExoCacheConfig config) throws ExoCacheInitException {
      if (config.getImplementation() == null) {
        // No implementation has been defined
        return new SimpleExoCache();
      } else {
        // An implementation has been defined
        final ClassLoader cl = Thread.currentThread().getContextClassLoader();
        try {
          final Class clazz = cl.loadClass(config.getImplementation());
          return (ExoCache) clazz.newInstance();
        } catch (Exception e) {
          throw new ExoCacheInitException("Cannot create instance of ExoCache of type " + config.getImplementation(), e);
        }
      }    
    }  
  }
}
