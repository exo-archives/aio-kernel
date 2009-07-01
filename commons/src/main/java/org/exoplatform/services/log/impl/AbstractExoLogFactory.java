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
package org.exoplatform.services.log.impl;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.exoplatform.services.log.ExoLogFactory;
import org.exoplatform.services.log.Log;

/**
 * An abstract logger factory that maintains a cache of name to logger instance.
 * The cache is based on the {@link ConcurrentHashMap} for better scalability.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Id$
 */
public abstract class AbstractExoLogFactory implements ExoLogFactory {

  /** . */
  private final ConcurrentMap<String, Log> loggers = new ConcurrentHashMap<String, Log>();

  /**
   * Obtain a specified logger.
   *
   * @param name the logger name
   * @return the logger
   */
  protected abstract Log getLogger(String name);

  /**
   * {@inheritDoc}
   */
  public final Log getExoLogger(String name) {
    if (name == null) {
      throw new NullPointerException();
    }
    Log exoLogger = loggers.get(name);
    if (exoLogger == null) {
      exoLogger = getLogger(name);
      Log phantom = loggers.putIfAbsent(name, exoLogger);
      if (phantom != null) {
        exoLogger = phantom;
      }
    }
    return exoLogger;
  }

  /**
   * {@inheritDoc}
   */
  public final Log getExoLogger(Class clazz) {
    if (clazz == null) {
      throw new NullPointerException();
    }
    return getExoLogger(clazz.getName());
  }
}