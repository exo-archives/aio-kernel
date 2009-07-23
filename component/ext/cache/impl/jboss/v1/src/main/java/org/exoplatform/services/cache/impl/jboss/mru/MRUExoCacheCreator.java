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
package org.exoplatform.services.cache.impl.jboss.mru;

import org.exoplatform.management.annotations.ManagedDescription;
import org.exoplatform.management.annotations.ManagedName;
import org.exoplatform.services.cache.ExoCache;
import org.exoplatform.services.cache.ExoCacheConfig;
import org.exoplatform.services.cache.ExoCacheInitException;
import org.exoplatform.services.cache.impl.jboss.AbstractExoCache;
import org.exoplatform.services.cache.impl.jboss.ExoCacheCreator;
import org.jboss.cache.TreeCache;
import org.jboss.cache.eviction.MRUConfiguration;
import org.jboss.cache.eviction.MRUPolicy;
import org.jboss.cache.eviction.RegionManager;
import org.jboss.cache.eviction.RegionNameConflictException;

/**
 * The MRU Implementation of an {@link org.exoplatform.services.cache.impl.jboss.ExoCacheCreator}
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * 21 juil. 2009  
 */
public class MRUExoCacheCreator implements ExoCacheCreator {
  
  /**
   * The expected implementation name
   */
  public static final String EXPECTED_IMPL = "MRU"; 

  /**
   * {@inheritDoc}
   */
  public ExoCache create(ExoCacheConfig config, TreeCache cache) throws ExoCacheInitException {
    if (config instanceof MRUExoCacheConfig) {
      final MRUExoCacheConfig mruConfig = (MRUExoCacheConfig) config;
      return create(config, cache, mruConfig.getMaxNodes());
    } else {
      return create(config, cache, config.getMaxSize());
    }
  }
  
  /**
   * Creates a new ExoCache instance with the relevant parameters
   */
  private ExoCache create(ExoCacheConfig config, TreeCache cache, int maxNodes) throws ExoCacheInitException {
    final RegionManager regionManager = cache.getEvictionRegionManager();
    final MRUConfiguration mru = new MRUConfiguration();
    mru.setMaxNodes(maxNodes);
    final MRUPolicy policy = new MRUPolicy();
    policy.configure(cache);   
    // Create an eviction region config
    try {
      regionManager.createRegion(RegionManager.DEFAULT_REGION, policy, mru);
    } catch (RegionNameConflictException e) {
      throw new ExoCacheInitException("Cannot create the region", e);
    }   
    
    return new AbstractExoCache(config, cache) {
      
      public void setMaxSize(int max) {
        mru.setMaxNodes(max);
      }
      
      public void setLiveTime(long period) {
        throw new UnsupportedOperationException();
      }
      
      @ManagedName("MaxNodes")
      @ManagedDescription("This is the maximum number of nodes allowed in this region. Any integer less than or equal to 0 will throw an exception when the policy provider is being configured for use.")
      public int getMaxSize() {
        return mru.getMaxNodes();
      }
      
      public long getLiveTime() {
        throw new UnsupportedOperationException();
      }
    };    
  }

  /**
   * {@inheritDoc}
   */  
  public Class<? extends ExoCacheConfig> getExpectedConfigType() {
    return MRUExoCacheConfig.class;
  }

  /**
   * {@inheritDoc}
   */  
  public String getExpectedImplementation() {
    return EXPECTED_IMPL;
  }
}
