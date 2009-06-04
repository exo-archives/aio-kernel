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

import org.exoplatform.services.log.ExoLoggerFactory;
import org.exoplatform.services.log.ExoLogger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * An abstract logger factory that maintains a cache of name to logger instance.
 * The cache is based on the {@link ConcurrentHashMap} for better scalability.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public abstract class AbstractExoLoggerFactory implements ExoLoggerFactory {

  /** . */
  private final ConcurrentMap<String, ExoLogger> loggers = new ConcurrentHashMap<String, ExoLogger>();

  /**
   * Obtain a specified logger.
   *
   * @param name the logger name
   * @return the logger
   */
  protected abstract ExoLogger getLogger(String name);

  public final ExoLogger getExoLogger(String name) {
    if (name == null) {
      throw new NullPointerException();
    }
    ExoLogger exoLogger = loggers.get(name);
    if (exoLogger == null) {
      exoLogger = getLogger(name);
      ExoLogger phantom = loggers.putIfAbsent(name, exoLogger);
      if (phantom != null) {
        exoLogger = phantom;
      }
    }
    return exoLogger;
  }

  public final ExoLogger getExoLogger(Class clazz) {
    if (clazz == null) {
      throw new NullPointerException();
    }
    return getExoLogger(clazz.getName());
  }
}