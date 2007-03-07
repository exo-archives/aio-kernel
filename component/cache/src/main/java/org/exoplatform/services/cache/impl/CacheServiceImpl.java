/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
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
 * Created by The eXo Platform SARL        .
 * Author : Tuan Nguyen
 *          tuan08@users.sourceforge.net
 * Sat, Sep 13, 2003 @  
 * Time: 1:12:22 PM
 */
public class CacheServiceImpl  implements CacheService {
  private HashMap<String, ExoCacheConfig>  configs_  = new HashMap<String, ExoCacheConfig>() ;
  private HashMap<String, ExoCache> cacheMap_ = new HashMap<String, ExoCache>(); 
  private ExoCacheConfig defaultConfig_ ;
  private DistributedCacheListener distrbutedListener_ ;
  
  public CacheServiceImpl(InitParams params) throws Exception {
    List configs = params.getObjectParamValues(ExoCacheConfig.class) ;    
    for(int i = 0 ; i  < configs.size(); i++) {
      ExoCacheConfig config = (ExoCacheConfig)configs.get(i) ;
      configs_.put(config.getName(), config) ;
    }
    defaultConfig_ = configs_.get("default") ;
  }
  
  public  void  addExoCacheConfig(ComponentPlugin plugin) {
    addExoCacheConfig((ExoCacheConfigPlugin) plugin) ;
  }
  
  public  void addExoCacheConfig(ExoCacheConfigPlugin plugin) {    
    List<ExoCacheConfig> configs = plugin.getConfigs() ;
    for(ExoCacheConfig config : configs) {        
      configs_.put(config.getName(), config) ;    
    }
  }
  
  public  void setDistributedCacheListener(DistributedCacheListener listener) {
    distrbutedListener_ =  listener ;
  }
  
  public ExoCache getCacheInstance(String region) throws Exception {
    if( region == null || region.length() == 0) {
      throw new Exception ("region cannot be empty"); 
    }
    ExoCache cache = cacheMap_.get(region) ; 
    if (cache == null) {
      synchronized (cacheMap_) {
        ExoCacheConfig config = configs_.get(region) ;
        if(config == null) config = defaultConfig_; 
        cache = createCacheInstance(region) ;
        cacheMap_.put(region, cache) ;
      }
    }
    return cache ;
  }
  
  synchronized private ExoCache createCacheInstance(String region) throws Exception {
    ExoCacheConfig config = configs_.get(region) ;
    if(config == null) config = defaultConfig_;
    ExoCache simple = null ;
    if(config.getImplementation() == null) {
      simple = new SimpleExoCache() ;
    }
    else {
      ClassLoader cl = Thread.currentThread().getContextClassLoader() ;
      Class clazz = cl.loadClass(config.getImplementation()) ;
      simple = (ExoCache) clazz.newInstance() ;
    }
    simple.setName(region) ;
    simple.setLabel(config.getLabel()) ;
    simple.setMaxSize(config.getMaxSize()) ;
    simple.setLiveTime(config.getLiveTime()) ;
    simple.setReplicated(config.isRepicated()) ;
    simple.setDistributed(config.isDistributed()) ;
    if(simple.isDistributed()) {
      simple.addCacheListener(distrbutedListener_) ;
    }
    return simple ;
  }
  
  public Collection getAllCacheInstances() throws Exception  {
    return cacheMap_.values() ;
  }
  
  synchronized public void synchronize(String region, Serializable key, Object value) throws Exception {    
    BaseExoCache cache = (BaseExoCache)getCacheInstance(region) ; 
//    S ystem.out.println("Synchonize key : " + key + " , value: " + value) ;
    if(key == null) { //invalidate all cache if key is null ;
      cache.localClear() ;
    } else if(value == null) { //remove the key if value is null ;
      cache.localRemove(key) ;
    } else { //replicate the data
      cache.localPut(key, value) ;
    }
  }
}

