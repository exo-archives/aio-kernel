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
package org.exoplatform.services.cache.impl.jboss.lfu;

import org.exoplatform.management.annotations.Managed;
import org.exoplatform.management.annotations.ManagedDescription;
import org.exoplatform.management.annotations.ManagedName;
import org.exoplatform.services.cache.ExoCache;
import org.exoplatform.services.cache.ExoCacheConfig;
import org.exoplatform.services.cache.ExoCacheInitException;
import org.exoplatform.services.cache.impl.jboss.AbstractExoCache;
import org.exoplatform.services.cache.impl.jboss.ExoCacheCreator;
import org.jboss.cache.TreeCache;
import org.jboss.cache.eviction.LFUConfiguration;
import org.jboss.cache.eviction.LFUPolicy;
import org.jboss.cache.eviction.RegionManager;
import org.jboss.cache.eviction.RegionNameConflictException;

/**
 * The LFU Implementation of an {@link org.exoplatform.services.cache.impl.jboss.ExoCacheCreator}
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * 21 juil. 2009  
 */
public class LFUExoCacheCreator implements ExoCacheCreator {
  
  /**
   * The expected implementation name
   */
  public static final String EXPECTED_IMPL = "LFU";
  
  /**
   * The default value for the parameter maxAge
   */
  protected int defaultMinNodes;
  
  /**
   * {@inheritDoc}
   */
  public ExoCache create(ExoCacheConfig config, TreeCache cache) throws ExoCacheInitException {
    if (config instanceof LFUExoCacheConfig) {
      final LFUExoCacheConfig lfuConfig = (LFUExoCacheConfig) config;
      return create(config, cache, lfuConfig.getMaxNodes(), lfuConfig.getMinNodes());
    } else {
      return create(config, cache, config.getMaxSize(), defaultMinNodes);
    }
  }

  /**
   * Creates a new ExoCache instance with the relevant parameters
   */
  private ExoCache create(ExoCacheConfig config, TreeCache cache, int maxNodes, int minNodes) throws ExoCacheInitException {
    final RegionManager regionManager = cache.getEvictionRegionManager();
    final LFUConfiguration lfu = new LFUConfiguration();
    lfu.setMaxNodes(maxNodes);
    lfu.setMinNodes(minNodes);
    final LFUPolicy policy = new LFUPolicy();
    policy.configure(cache);   
    // Create an eviction region config
    try {
      regionManager.createRegion(RegionManager.DEFAULT_REGION, policy, lfu);
    } catch (RegionNameConflictException e) {
      throw new ExoCacheInitException("Cannot create the region", e);
    }       

    return new LFUExoCache(config, cache, lfu);
  }

  /**
   * {@inheritDoc}
   */
  public Class<? extends ExoCacheConfig> getExpectedConfigType() {
    return LFUExoCacheConfig.class;
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
  public static class LFUExoCache extends AbstractExoCache {

    private final LFUConfiguration lfu;
    
    public LFUExoCache(ExoCacheConfig config, TreeCache cache, LFUConfiguration lfu) {
      super(config, cache);
      this.lfu = lfu;
    }
    
    public long getLiveTime() {
      throw new UnsupportedOperationException();
    }

    @ManagedName("MaxNodes")
    @ManagedDescription("This is the maximum number of nodes allowed in this region. A value of 0 for maxNodes means that there is no upper bound for the configured cache region.")
    public int getMaxSize() {
      return lfu.getMaxNodes();
    }
    
    @Managed
    @ManagedName("MinNodes")
    @ManagedDescription("This is the minimum number of nodes allowed in this region. This value determines what the eviction queue should prune down to per pass. e.g. If minNodes is 10 and the cache grows to 100 nodes, the cache is pruned down to the 10 most frequently used nodes when the eviction timer makes a pass through the eviction algorithm.")    
    public long getMinNodes() {
      return lfu.getMinNodes();
    }
    
    public void setLiveTime(long period) {
      throw new UnsupportedOperationException();
    }

    public void setMaxSize(int max) {
      lfu.setMaxNodes(max);
    }
    
    @Managed
    public void setMinNodes(int minNodes) {
      lfu.setMinNodes(minNodes);
    }
  }  
}
