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
package org.exoplatform.services.log;

import org.apache.commons.logging.Log;
import org.exoplatform.services.log.impl.SLF4JExoLoggerFactory;
import org.exoplatform.services.log.impl.SimpleExoLoggerFactory;

/**
 * The logger definition for exo platform.
 *
 * <p>The logger implements the {@link org.apache.commons.logging.Log} interface for backward compatibility
 * purpose.</p>
 *
 * <p>This class is also the way to obtain a reference to a logger through the static methods {@link #getExoLogger(String)}
 * and {@link #getExoLogger(Class)}.</p>
 *
 *
 * <p>The factory methods delegates to an instance of {@link org.exoplatform.services.log.ExoLoggerFactory} that is determined
 * by the following rules
 * <ul>
 * <li>A static instance is used and by default the static instance is assigned with an instance of the class
 * {@link org.exoplatform.services.log.impl.SLF4JExoLoggerFactory}. It is possible to change the instance
 * at runtime by calling the static method {@link #setFactory(ExoLoggerFactory)}.</li>
 * <li>If the static instance fails to deliver a logger at runtime due to a {@link NoClassDefFoundError} then a factory
 * instance of class {@link org.exoplatform.services.log.impl.SimpleExoLoggerFactory} is used for fail over.</li>
 * </ul>
 * </p>
 *
 * @author <a href="mailto:gennady.azarenkov@exoplatform.com">Gennady
 *         Azarenkov</a>
 * @version $Id: ExoLogger.java 5332 2006-04-29 18:32:44Z geaz $
 */

public abstract class ExoLogger implements Log {

  /** The factory we use when we cannot load SLF4J (for instance when jibx maven plugin is executed). */
  private static SimpleExoLoggerFactory failOverFactory = new SimpleExoLoggerFactory();

  /** . */
  private static ExoLoggerFactory loggerFactory = new SLF4JExoLoggerFactory();

  /**
   * Configures the exo logger factory. This method can be called multiple times to replace
   * the current static instance.
   *
   * @param factory the new factory
   * @throws NullPointerException when the factory is null
   */
  public static void setFactory(ExoLoggerFactory factory) throws NullPointerException {
    if (factory == null) {
      throw new NullPointerException("Cannot set a null logger factory");
    }
    loggerFactory = factory;
  }

  /**
   * Use instead {@link #getExoLogger(String)}.
   *
   * @param name the logger name
   * @return the logger
   */
  public static Log getLogger(String name) {
    return getExoLogger(name);
  }

  /**
   * Use instead {@link #getExoLogger(Class)}.
   *
   * @param name the logger name
   * @return the logger
   */
  public static Log getLogger(Class name) {
    return getExoLogger(name);
  }

  /**
   * Returns a specified logger.
   *
   * @param name the logger name
   * @return the logger
   * @throws NullPointerException if the name is null
   */
  public static ExoLogger getExoLogger(String name) throws NullPointerException {
    if (name == null) {
      throw new NullPointerException("No null name accepted");
    }
    try {
      return loggerFactory.getExoLogger(name);
    }
    catch (NoClassDefFoundError e) {
      System.err.println("Could not load logger class factory " + e.getMessage() + " will use fail over logger instead");
      return failOverFactory.getExoLogger(name);
    }
  }

  /**
   * Returns a specified logger.
   *
   * @param name the logger name
   * @return the logger
   * @throws NullPointerException if the name is null
   */
  public static ExoLogger getExoLogger(Class name) throws NullPointerException {
    if (name == null) {
      throw new NullPointerException("No null name accepted");
    }
    try {
      return loggerFactory.getExoLogger(name);
    }
    catch (NoClassDefFoundError e) {
      System.err.println("Could not load logger class factory " + e.getMessage() + " will use fail over logger instead");
      return failOverFactory.getExoLogger(name);
    }
  }
}
