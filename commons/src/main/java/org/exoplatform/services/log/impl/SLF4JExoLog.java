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

import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.slf4j.Logger;

/**
 * An implementation of {@link ExoLogger} that delegates to an instance of {@link Logger}.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Id$
 */
public class SLF4JExoLog implements Log{

  /** . */
  private Logger logger;

  /**
   * Create a new instance.
   *
   * @param logger Logger
   * @throws NullPointerException if the logger is null
   */
  public SLF4JExoLog(Logger logger) throws NullPointerException {
    if (logger == null)
      throw new NullPointerException();
    
    this.logger = logger;
  }

  public boolean isDebugEnabled() {
    return logger.isDebugEnabled();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isErrorEnabled() {
    return logger.isErrorEnabled();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isFatalEnabled() {
    return logger.isErrorEnabled();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isInfoEnabled() {
    return logger.isInfoEnabled();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isTraceEnabled() {
    return logger.isTraceEnabled();
  }

  /**
   * {@inheritDoc}
   */
  public boolean isWarnEnabled() {
    return logger.isWarnEnabled();
  }

  /**
   * {@inheritDoc}
   */
  public void trace(Object o) {
    logger.trace(String.valueOf(o));
  }

  /**
   * {@inheritDoc}
   */
  public void trace(Object o, Throwable throwable) {
    logger.trace(String.valueOf(o), throwable);
  }

  /**
   * {@inheritDoc}
   */
  public void debug(Object o) {
    logger.debug(String.valueOf(o));
  }

  /**
   * {@inheritDoc}
   */
  public void debug(Object o, Throwable throwable) {
    logger.debug(String.valueOf(o), throwable);
  }

  /**
   * {@inheritDoc}
   */
  public void info(Object o) {
    logger.info(String.valueOf(o));
  }

  /**
   * {@inheritDoc}
   */
  public void info(Object o, Throwable throwable) {
    logger.info(String.valueOf(o), throwable);
  }

  /**
   * {@inheritDoc}
   */
  public void warn(Object o) {
    logger.warn(String.valueOf(o));
  }

  /**
   * {@inheritDoc}
   */
  public void warn(Object o, Throwable throwable) {
    logger.warn(String.valueOf(o), throwable);
  }

  /**
   * {@inheritDoc}
   */
  public void error(Object o) {
    logger.error(String.valueOf(o));
  }

  /**
   * {@inheritDoc}
   */
  public void error(Object o, Throwable throwable) {
    logger.error(String.valueOf(o), throwable);
  }

  /**
   * {@inheritDoc}
   */
  public void fatal(Object o) {
    logger.error(String.valueOf(o));
  }

  /**
   * {@inheritDoc}
   */
  public void fatal(Object o, Throwable throwable) {
    logger.error(String.valueOf(o), throwable);
  }
}
