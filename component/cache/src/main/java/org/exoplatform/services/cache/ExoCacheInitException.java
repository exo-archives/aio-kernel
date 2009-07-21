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
 * An exception that represents any type of exception that prevent the initialization of 
 * the {@link org.exoplatform.services.cache.ExoCache}
 * 
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * 15 juil. 2009  
 */
public class ExoCacheInitException extends Exception {

  /**
   * The serial version UID
   */
  private static final long serialVersionUID = 7234748766606267891L;

  /**
   * {@inheritDoc}
   */
  public ExoCacheInitException(String message) {
    super(message);
  }

  /**
   * {@inheritDoc}
   */
  public ExoCacheInitException(Throwable cause) {
    super(cause);
  }

  /**
   * {@inheritDoc}
   */
  public ExoCacheInitException(String message, Throwable cause) {
    super(message, cause);
  }
}
