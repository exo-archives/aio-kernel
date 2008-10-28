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

import org.exoplatform.test.BasicTestCase;
import org.exoplatform.services.cache.CacheListener;
import org.exoplatform.services.cache.ExoCache;
import org.exoplatform.services.cache.concurrent.ConcurrentFIFOExoCache;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class TestConcurrentCache extends BasicTestCase {

  private final Object v1 = new Object();
  private final Object v2 = new Object();
  private final Object v3 = new Object();
  private final Object v4 = new Object();
  private final Object v5 = new Object();

  public void testPut() {
    CacheHelper cache = new CacheHelper();
    cache.put("Foo", v1);
    cache.assertPut("Foo", v1).assertEmpty();
    assertEquals(v1, cache.get("Foo"));
    cache.assertGet("Foo", v1).assertEmpty();
  }

  public void testOverCapacity() {
    CacheHelper cache = new CacheHelper();
    cache.put("Foo", v1);
    cache.assertPut("Foo", v1).assertEmpty();
    cache.put("Bar", v2);
    cache.assertPut("Bar", v2).assertEmpty();
    cache.put("Juu", v3);
    cache.assertExpire("Foo", v1).assertPut("Juu", v3).assertEmpty();
    assertEquals(null, cache.get("Foo"));
    assertEquals(v2, cache.get("Bar"));
    assertEquals(v3, cache.get("Juu"));
  }

  public void testPromotion() {
    CacheHelper cache = new CacheHelper();
    cache.put("Foo", v1);
    cache.assertPut("Foo", v1).assertEmpty();
    cache.put("Bar", v2);
    cache.assertPut("Bar", v2).assertEmpty();
    cache.put("Foo", v3);
    cache.assertPut("Foo", v3).assertEmpty();
    cache.put("Juu", v4);
    cache.assertExpire("Bar", v2).assertPut("Juu", v4).assertEmpty();
    assertEquals(v3, cache.get("Foo"));
    assertEquals(null, cache.get("Bar"));
    assertEquals(v4, cache.get("Juu"));
  }

  public void testRemove() {
    CacheHelper cache = new CacheHelper();
    cache.put("Foo", v1);
    cache.assertPut("Foo", v1).assertEmpty();
    assertEquals(v1, cache.remove("Foo"));
    cache.assertRemove("Foo", v1).assertEmpty();
    assertEquals(null, cache.get("Foo"));
  }

  public void testExpireOnPut() {
    CacheHelper cache = new CacheHelper();
    cache.setMaxSize(4);
    cache.put("Foo", v1);
    cache.assertPut("Foo", v1).assertEmpty();
    cache.put("Bar", v2);
    cache.assertPut("Bar", v2).assertEmpty();
    cache.put("Juu", v3);
    cache.assertPut("Juu", v3).assertEmpty();
    cache.put("Daa", v4);
    cache.assertPut("Daa", v4).assertEmpty();
    cache.setMaxSize(2);
    cache.put("Boo", v5);
    cache.assertExpire("Foo", v1).assertExpire("Bar", v2).assertExpire("Juu", v3).assertPut("Boo", v5).assertEmpty();
  }

  public void testExpireOnGet() {
    CacheHelper cache = new CacheHelper();
    cache.setLiveTimeMillis(5);
    cache.put("Foo", v1);
    cache.assertPut("Foo", v1).assertEmpty();
    waitFor(5);
    assertEquals(null, cache.get("Foo"));
    cache.assertExpire("Foo", v1).assertEmpty();
  }

  public void testExpireOnRemove() {
    CacheHelper cache = new CacheHelper();
    cache.setLiveTimeMillis(5);
    cache.put("Foo", v1);
    cache.assertPut("Foo", v1).assertEmpty();
    waitFor(5);
    assertEquals(null, cache.remove("Foo"));
    cache.assertExpire("Foo", v1).assertEmpty();
  }

  private void waitFor(long millis) {
    try {
      Thread.sleep(millis);
    }
    catch (InterruptedException e) {
      fail();
    }
  }

  private static class CacheHelper extends ConcurrentFIFOExoCache implements CacheListener {

    private final LinkedList<Event> events;

    CacheHelper() {
      super(2);
      events = new LinkedList<Event>();
      addCacheListener(this);
    }

    CacheHelper(int maxSize) {
      super(maxSize);
      events = new LinkedList<Event>();
      addCacheListener(this);
    }

    public CacheHelper assertRemove(Serializable key, Object object) {
      assertEntry(EntryEvent.Type.REMOVE, key, object);
      return this;
    }

    public CacheHelper assertPut(Serializable key, Object object) {
      assertEntry(EntryEvent.Type.PUT, key, object);
      return this;
    }

    public CacheHelper assertExpire(Serializable key, Object object) {
      assertEntry(EntryEvent.Type.EXPIRE, key, object);
      return this;
    }

    public CacheHelper assertGet(Serializable key, Object object) {
      assertEntry(EntryEvent.Type.GET, key, object);
      return this;
    }

    public CacheHelper assertClear() {
      assertFalse(events.isEmpty());
      Event event = events.removeFirst();
      assertNotNull(event);
      assertTrue(event instanceof ClearEvent);
      return this;
    }

    public CacheHelper assertEntry(EntryEvent.Type type, Serializable key, Object object) {
      assertFalse(events.isEmpty());
      Event event = events.removeFirst();
      assertNotNull(event);
      assertTrue(event instanceof EntryEvent);
      EntryEvent entryEvent = (EntryEvent)event;
      assertEquals(type, entryEvent.type);
      assertEquals(key, entryEvent.key);
      assertEquals(object, entryEvent.object);
      return this;
    }

    public CacheHelper assertEmpty() {
      assertTrue(events.isEmpty());
      return this;
    }

    public void onExpire(ExoCache cache, Serializable key, Object obj) throws Exception {
      events.addLast(new EntryEvent(EntryEvent.Type.EXPIRE, key, obj));
    }

    public void onRemove(ExoCache cache, Serializable key, Object obj) throws Exception {
      events.addLast(new EntryEvent(EntryEvent.Type.REMOVE, key, obj));
    }

    public void onPut(ExoCache cache, Serializable key, Object obj) throws Exception {
      events.addLast(new EntryEvent(EntryEvent.Type.PUT, key, obj));
    }

    public void onGet(ExoCache cache, Serializable key, Object obj) throws Exception {
      events.addLast(new EntryEvent(EntryEvent.Type.GET, key, obj));
    }

    public void onClearCache(ExoCache cache) throws Exception {
      events.addLast(new ClearEvent());
    }

    private static class Event {
    }

    private static class EntryEvent extends Event {

      private enum Type {
        EXPIRE, REMOVE, PUT, GET
      }

      private final Type type;
      private final Serializable key;
      private final Object object;

      private EntryEvent(Type type, Serializable key, Object object) {
        this.type = type;
        this.key = key;
        this.object = object;
      }
    }

    private static class ClearEvent extends Event {
    }
  }
}
