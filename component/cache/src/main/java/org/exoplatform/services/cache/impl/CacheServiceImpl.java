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
import org.exoplatform.services.cache.SimpleExoCache;

/**
 * Created by The eXo Platform SAS. Author : Tuan Nguyen
 * tuan08@users.sourceforge.net Sat, Sep 13, 2003 @ Time: 1:12:22 PM
 */
public class CacheServiceImpl implements CacheService {
  private HashMap<String, ExoCacheConfig> configs_  = new HashMap<String, ExoCacheConfig>();

  private HashMap<String, ExoCache>       cacheMap_ = new HashMap<String, ExoCache>();

  private ExoCacheConfig                  defaultConfig_;

  private DistributedCacheListener        distrbutedListener_;
  
  private LoggingCacheListener            loggingListener_;

  public CacheServiceImpl(InitParams params) throws Exception {
    List configs = params.getObjectParamValues(ExoCacheConfig.class);
    for (int i = 0; i < configs.size(); i++) {
      ExoCacheConfig config = (ExoCacheConfig) configs.get(i);
      configs_.put(config.getName(), config);
    }
    defaultConfig_ = configs_.get("default");
    loggingListener_ = new LoggingCacheListener();
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

  public ExoCache getCacheInstance(String region) throws Exception {
    if (region == null || region.length() == 0) {
      throw new Exception("region cannot be empty");
    }
    ExoCache cache = cacheMap_.get(region);
    if (cache == null) {
      synchronized (cacheMap_) {
        ExoCacheConfig config = configs_.get(region);
        if (config == null)
          config = defaultConfig_;
        cache = createCacheInstance(region);
        cacheMap_.put(region, cache);
      }
    }
    return cache;
  }

  synchronized private ExoCache createCacheInstance(String region) throws Exception {
    ExoCacheConfig config = configs_.get(region);
    if (config == null)
      config = defaultConfig_;
    ExoCache simple = null;
    if (config.getImplementation() == null) {
      simple = new SimpleExoCache();
    } else {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      Class clazz = cl.loadClass(config.getImplementation());
      simple = (ExoCache) clazz.newInstance();
    }
    simple.setName(region);
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

  public Collection getAllCacheInstances() throws Exception {
    return cacheMap_.values();
  }

  synchronized public void synchronize(String region, Serializable key, Object value) throws Exception {
    BaseExoCache cache = (BaseExoCache) getCacheInstance(region);
    // S ystem.out.println("Synchonize key : " + key + " , value: " + value) ;
    if (key == null) { // invalidate all cache if key is null ;
      cache.localClear();
    } else if (value == null) { // remove the key if value is null ;
      cache.localRemove(key);
    } else { // replicate the data
      cache.localPut(key, value);
    }
  }
  

}
