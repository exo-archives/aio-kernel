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
package org.exoplatform.services.cache.impl.jboss.lru;

import org.exoplatform.management.annotations.Managed;
import org.exoplatform.management.annotations.ManagedDescription;
import org.exoplatform.management.annotations.ManagedName;
import org.exoplatform.services.cache.ExoCache;
import org.exoplatform.services.cache.ExoCacheConfig;
import org.exoplatform.services.cache.ExoCacheInitException;
import org.exoplatform.services.cache.impl.jboss.AbstractExoCache;
import org.exoplatform.services.cache.impl.jboss.ExoCacheCreator;
import org.jboss.cache.TreeCache;
import org.jboss.cache.eviction.LRUConfiguration;
import org.jboss.cache.eviction.LRUPolicy;
import org.jboss.cache.eviction.RegionManager;
import org.jboss.cache.eviction.RegionNameConflictException;

/**
 * The LRU Implementation of an {@link org.exoplatform.services.cache.impl.jboss.ExoCacheCreator}
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * 21 juil. 2009  
 */
public class LRUExoCacheCreator implements ExoCacheCreator {
  
  /**
   * The expected implementation name
   */
  public static final String EXPECTED_IMPL = "LRU";
  
  /**
   * The default value for the parameter maxAge in seconds
   */
  protected int defaultMaxAgeSeconds;
  
  /**
   * {@inheritDoc}
   */
  public ExoCache create(ExoCacheConfig config, TreeCache cache) throws ExoCacheInitException {
    if (config instanceof LRUExoCacheConfig) {
      final LRUExoCacheConfig lruConfig = (LRUExoCacheConfig) config;
      return create(config, cache, lruConfig.getMaxNodes(), lruConfig.getTimeToLiveSeconds(), 
                    lruConfig.getMaxAgeSeconds());
    } else {
      return create(config, cache, config.getMaxSize(), (int) config.getLiveTime(), defaultMaxAgeSeconds);
    }
  }

  /**
   * Creates a new ExoCache instance with the relevant parameters
   */
  private ExoCache create(ExoCacheConfig config, TreeCache cache, int maxNodes, int timeToLiveSeconds, 
                          int maxAgeSeconds) throws ExoCacheInitException {
    final RegionManager regionManager = cache.getEvictionRegionManager();
    final LRUConfiguration lru = new LRUConfiguration();
    lru.setMaxNodes(maxNodes);
    lru.setTimeToLiveSeconds(timeToLiveSeconds);
    lru.setMaxAgeSeconds(maxAgeSeconds);
    final LRUPolicy policy = new LRUPolicy();
    policy.configure(cache);   
    // Create an eviction region config
    try {
      regionManager.createRegion(RegionManager.DEFAULT_REGION, policy, lru);
    } catch (RegionNameConflictException e) {
      throw new ExoCacheInitException("Cannot create the region", e);
    }
    return new LRUExoCache(config, cache, lru);
  }
  
  /**
   * {@inheritDoc}
   */
  public Class<? extends ExoCacheConfig> getExpectedConfigType() {
    return LRUExoCacheConfig.class;
  }

  /**
   * {@inheritDoc}
   */
  public String getExpectedImplementation() {
    return EXPECTED_IMPL;
  }
  
  /**
   * The LRU implementation of an ExoCache
   */
  public static class LRUExoCache extends AbstractExoCache {

    private final LRUConfiguration lru;
    
    public LRUExoCache(ExoCacheConfig config, TreeCache cache, LRUConfiguration lru) {
      super(config, cache);
      this.lru = lru;
    }
    
    @ManagedName("TimeToLiveSeconds")
    @ManagedDescription("Time to idle (in seconds) before the node is swept away. 0 denotes no limit.")
    public long getLiveTime() {
      return lru.getTimeToLiveSeconds();
    }

    @ManagedName("MaxNodes")
    @ManagedDescription("This is the maximum number of nodes allowed in this region. 0 denotes no limit.")
    public int getMaxSize() {
      return lru.getMaxNodes();
    }
    
    @Managed
    @ManagedName("MaxAgeSeconds")
    @ManagedDescription("Time an object should exist in TreeCache (in seconds) regardless of idle time before the node is swept away. 0 denotes no limit.")    
    public int getMaxAge() {
      return lru.getMaxAgeSeconds();
    }
    
    public void setLiveTime(long timeToLiveSeconds) {
      lru.setTimeToLiveSeconds((int) timeToLiveSeconds);
    }

    public void setMaxSize(int max) {
      lru.setMaxNodes(max);
    }
    
    @Managed
    public void setMaxAge(int maxAgeSeconds) {
      lru.setMaxAgeSeconds(maxAgeSeconds);
    }
  }
}
