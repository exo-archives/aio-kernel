/*
 * Copyright (C) 2003-2007 eXo Platform SAS.
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
package org.exoplatform.services.cache.test;

import junit.framework.TestCase;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.Random;
import java.util.List;
import java.util.Arrays;
import java.io.Serializable;

import org.exoplatform.services.cache.ExoCache;
import org.exoplatform.services.cache.FIFOExoCache;
import org.exoplatform.services.cache.SimpleExoCache;
import org.exoplatform.services.cache.CacheListener;
import org.exoplatform.services.cache.concurrent.ConcurrentFIFOExoCache;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class TestScalability extends TestCase {


  private static void doTest(String name, int cacheSize, Config config) {

    System.out.println("-----------------------------------------");
    System.out.println("Test " + name +
      " cacheSize=" + cacheSize +
      " threadSize=" + config.threadSize +
      " getSize=" + config.getSize +
      " putSize=" + config.putSize +
      " configSize=" + config.removalSize);

    List<Test> tests = Arrays.asList(
      createFIFOCacheTest(cacheSize, config),
      createSimpleCacheTest(cacheSize, config), 
      createFIFOCacheWithListenerTest(cacheSize, config),
      createConcurrentFIFOCacheTest(cacheSize, config),
      createConcurrentFIFOCacheWithListenerTest(cacheSize, config));

    for (Test test : tests) {
      long time = test.perform();
      System.out.println("Cache " + test.name + ": " + time + "ms");
    }
    System.out.println("");

  }

  public void testReadMostly1() {
    doTest("Read mostly 1", 50, new Config(4, 100, 100000, 1000, 1000));
    doTest("Read mostly 1", 50, new Config(8, 100, 100000, 1000, 1000));
    doTest("Read mostly 1", 50, new Config(16, 100, 100000, 1000, 1000));
    doTest("Read mostly 1", 50, new Config(32, 100, 100000, 1000, 1000));
  }

  public void testReadMostly2() {
    doTest("Read mostly 2", 500, new Config(4, 100, 100000, 1000, 1000));
    doTest("Read mostly 2", 500, new Config(8, 100, 100000, 1000, 1000));
    doTest("Read mostly 2", 500, new Config(16, 100, 100000, 1000, 1000));
    doTest("Read mostly 2", 500, new Config(32, 100, 100000, 1000, 1000));
  }

  public void testWrite1() {
    doTest("Write only 1", 50, new Config(4, 100, 0, 10000, 10000));
    doTest("Write only 1", 50, new Config(8, 100, 0, 10000, 10000));
    doTest("Write only 1", 50, new Config(16, 100, 0, 10000, 10000));
    doTest("Write only 1", 50, new Config(32, 100, 0, 10000, 10000));
  }

  public void testWrite2() {
    doTest("Write only 2", 500, new Config(4, 100, 0, 10000, 10000));
    doTest("Write only 2", 500, new Config(8, 100, 0, 10000, 10000));
    doTest("Write only 2", 500, new Config(16, 100, 0, 10000, 10000));
    doTest("Write only 2", 500, new Config(32, 100, 0, 10000, 10000));
  }

  private static Test createConcurrentFIFOCacheTest(int cacheSize, Config config) {
    return new Test("Concurrent FIFO cache", new ConcurrentFIFOExoCache(cacheSize), config);
  }

  private static Test createFIFOCacheTest(int cacheSize, Config config) {
    return new Test("FIFO cache", new FIFOExoCache(cacheSize), config);
  }

  private static Test createSimpleCacheTest(int cacheSize, Config config) {
    return new Test("Simple cache", new SimpleExoCache(cacheSize), config);
  }

  private static Test createFIFOCacheWithListenerTest(int cacheSize, Config config) {
    FIFOExoCache cache = new FIFOExoCache(cacheSize);
    cache.addCacheListener(new SimpleCacheListener());
    return new Test("FIFO cache with listener", cache, config);
  }

  private static Test createConcurrentFIFOCacheWithListenerTest(int cacheSize, Config config) {
    ConcurrentFIFOExoCache cache = new ConcurrentFIFOExoCache(cacheSize);
    cache.addCacheListener(new SimpleCacheListener());
    return new Test("Concurrent FIFO cache with listener", cache, config);
  }

  private static class Config {

    private final int threadSize;
    private final int objectSize;
    private final int putSize;
    private final int getSize;
    private final int removalSize;

    private Config(int threadSize, int objectSize, int getSize, int putSize, int removalSize) {
      this.threadSize = threadSize;
      this.objectSize = objectSize;
      this.putSize = putSize;
      this.getSize = getSize;
      this.removalSize = removalSize;
    }
  }

  private static class Test {

    private final String name;
    private final Config config;
    private final ExecutorService executor;
    private final ExecutorCompletionService<Worker> completionService;
    private final Worker[] workers;
    private final ExoCache cache;

    private Test(String name, ExoCache cache, Config config) {
      this.name = name;
      this.config = config;
      this.executor = Executors.newFixedThreadPool(config.threadSize);
      this.completionService = new ExecutorCompletionService<Worker>(executor);
      this.cache = cache;

      //
      Worker[] workers = new Worker[config.threadSize];
      for (int i = 0;i < config.threadSize;i++) {
        workers[i] = new Worker();
      }
      this.workers = workers;
    }

    private void start() {
      for (Worker worker : workers) {
        completionService.submit(worker);
      }
    }

    private void stop() {
      try {
        for (int i = 0;i < config.threadSize;i++) {
          completionService.take().get();
        }
      }
      catch (Exception e) {
        e.printStackTrace();
        fail();
      }
    }

    public long perform() {
      long time = -System.currentTimeMillis();
      start();
      stop();
      time += System.currentTimeMillis();
      return time;
    }

    private class Worker implements Callable<Worker> {

      private final Serializable[] keys;
      private final Object[] objects;
      private final Random random = new Random();

      private Worker() {
        keys = new Serializable[config.objectSize];
        objects = new Object[config.objectSize];
        for (int i = 0;i < config.objectSize;i++) {
          keys[i] = new Key();
          objects[i] = new Object();
        }
      }

      public Worker call() throws Exception {
        int gets = config.getSize;
        int puts = config.putSize;
        int removals = config.removalSize;

        //
        while (gets > 0 || puts > 0 || removals > 0) {
          int entry = Math.abs(random.nextInt()) % objects.length;
          int decision = Math.abs(random.nextInt()) % (gets + puts + removals);
          if (decision < gets) {
            cache.get(keys[entry]);
            gets--;
          } else if (decision >= gets && decision < (gets + puts)) {
            cache.put(keys[entry], objects[entry]);
            puts--;
          } else {
            cache.remove(keys[entry]);
            removals--;
          }
        }

        //
        return this;
      }
    }

    private static class Key implements Serializable {
    }
  }

  private static class SimpleCacheListener implements CacheListener {
    public void onGet(ExoCache cache, Serializable key, Object obj) throws Exception {
    }
    public void onExpire(ExoCache cache, Serializable key, Object obj) throws Exception {
      doWait();
    }
    public void onRemove(ExoCache cache, Serializable key, Object obj) throws Exception {
      doWait();
    }
    public void onPut(ExoCache cache, Serializable key, Object obj) throws Exception {
      doWait();
    }
    public void onClearCache(ExoCache cache) throws Exception {
      doWait();
    }
    private void doWait() throws Exception {
      String s1 = "abcdefgh";
      String s2 = s1 + s1;
      String s3 = s2 + s2;
      String s4 = s3 + s3;
      String s5 = s4 + s4;
    }
  }

}
