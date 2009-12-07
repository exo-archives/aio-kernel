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

import org.exoplatform.services.cache.ExoCache;
import org.exoplatform.services.cache.ExoCacheConfig;
import org.exoplatform.services.cache.ExoCacheInitException;
import org.jboss.cache.Cache;

/**
 * This class is used to create the cache according to the given 
 * configuration {@link org.exoplatform.services.cache.ExoCacheConfig}
 * 
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * 20 juil. 2009  
 */
public interface ExoCacheCreator {

  /**
   * Creates an eXo cache according to the given configuration {@link org.exoplatform.services.cache.ExoCacheConfig}
   * @param config the configuration of the cache to apply
   * @param cache the cache to initialize
   * @exception ExoCacheInitException if an exception happens while initializing the cache
   */
  public ExoCache create(ExoCacheConfig config, Cache<Serializable, Object> cache) throws ExoCacheInitException;
  
  /**
   * Returns the type of {@link org.exoplatform.services.cache.ExoCacheConfig} expected by the creator  
   * @return the expected type
   */
  public Class<? extends ExoCacheConfig> getExpectedConfigType();
  
  /**
   * Returns the name of the implementation expected by the creator. This is mainly used to be backward compatible
   * @return the expected by the creator
   */
  public String getExpectedImplementation();
}
