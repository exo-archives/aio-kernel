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
package org.exoplatform.services.cache;

/**
 * This class allows you to create a new instance of {@link org.exoplatform.services.cache.ExoCache}
 * 
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * 15 juil. 2009  
 */
public interface ExoCacheFactory {
  
  /**
   * Creates a new instance of {@link org.exoplatform.services.cache.ExoCache}
   * @param config the cache to create
   * @return the new instance of {@link org.exoplatform.services.cache.ExoCache}
   * @exception ExoCacheInitException if an exception happens while initializing the cache
   */
  public ExoCache createCache(ExoCacheConfig config) throws ExoCacheInitException;  
}
