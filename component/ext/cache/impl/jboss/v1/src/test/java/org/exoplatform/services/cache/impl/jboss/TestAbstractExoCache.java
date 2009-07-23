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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.cache.CacheListener;
import org.exoplatform.services.cache.CacheService;
import org.exoplatform.services.cache.CachedObjectSelector;
import org.exoplatform.services.cache.ExoCache;
import org.exoplatform.services.cache.ExoCacheConfig;
import org.exoplatform.services.cache.ExoCacheFactory;
import org.exoplatform.services.cache.ObjectCacheInfo;
import org.exoplatform.test.BasicTestCase;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * 21 juil. 2009  
 */
public class TestAbstractExoCache extends BasicTestCase {

  CacheService service;
  AbstractExoCache cache;
  ExoCacheFactory factory;

  public TestAbstractExoCache(String name) {
    super(name);
  }

  public void setUp() throws Exception {
    this.service = (CacheService) PortalContainer.getInstance()
                                             .getComponentInstanceOfType(CacheService.class);
    this.cache = (AbstractExoCache) service.getCacheInstance("myCache");
    this.factory = (ExoCacheFactory) PortalContainer.getInstance()
                                            .getComponentInstanceOfType(ExoCacheFactory.class);    
  }
  
  protected void tearDown() throws Exception {
    cache.clearCache();
  }  
  
  public void testPut() throws Exception {
    cache.put(new MyKey("a"), "a");
    cache.put(new MyKey("b"), "b");
    cache.put(new MyKey("c"), "c");
    assertEquals(3, cache.getCacheSize());
    cache.put(new MyKey("a"), "c");
    assertEquals(3, cache.getCacheSize());
    cache.put(new MyKey("d"), "c");
    assertEquals(4, cache.getCacheSize());
  }
  
  public void testClearCache() throws Exception {
    cache.put(new MyKey("a"), "a");
    cache.put(new MyKey("b"), "b");
    cache.put(new MyKey("c"), "c");
    assertTrue(cache.getCacheSize() > 0);
    cache.clearCache();
    assertTrue(cache.getCacheSize() == 0);
  }
  
  public void testGet() throws Exception {
    cache.put(new MyKey("a"), "a");
    assertEquals("a", cache.get(new MyKey("a")));
    cache.put(new MyKey("a"), "c");
    assertEquals("c", cache.get(new MyKey("a")));
    cache.remove(new MyKey("a"));
    assertEquals(null, cache.get(new MyKey("a")));
    assertEquals(null, cache.get(new MyKey("x")));
  }
  
  public void testRemove() throws Exception {
    cache.put(new MyKey("a"), 1);
    cache.put(new MyKey("b"), 2);
    cache.put(new MyKey("c"), 3);
    assertEquals(3, cache.getCacheSize());
    assertEquals(1, cache.remove(new MyKey("a")));
    assertEquals(2, cache.getCacheSize());
    assertEquals(2, cache.remove(new MyKey("b")));
    assertEquals(1, cache.getCacheSize());
    assertEquals(null, cache.remove(new MyKey("x")));
    assertEquals(1, cache.getCacheSize());
  }
  
  public void testPutMap() throws Exception {
    Map<Serializable, Object> values = new HashMap<Serializable, Object>();
    values.put(new MyKey("a"), "a");
    values.put(new MyKey("b"), "b");
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
    values.put(new MyKey("e"), "e"); 
    values.put(new MyKey("d"), "d");
    try {
      cache.putMap(values);
      assertTrue("An error was expected", false);
    } catch (Exception e) {
    }
    assertEquals(2, cache.getCacheSize());
  }
  
  public void testGetCachedObjects() throws Exception {
    cache.put(new MyKey("a"), "a");
    cache.put(new MyKey("b"), "b");
    cache.put(new MyKey("c"), "c");
    cache.put(new MyKey("d"), null);
    assertEquals(4, cache.getCacheSize());
    List<Object> values = cache.getCachedObjects();
    assertEquals(3, values.size());
    assertTrue(values.contains("a"));
    assertTrue(values.contains("b"));
    assertTrue(values.contains("c"));
  }
  
