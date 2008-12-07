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
package org.exoplatform.services.cache;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * An abstraction for a cache. It does not handle null keys (considered as programmation error from
 * the perspective of the cache client).
 *
 * Created by The eXo Platform SAS. Author : Tuan Nguyen
 * tuan08@users.sourceforge.net Date: Jun 14, 2003 Time: 1:12:22 PM
 */
public interface ExoCache {

  public String getName();

  public void setName(String name);

  public String getLabel();

  public void setLabel(String s);

  /**
   * Performs a lookup operation. If the key is null the value will be ignored and null is returned.
   *
   * @param name the cache key
   * @return the cached value which may be evaluated to null
   */
  public Object get(Serializable name);

  /**
   * Removes an entry from the cache.
   *
   * @param name the cache key
   * @return the previously cached value or null if no entry existed or that entry value was evaluated to null
   * @throws IllegalArgumentException if the key is null
   */
  public Object remove(Serializable name) throws IllegalArgumentException;

  /**
   * Performs a put in the cache.
   *
   * @param name the cache key
   * @param obj the cached value
   * @throws IllegalArgumentException if the key is null
   */
  public void put(Serializable name, Object obj) throws IllegalArgumentException;

  /**
   * Performs a put of all the entries provided by the map argument.
   *
   * @param objs the objects to put
   * @throws IllegalArgumentException if they map is null or contains a null key
   */
  public void putMap(Map<Serializable, Object> objs) throws IllegalArgumentException;

  /**
   * Clears the cache.
   */
  public void clearCache();

  /**
   * Selects a subset of the cache.
   *
   * @param selector the selector
   * @throws IllegalArgumentException if they key is null
   * @throws Exception an exception thrown by the selector
   */
  public void select(CachedObjectSelector selector) throws Exception;

  public int getCacheSize();

  public int getMaxSize();

  public void setMaxSize(int max);

  public long getLiveTime();

  public void setLiveTime(long period);

  public int getCacheHit();

  public int getCacheMiss();

  /**
   * Returns a list of cached object that are considered as valid when the method is called. Any non valid
   * object will not be returnted.
   *
   * @return the list of cached objects
   */
  public List getCachedObjects();

  /**
   * Clears the cache and returns the list of cached object that are considered as valid when the method is called.
   * Any non valid
   * object will not be returned.
   *
   * @return the list of cached objects
   * @throws Exception any exception
   */
  public List removeCachedObjects() throws Exception;

  public void addCacheListener(CacheListener listener);

  public boolean isDistributed();

  public void setDistributed(boolean b);

  public boolean isReplicated();

  public void setReplicated(boolean b);
  
  public boolean isLogEnabled();

  public void setLogEnabled(boolean b);
}
