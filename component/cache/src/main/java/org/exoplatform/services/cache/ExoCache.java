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

import org.exoplatform.management.annotations.ManagedBy;
import org.exoplatform.management.annotations.Managed;
import org.exoplatform.management.annotations.ManagedName;
import org.exoplatform.management.annotations.ManagedDescription;
import org.exoplatform.management.jmx.annotations.NameTemplate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by The eXo Platform SAS. Author : Tuan Nguyen
 * tuan08@users.sourceforge.net Date: Jun 14, 2003 Time: 1:12:22 PM
 */
@Managed
@NameTemplate("exo:service=cache,name={Name}")
@ManagedDescription("Exo Cache")
public interface ExoCache {

  @Managed
  @ManagedName("Name")
  @ManagedDescription("The cache name")
  public String getName();

  public void setName(String name);

  public String getLabel();

  public void setLabel(String s);

  /**
   * Performs a lookup operation.
   *
   * @param name the cache key
   * @return the cached value which may be evaluated to null
   * @throws Exception any exception
   */
  public Object get(Serializable name) throws Exception;

  /**
   * Removes an entry from the cache.
   *
   * @param name the cache key
   * @return the previously cached value or null if no entry existed or that entry value was evaluated to null
   * @throws Exception any exception
   */
  public Object remove(Serializable name) throws Exception;

  /**
   * Performs a put in the cache.
   *
   * @param name the cache key
   * @param obj the cached value
   * @throws Exception any exception
   */
  public void put(Serializable name, Object obj) throws Exception;

  /**
   * Performs a put of all the entries provided by the map argument.
   *
   * @param objs the objects to put
   * @throws Exception any exception
   */
  public void putMap(Map<Serializable, Object> objs) throws Exception;

  /**
   * Clears the cache.
   *
   * @throws Exception any exception
   */
  @Managed
  @ManagedDescription("Evict all entries of the cache")
  public void clearCache() throws Exception;

  /**
   * Selects a subset of the cache.
   *
   * @param selector the selector
   * @throws Exception any exception
   */
  public void select(CachedObjectSelector selector) throws Exception;

  @Managed
  @ManagedName("Size")
  @ManagedDescription("The cache size")
  public int getCacheSize();

  @Managed
  @ManagedName("Capacity")
  @ManagedDescription("The maximum capacity")
  public int getMaxSize();

  @Managed
  public void setMaxSize(int max);

  @Managed
  @ManagedName("TimeToLive")
  @ManagedDescription("The maximum life time of an entry")
  public long getLiveTime();

  @Managed
  public void setLiveTime(long period);

  @Managed
  @ManagedName("HitCount")
  @ManagedDescription("The count of cache hits")
  public int getCacheHit();

  @Managed
  @ManagedName("MissCount")
  @ManagedDescription("The count of cache misses")
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
