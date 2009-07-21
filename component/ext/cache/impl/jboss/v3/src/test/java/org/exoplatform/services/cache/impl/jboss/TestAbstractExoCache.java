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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.cache.CacheService;
import org.exoplatform.services.cache.CachedObjectSelector;
import org.exoplatform.services.cache.ExoCache;
import org.exoplatform.services.cache.ObjectCacheInfo;
import org.exoplatform.test.BasicTestCase;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * 21 juil. 2009  
 */
public class TestAbstractExoCache extends BasicTestCase {

  AbstractExoCache cache;

  public TestAbstractExoCache(String name) {
    super(name);
  }

  public void setUp() throws Exception {
    CacheService service = (CacheService) PortalContainer.getInstance()
                                             .getComponentInstanceOfType(CacheService.class);
    this.cache = (AbstractExoCache) service.getCacheInstance("myCache");
  }
  
  protected void tearDown() throws Exception {
    cache.clearCache();
  }  
  
  public void testPut() throws Exception {
    cache.put("a", "a");
    cache.put("b", "b");
    cache.put("c", "c");
    assertEquals(3, cache.getCacheSize());
    cache.put("a", "c");
    assertEquals(3, cache.getCacheSize());
    cache.put("d", "c");
    assertEquals(4, cache.getCacheSize());
  }
  
  public void testClearCache() throws Exception {
    cache.put("a", "a");
    cache.put("b", "b");
    cache.put("c", "c");
    assertTrue(cache.getCacheSize() > 0);
    cache.clearCache();
    assertTrue(cache.getCacheSize() == 0);
  }
  
  public void testGet() throws Exception {
    cache.put("a", "a");
    assertEquals("a", cache.get("a"));
    cache.put("a", "c");
    assertEquals("c", cache.get("a"));
    cache.remove("a");
    assertEquals(null, cache.get("a"));
    assertEquals(null, cache.get("x"));
  }
  
  public void testRemove() throws Exception {
    cache.put("a", "a");
    cache.put("b", "b");
    cache.put("c", "c");
    assertEquals(3, cache.getCacheSize());
    cache.remove("a");
    assertEquals(2, cache.getCacheSize());
    cache.remove("b");
    assertEquals(1, cache.getCacheSize());
    cache.remove("x");
    assertEquals(1, cache.getCacheSize());
  }
  
  public void testPutMap() throws Exception {
    Map<Serializable, Object> values = new HashMap<Serializable, Object>();
    values.put("a", "a");
    values.put("b", "b");
    assertEquals(0, cache.getCacheSize());
    cache.putMap(values);
    assertEquals(2, cache.getCacheSize());
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
      cache.putMap(values);
      assertTrue("An error was expected", false);
    } catch (Exception e) {
    }
    assertEquals(2, cache.getCacheSize());
  }
  
  public void testGetCachedObjects() throws Exception {
    cache.put("a", "a");
    cache.put("b", "b");
    cache.put("c", "c");
    cache.put("d", null);
    assertEquals(4, cache.getCacheSize());
    List<Object> values = cache.getCachedObjects();
    assertEquals(3, values.size());
    assertTrue(values.contains("a"));
    assertTrue(values.contains("b"));
    assertTrue(values.contains("c"));
  }
  
  public void testRemoveCachedObjects() throws Exception {
    cache.put("a", "a");
    cache.put("b", "b");
    cache.put("c", "c");
    cache.put("d", null);
    assertEquals(4, cache.getCacheSize());
    List<Object> values = cache.removeCachedObjects();
    assertEquals(3, values.size());
    assertTrue(values.contains("a"));
    assertTrue(values.contains("b"));
    assertTrue(values.contains("c"));
    assertEquals(0, cache.getCacheSize());
  }
  
  public void testSelect() throws Exception {
    cache.put("a", 1);
    cache.put("b", 2);
    cache.put("c", 3);
    final AtomicInteger count = new AtomicInteger();
    CachedObjectSelector selector = new CachedObjectSelector() {

      public void onSelect(ExoCache cache, Serializable key, ObjectCacheInfo ocinfo) throws Exception {
        assertTrue(key.equals("a") || key.equals("b") || key.equals("c"));
        assertTrue(ocinfo.get().equals(1) || ocinfo.get().equals(2) || ocinfo.get().equals(3));
        count.incrementAndGet();
      }

      public boolean select(Serializable key, ObjectCacheInfo ocinfo) {
        return true;
      }
    };
    cache.select(selector);
    assertEquals(3, count.intValue());
  }
  
  public void testGetHitsNMisses() throws Exception {
    int hits = cache.getCacheHit();
    int misses = cache.getCacheMiss();
    cache.put("a", "a");
    cache.get("a");
    cache.remove("a");
    cache.get("a");
    cache.get("z");
    assertEquals(1, cache.getCacheHit() - hits);
    assertEquals(2, cache.getCacheMiss() - misses);
  }
}
