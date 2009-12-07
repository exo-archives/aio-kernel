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

import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.cache.CacheService;
import org.exoplatform.services.cache.ExoCache;
import org.exoplatform.services.cache.impl.jboss.TestExoCacheCreator.TestExoCache;
import org.exoplatform.test.BasicTestCase;
import org.jboss.cache.TreeCache;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * 20 juil. 2009  
 */
public class TestExoCacheFactoryImpl extends BasicTestCase {

  CacheService service_;

  public TestExoCacheFactoryImpl(String name) {
    super(name);
  }

  public void setUp() throws Exception {
    service_ = (CacheService) PortalContainer.getInstance()
                                             .getComponentInstanceOfType(CacheService.class);
  }
  
  public void testCacheFactory() {
    ExoCache cache = service_.getCacheInstance("myCache");
    assertTrue("expect an instance of AbstractExoCache", cache instanceof AbstractExoCache);
    AbstractExoCache aCache = (AbstractExoCache) cache;
    assertTrue("expect a local cache", aCache.cache.getCacheModeInternal() == TreeCache.LOCAL);
    aCache.cache.stop();
    cache = service_.getCacheInstance("cacheDistributed");
    assertTrue("expect an instance of AbstractExoCache", cache instanceof AbstractExoCache);
    aCache = (AbstractExoCache) cache;
    assertTrue("expect a distributed cache", aCache.cache.getCacheModeInternal() == TreeCache.REPL_SYNC);
    aCache.cache.stop();
    cache = service_.getCacheInstance("myCustomCache");
    assertTrue("expect an instance of AbstractExoCache", cache instanceof AbstractExoCache);
    aCache = (AbstractExoCache) cache;
    assertTrue("expect a local cache", aCache.cache.getCacheModeInternal() == TreeCache.REPL_SYNC);
    aCache.cache.stop();    
  }
  
  public void testExoCacheCreator() {
    ExoCache cache = service_.getCacheInstance("test-default-impl");
    assertTrue("expect an instance of AbstractExoCache", cache instanceof AbstractExoCache);
    AbstractExoCache aCache = (AbstractExoCache) cache;
    aCache.cache.stop(); 
    cache = service_.getCacheInstance("test-custom-impl-with-old-config");
    assertTrue("expect an instance of TestExoCache", cache instanceof TestExoCache);
    cache = service_.getCacheInstance("test-custom-impl-with-new-config");
    assertTrue("expect an instance of TestExoCache", cache instanceof TestExoCache);    
  }
}
