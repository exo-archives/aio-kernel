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
package org.exoplatform.services.cache.impl.jboss.lru;

import org.exoplatform.services.cache.ExoCacheConfig;

/**
 * The {@link org.exoplatform.services.cache.ExoCacheConfig} for the LRU implementation
 * 
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * 21 juil. 2009  
 */
public class LRUExoCacheConfig extends ExoCacheConfig {

  private int maxNodes;
  private int timeToLiveSeconds;
  private int maxAgeSeconds;
  
  public int getMaxNodes() {
    return maxNodes;
  }
  
  public void setMaxNodes(int maxNodes) {
    this.maxNodes = maxNodes;
  }
  
  public int getTimeToLiveSeconds() {
    return timeToLiveSeconds;
  }
  
  public void setTimeToLiveSeconds(int timeToLiveSeconds) {
    this.timeToLiveSeconds = timeToLiveSeconds;
  }
  
  public int getMaxAgeSeconds() {
    return maxAgeSeconds;
  }
  
  public void setMaxAgeSeconds(int maxAgeSeconds) {
    this.maxAgeSeconds = maxAgeSeconds;
  }  
}
