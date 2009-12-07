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

/**
 * This class defines the main configuration properties of an {@link org.exoplatform.services.cache.ExoCache}
 * 
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Feb 20, 2005
 * @version $Id: ExoCacheConfig.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class ExoCacheConfig implements Cloneable {
  
  /**
   * The name of the cache
   */
  private String  name;

  /**
   * The label of the cache
   */
  private String  label;

  /**
   * The maximum number of elements in your cache
   */
  private int     maxSize;

  /**
   * The amount of time (in milliseconds) an element is not written or
   * read before it is evicted. 
   */
  private long    liveTime;

  /**
   * Indicates if the cache is distributed
   */
  private boolean distributed;

  /**
   * Indicates if the cache is replicated
   */
  private boolean replicated;

  /**
   * The full qualified name of the cache implementation to use
   */
  private String  implementation;
  
  /**
   * Indicates if the log is enabled
   */
  private boolean logEnabled;

  public String getName() {
    return name;
  }

  public void setName(String s) {
    name = s;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String s) {
    label = s;
  }

  public int getMaxSize() {
    return maxSize;
  }

  public void setMaxSize(int size) {
    maxSize = size;
  }

  public long getLiveTime() {
    return liveTime;
  }

  public void setLiveTime(long period) {
    liveTime = period * 1000;
  }

  public boolean isDistributed() {
    return distributed;
  }

  public void setDistributed(boolean b) {
    distributed = b;
  }

  public boolean isRepicated() {
    return replicated;
  }

  public void setReplicated(boolean b) {
    replicated = b;
  }

  public String getImplementation() {
    return implementation;
  }

  public void setImplementation(String alg) {
    implementation = alg;
  }

  public boolean isLogEnabled() {
    return logEnabled;
  }

  public void setLogEnabled(boolean enableLogging) {
    this.logEnabled = enableLogging;
  }
  
  @Override
  public ExoCacheConfig clone() {
    try {
      return (ExoCacheConfig) super.clone();
    } catch (CloneNotSupportedException e) {
      throw new AssertionError();
    }
  }
}
