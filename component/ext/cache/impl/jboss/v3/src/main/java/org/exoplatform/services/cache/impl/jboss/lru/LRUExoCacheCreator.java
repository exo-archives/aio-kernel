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

import java.io.Serializable;

import org.exoplatform.management.annotations.Managed;
import org.exoplatform.management.annotations.ManagedDescription;
import org.exoplatform.management.annotations.ManagedName;
import org.exoplatform.services.cache.ExoCache;
import org.exoplatform.services.cache.ExoCacheConfig;
import org.exoplatform.services.cache.ExoCacheInitException;
import org.exoplatform.services.cache.impl.jboss.AbstractExoCache;
import org.exoplatform.services.cache.impl.jboss.ExoCacheCreator;
import org.jboss.cache.Cache;
import org.jboss.cache.Fqn;
import org.jboss.cache.config.Configuration;
import org.jboss.cache.config.EvictionConfig;
import org.jboss.cache.config.EvictionRegionConfig;
import org.jboss.cache.eviction.LRUAlgorithmConfig;

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
   * The default value for the parameter timeToLive
   */
  private long defaultTimeToLive;
  
  /**
   * The default value for the parameter maxAge
   */
  private long defaultMaxAge;
  
  /**
   * {@inheritDoc}
   */
  public ExoCache create(ExoCacheConfig config, Cache<Serializable, Object> cache) throws ExoCacheInitException {
    if (config instanceof LRUExoCacheConfig) {
      final LRUExoCacheConfig lruConfig = (LRUExoCacheConfig) config;
      return create(config, cache, lruConfig.getMaxNodes(), lruConfig.getTimeToLive(), 
                    lruConfig.getMaxAge(), lruConfig.getMinTimeToLive());
    } else {
      final long period = config.getLiveTime();
      return create(config, cache, config.getMaxSize(), defaultTimeToLive, defaultMaxAge, period > 0 ? period * 1000 : 0);
    }
  }

  /**
   * Creates a new ExoCache instance with the relevant parameters
   */
  private ExoCache create(ExoCacheConfig config, Cache<Serializable, Object> cache, int maxNodes, long timeToLive, 
                          long maxAge, long minTimeToLive) throws ExoCacheInitException {
    final Configuration configuration = cache.getConfiguration();
    final LRUAlgorithmConfig lru = new LRUAlgorithmConfig(timeToLive, maxAge, maxNodes);
    lru.setMinTimeToLive(minTimeToLive);
    // Create an eviction region config
    final EvictionRegionConfig erc = new EvictionRegionConfig(Fqn.ROOT, lru);

    final EvictionConfig evictionConfig = configuration.getEvictionConfig();
    evictionConfig.setDefaultEvictionRegionConfig(erc);
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

    private final LRUAlgorithmConfig lru;
    
    public LRUExoCache(ExoCacheConfig config, Cache<Serializable, Object> cache, LRUAlgorithmConfig lru) {
      super(config, cache);
      this.lru = lru;
    }
    
    @ManagedName("MinTimeToLive")
    @ManagedDescription("the minimum amount of time a node must be allowed to live after being accessed before it is allowed to be considered for eviction. 0 denotes that this feature is disabled, which is the default value.")
    public long getLiveTime() {
      return lru.getMinTimeToLive();
    }

    @ManagedName("MaxNodes")
    @ManagedDescription("This is the maximum number of nodes allowed in this region. 0 denotes immediate expiry, -1 denotes no limit.")
    public int getMaxSize() {
      return lru.getMaxNodes();
    }

    @Managed
    @ManagedName("TimeToLive")
    @ManagedDescription("The amount of time a node is not written to or read (in milliseconds) before the node is swept away. 0 denotes immediate expiry, -1 denotes no limit.")    
    public long getTimeToLive() {
      return lru.getTimeToLive();
    }
    
    @Managed
    @ManagedName("MaxAges")
    @ManagedDescription("Lifespan of a node (in milliseconds) regardless of idle time before the node is swept away. 0 denotes immediate expiry, -1 denotes no limit.")    
    public long getMaxAge() {
      return lru.getMaxAge();
    }
    
    public void setLiveTime(long period) {
      lru.setMinTimeToLive(period);
    }

    public void setMaxSize(int max) {
      lru.setMaxNodes(max);
    }
    
    @Managed
    public void setTimeToLive(long timeToLive) {
      lru.setTimeToLive(timeToLive); 
    }
    
    @Managed
    public void setMaxAge(long maxAge) {
      lru.setMaxAge(maxAge);
    }
  }
}
