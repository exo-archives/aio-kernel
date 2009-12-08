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
import org.exoplatform.services.cache.impl.jboss.lru.LRUExoCacheCreator.LRUExoCache;
import org.exoplatform.test.BasicTestCase;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * 21 juil. 2009  
 */
public class TestLRUCache extends BasicTestCase {

  CacheService service_;

  public TestLRUCache(String name) {
    super(name);
  }

  public void setUp() throws Exception {
    service_ = (CacheService) PortalContainer.getInstance()
                                             .getComponentInstanceOfType(CacheService.class);
  }
  
  public void testPolicy() throws Exception {
    testPolicy("test-lru");
    testPolicy("test-lru-with-old-config");
  }
  
  private void testPolicy(String cacheName) throws Exception {
    LRUExoCache cache = (LRUExoCache) service_.getCacheInstance(cacheName);
    cache.put("a", "a");
    cache.put("b", "a");
    cache.put("c", "a");
    cache.put("d", "a");
    assertEquals(4, cache.getCacheSize());
    cache.put("e", "a");
    assertEquals(5, cache.getCacheSize());
    cache.put("f", "a");
    assertEquals(6, cache.getCacheSize());
    Thread.sleep(500);
    assertEquals(6, cache.getCacheSize());
    Thread.sleep(600);
    assertEquals(5, cache.getCacheSize());
    Thread.sleep(1900);
    assertEquals(0, cache.getCacheSize());
    cache.setMaxSize(3);
    cache.setLiveTime(1);
    cache.setMaxAge(1);
    cache.put("a", "a");
    cache.put("b", "a");
    cache.put("c", "a");
    cache.put("d", "a");
    assertEquals(4, cache.getCacheSize());
    cache.put("e", "a");
    assertEquals(5, cache.getCacheSize());
    cache.put("f", "a");
    Thread.sleep(1100);
    assertEquals(3, cache.getCacheSize());
    Thread.sleep(1700);
    assertEquals(0, cache.getCacheSize());
  }
}