  public void testRemoveCachedObjects() throws Exception {
    cache.put(new MyKey("a"), "a");
    cache.put(new MyKey("b"), "b");
    cache.put(new MyKey("c"), "c");
    cache.put(new MyKey("d"), null);
    assertEquals(4, cache.getCacheSize());
    List<Object> values = cache.removeCachedObjects();
    assertEquals(3, values.size());
    assertTrue(values.contains("a"));
    assertTrue(values.contains("b"));
    assertTrue(values.contains("c"));
    assertEquals(0, cache.getCacheSize());
  }
  
  public void testSelect() throws Exception {
    cache.put(new MyKey("a"), 1);
    cache.put(new MyKey("b"), 2);
    cache.put(new MyKey("c"), 3);
    final AtomicInteger count = new AtomicInteger();
    CachedObjectSelector selector = new CachedObjectSelector() {

      public void onSelect(ExoCache cache, Serializable key, ObjectCacheInfo ocinfo) throws Exception {
        assertTrue(key.equals(new MyKey("a")) || key.equals(new MyKey("b")) || key.equals(new MyKey("c")));
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
    cache.put(new MyKey("a"), "a");
    cache.get(new MyKey("a"));
    cache.remove(new MyKey("a"));
    cache.get(new MyKey("a"));
    cache.get(new MyKey("z"));
    assertEquals(1, cache.getCacheHit() - hits);
    assertEquals(2, cache.getCacheMiss() - misses);
  }
  
  
  public void testDistributedCache() throws Exception {
    ExoCacheConfig config = new ExoCacheConfig();
    config.setName("MyCacheDistributed");
    config.setMaxSize(5);
    config.setLiveTime(1000);
    config.setDistributed(true);
    ExoCacheConfig config2 = new ExoCacheConfig();
    config2.setName("MyCacheDistributed2");
    config2.setMaxSize(5);
    config2.setLiveTime(1000);
    config2.setDistributed(true);    
    AbstractExoCache cache1 = (AbstractExoCache) factory.createCache(config);
    MyCacheListener listener1 = new MyCacheListener();
    cache1.addCacheListener(listener1);
    AbstractExoCache cache2 = (AbstractExoCache) factory.createCache(config);
    MyCacheListener listener2 = new MyCacheListener();
    cache2.addCacheListener(listener2);
    AbstractExoCache cache3 = (AbstractExoCache) factory.createCache(config2);
    MyCacheListener listener3 = new MyCacheListener();
    cache3.addCacheListener(listener3);
    try {
      cache1.put(new MyKey("a"), "b");
      assertEquals(1, cache1.getCacheSize());
      assertEquals("b", cache2.get(new MyKey("a")));
      assertEquals(1, cache2.getCacheSize());
      assertEquals(0, cache3.getCacheSize());
      assertEquals(1, listener1.put);
      assertEquals(1, listener2.put);
      assertEquals(0, listener3.put);
      assertEquals(0, listener1.get);
      assertEquals(1, listener2.get);
      assertEquals(0, listener3.get);
      cache2.put(new MyKey("b"), "c");
      assertEquals(2, cache1.getCacheSize());
      assertEquals(2, cache2.getCacheSize());
      assertEquals("c", cache1.get(new MyKey("b")));
      assertEquals(0, cache3.getCacheSize());
      assertEquals(2, listener1.put);
      assertEquals(2, listener2.put);
      assertEquals(0, listener3.put);
      assertEquals(1, listener1.get);
      assertEquals(1, listener2.get);
      assertEquals(0, listener3.get);
      cache3.put(new MyKey("c"), "d");
      assertEquals(2, cache1.getCacheSize());
      assertEquals(2, cache2.getCacheSize());
      assertEquals(1, cache3.getCacheSize());
      assertEquals("d", cache3.get(new MyKey("c")));
      assertEquals(2, listener1.put);
      assertEquals(2, listener2.put);
      assertEquals(1, listener3.put);
      assertEquals(1, listener1.get);
      assertEquals(1, listener2.get);
      assertEquals(1, listener3.get);
      cache2.put(new MyKey("a"), "a");
      assertEquals(2, cache1.getCacheSize());
      assertEquals(2, cache2.getCacheSize());
      assertEquals("a", cache1.get(new MyKey("a")));
      assertEquals(3, listener1.put);
      assertEquals(3, listener2.put);
      assertEquals(1, listener3.put);
      assertEquals(2, listener1.get);
      assertEquals(1, listener2.get);
      assertEquals(1, listener3.get);    
      cache2.remove(new MyKey("a"));
      assertEquals(1, cache1.getCacheSize());
      assertEquals(1, cache2.getCacheSize());   
      assertEquals(3, listener1.put);
      assertEquals(3, listener2.put);
      assertEquals(1, listener3.put);
      assertEquals(2, listener1.get);
      assertEquals(1, listener2.get);
      assertEquals(1, listener3.get);    
      assertEquals(1, listener1.remove);
      assertEquals(1, listener2.remove);
      assertEquals(0, listener3.remove);    
      cache1.clearCache();
      assertEquals(0, cache1.getCacheSize());
      assertEquals(null, cache2.get(new MyKey("b")));
      assertEquals(0, cache2.getCacheSize());
      assertEquals(3, listener1.put);
      assertEquals(3, listener2.put);
      assertEquals(1, listener3.put);
      assertEquals(2, listener1.get);
      assertEquals(2, listener2.get);
      assertEquals(1, listener3.get);    
      assertEquals(2, listener1.remove);
      assertEquals(2, listener2.remove);
      assertEquals(0, listener3.remove);    
      assertEquals(1, listener1.clearCache);
      assertEquals(0, listener2.clearCache);
      assertEquals(0, listener3.clearCache);    
      Map<Serializable, Object> values = new HashMap<Serializable, Object>();
      values.put(new MyKey("a"), "a");
      values.put(new MyKey("b"), "b");
      cache1.putMap(values);
      assertEquals(2, cache1.getCacheSize());
      Thread.sleep(40);
      assertEquals("a", cache2.get(new MyKey("a")));
      assertEquals("b", cache2.get(new MyKey("b")));
      assertEquals(2, cache2.getCacheSize());
      assertEquals(5, listener1.put);
      assertEquals(5, listener2.put);
      assertEquals(1, listener3.put);
      assertEquals(2, listener1.get);
      assertEquals(4, listener2.get);
      assertEquals(1, listener3.get);    
      assertEquals(2, listener1.remove);
      assertEquals(2, listener2.remove);
      assertEquals(0, listener3.remove);    
      assertEquals(1, listener1.clearCache);
      assertEquals(0, listener2.clearCache);
      assertEquals(0, listener3.clearCache);     
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
      values.put(new MyKey("e"), "e"); 
      values.put(new MyKey("d"), "d");
      try {
        cache1.putMap(values);
        assertTrue("An error was expected", false);
      } catch (Exception e) {
      }
      assertEquals(2, cache1.getCacheSize());    
      assertEquals(2, cache2.getCacheSize());
      assertEquals(5, listener1.put);
      assertEquals(5, listener2.put);
      assertEquals(1, listener3.put);
      assertEquals(2, listener1.get);
      assertEquals(4, listener2.get);
      assertEquals(1, listener3.get);    
      assertEquals(2, listener1.remove);
      assertEquals(2, listener2.remove);
      assertEquals(0, listener3.remove);    
      assertEquals(1, listener1.clearCache);
      assertEquals(0, listener2.clearCache);
      assertEquals(0, listener3.clearCache);
    } finally {
      cache1.cache.stop();
      cache2.cache.stop();
      cache3.cache.stop();
    } 
  } 
  
  public void testMultiThreading() throws Exception {
    final ExoCache cache = service.getCacheInstance("test-multi-threading");    
    final int totalElement = 100;
    final int totalTimes = 20;
    int reader = 20;
    int writer = 10;
    int remover = 5;
    int cleaner = 1;
    final CountDownLatch startSignalWriter = new CountDownLatch(1);
    final CountDownLatch startSignalOthers = new CountDownLatch(1);
    final CountDownLatch doneSignal = new CountDownLatch(reader + writer + remover);
    final List<Exception> errors = Collections.synchronizedList(new ArrayList<Exception>());
    for (int i = 0; i < writer; i++) {
      final int index = i;
      Thread thread = new Thread() {
        public void run() {
          try {
            startSignalWriter.await();
            for (int j = 0; j < totalTimes; j++) {
              for (int i = 0; i < totalElement; i++) {
                cache.put(new MyKey("key" + i), "value" + i);
              }
              if (index == 0 && j == 0) {
                // The cache is full, we can launch the others
                startSignalOthers.countDown();
              }
              sleep(50);
            }
            doneSignal.countDown();
          } catch (Exception e) {
            errors.add(e);
          }
        }
      };
      thread.start();
    }
    startSignalWriter.countDown();
    for (int i = 0; i < reader; i++) {
      Thread thread = new Thread() {
        public void run() {
          try {
            startSignalOthers.await();
            for (int j = 0; j < totalTimes; j++) {
              for (int i = 0; i < totalElement; i++) {
                cache.get(new MyKey("key" + i));
              }
              sleep(50);
            }
            doneSignal.countDown();
          } catch (Exception e) {
            errors.add(e);
          }
        }
      };
      thread.start();
    }    
    for (int i = 0; i < remover; i++) {
      Thread thread = new Thread() {
        public void run() {
          try {
            startSignalOthers.await();
            for (int j = 0; j < totalTimes; j++) {
              for (int i = 0; i < totalElement; i++) {
                cache.remove(new MyKey("key" + i));
              }
              sleep(50);
            }
            doneSignal.countDown();
          } catch (Exception e) {
            errors.add(e);
          }
        }
      };
      thread.start();
    }
    doneSignal.await();
    for (int i = 0; i < totalElement; i++) {
      cache.put(new MyKey("key" + i), "value" + i);
    }
    assertEquals(totalElement, cache.getCacheSize());
    final CountDownLatch startSignal = new CountDownLatch(1);
    final CountDownLatch doneSignal2 = new CountDownLatch(writer + cleaner);
    for (int i = 0; i < writer; i++) {
      Thread thread = new Thread() {
        public void run() {
          try {
            startSignal.await();
            for (int j = 0; j < totalTimes; j++) {
              for (int i = 0; i < totalElement; i++) {
                cache.put(new MyKey("key" + i), "value" + i);
              }
              sleep(50);
            }
            doneSignal2.countDown();
          } catch (Exception e) {
            errors.add(e);
          }
        }
      };
      thread.start();
    }    
    for (int i = 0; i < cleaner; i++) {
      Thread thread = new Thread() {
        public void run() {
          try {
            startSignal.await();
            for (int j = 0; j < totalTimes; j++) {
              sleep(150);
              cache.clearCache();
            }
            doneSignal2.countDown();
          } catch (Exception e) {
            errors.add(e);
          }
        }
      };
      thread.start();
    }       
    cache.clearCache();
    assertEquals(0, cache.getCacheSize());
    if (!errors.isEmpty()) {
      for (Exception e: errors) {
        e.printStackTrace();
      }
      throw errors.get(0);
    }
    
  }
  
  public static class MyCacheListener implements CacheListener {
    
    public int clearCache;
    public int expire;
    public int get;
    public int put;
    public int remove;

    public void onClearCache(ExoCache cache) throws Exception {
      clearCache++;
    }

    public void onExpire(ExoCache cache, Serializable key, Object obj) throws Exception {
      expire++;
    }

    public void onGet(ExoCache cache, Serializable key, Object obj) throws Exception {
      get++;
    }

    public void onPut(ExoCache cache, Serializable key, Object obj) throws Exception {
      put++;
    }

    public void onRemove(ExoCache cache, Serializable key, Object obj) throws Exception {
      remove++;
    }
  }
  
  public static class MyKey implements Serializable {
    public String value;
    
    public MyKey(String value) {
      this.value = value;
    }

    @Override
    public boolean equals(Object paramObject) {
      return paramObject instanceof MyKey && ((MyKey) paramObject).value.endsWith(value);
    }

    @Override
    public int hashCode() {
      return value.hashCode();
    }
  }
}
