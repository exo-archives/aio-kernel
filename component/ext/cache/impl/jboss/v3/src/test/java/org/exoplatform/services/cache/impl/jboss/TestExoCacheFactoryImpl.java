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
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.cache.CacheService;
import org.exoplatform.services.cache.ExoCache;
import org.exoplatform.services.cache.ExoCacheConfig;
import org.exoplatform.services.cache.ExoCacheFactory;
import org.exoplatform.services.cache.impl.jboss.TestExoCacheCreator.TestExoCache;
import org.exoplatform.test.BasicTestCase;
import org.jboss.cache.config.Configuration.CacheMode;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * 20 juil. 2009  
 */
public class TestExoCacheFactoryImpl extends BasicTestCase {

  CacheService service_;
  ExoCacheFactory factory_;

  public TestExoCacheFactoryImpl(String name) {
    super(name);
  }

  public void setUp() throws Exception {
    service_ = (CacheService) PortalContainer.getInstance()
                                             .getComponentInstanceOfType(CacheService.class);
    factory_ = (ExoCacheFactory) PortalContainer.getInstance()
    .getComponentInstanceOfType(ExoCacheFactory.class);
  }
  
  public void testDistributedCache() throws Exception {
    ExoCacheConfig config = new ExoCacheConfig();
    config.setName("MyCacheReplicated");
    config.setMaxSize(5);
    config.setLiveTime(1000);
    config.setReplicated(true);
    ExoCacheConfig config2 = new ExoCacheConfig();
    config2.setName("MyCacheReplicated2");
    config2.setMaxSize(5);
    config2.setLiveTime(1000);
    config2.setReplicated(true);    
    ExoCache cache1 = factory_.createCache(config);
    ExoCache cache2 = factory_.createCache(config);
    ExoCache cache3 = factory_.createCache(config2);
    cache1.put("a", "b");
    assertEquals(1, cache1.getCacheSize());
    assertEquals("b", cache2.get("a"));
    assertEquals(1, cache2.getCacheSize());
    assertEquals(0, cache3.getCacheSize());
    cache2.put("b", "c");
    assertEquals(2, cache1.getCacheSize());
    assertEquals(2, cache2.getCacheSize());
    assertEquals("c", cache2.get("b"));
    assertEquals(0, cache3.getCacheSize());
    cache3.put("c", "d");
    assertEquals(2, cache1.getCacheSize());
    assertEquals(2, cache2.getCacheSize());
    assertEquals(1, cache3.getCacheSize());
    assertEquals("d", cache3.get("c"));
    cache2.put("a", "a");
    assertEquals(2, cache1.getCacheSize());
    assertEquals(2, cache2.getCacheSize());
    assertEquals("a", cache1.get("a"));
    cache2.remove("a");
    assertEquals(1, cache1.getCacheSize());
    assertEquals(1, cache2.getCacheSize());   
    cache1.clearCache();
    assertEquals(0, cache1.getCacheSize());
    assertEquals(null, cache2.get("b"));
    assertEquals(0, cache2.getCacheSize());
    Map<Serializable, Object> values = new HashMap<Serializable, Object>();
    values.put("a", "a");
    values.put("b", "b");
    cache1.putMap(values);
    assertEquals(2, cache1.getCacheSize());
    assertEquals(2, cache2.getCacheSize());
    values = new HashMap<Serializable, Object>() {
      public Set<Entry<Serializable, Object>> entrySet() {
        Set<Entry<Serializable, Object>> set = new LinkedHashSet<Entry<Serializable,Object>>(super.entrySet());
        set.add(new Entry<Serializable, Object>() {
          
          public Object setValue(Object paramV) {
            return null;
          }
          
          public Object getValue() {
            throw new RuntimeException("An exception");
          }
          
          public Serializable getKey() {
            return "c";
          }
        });
        return set;
      }
    };
    values.put("e", "e"); 
    values.put("d", "d");
    try {
      cache1.putMap(values);
      assertTrue("An error was expected", false);
    } catch (Exception e) {
    }
    assertEquals(2, cache1.getCacheSize());    
    assertEquals(2, cache2.getCacheSize());
  }
  
  public void testCacheFactory() {
    ExoCache cache = service_.getCacheInstance("myCache");
    assertTrue("expect an instance of AbstractExoCache", cache instanceof AbstractExoCache);
    AbstractExoCache aCache = (AbstractExoCache) cache;
    assertTrue("expect a local cache", aCache.cache.getConfiguration().getCacheMode() == CacheMode.LOCAL);
    aCache.cache.stop();
    cache = service_.getCacheInstance("cacheReplicated");
    assertTrue("expect an instance of AbstractExoCache", cache instanceof AbstractExoCache);
    aCache = (AbstractExoCache) cache;
    assertTrue("expect a replicated cache", aCache.cache.getConfiguration().getCacheMode() == CacheMode.REPL_SYNC);
    aCache.cache.stop();
    cache = service_.getCacheInstance("myCustomCache");
    assertTrue("expect an instance of AbstractExoCache", cache instanceof AbstractExoCache);
    aCache = (AbstractExoCache) cache;
    assertTrue("expect a local cache", aCache.cache.getConfiguration().getCacheMode() == CacheMode.LOCAL);
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
