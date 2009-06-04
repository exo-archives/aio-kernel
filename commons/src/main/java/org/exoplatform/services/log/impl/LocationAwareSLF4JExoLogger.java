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

import org.slf4j.spi.LocationAwareLogger;
import org.exoplatform.services.log.ExoLogger;

/**
 * An implementation of {@link ExoLogger} that delegates to an instance of {@link LocationAwareLogger}.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class LocationAwareSLF4JExoLogger extends ExoLogger {

  /** . */
  private static final String FQCN = LocationAwareSLF4JExoLogger.class.getName();
  
  /** . */
  private final LocationAwareLogger logger;

  /**
   * Create a new instance.
   *
   * @param logger the logger
   * @throws NullPointerException if the logger is null
   */
  public LocationAwareSLF4JExoLogger(LocationAwareLogger logger) {
    if (logger == null) {
      throw new NullPointerException();
    }
    this.logger = logger;
  }

  public boolean isDebugEnabled() {
    return logger.isDebugEnabled();
  }

  public boolean isErrorEnabled() {
    return logger.isErrorEnabled();
  }

  public boolean isFatalEnabled() {
    return logger.isErrorEnabled();
  }

  public boolean isInfoEnabled() {
    return logger.isInfoEnabled();
  }

  public boolean isTraceEnabled() {
    return logger.isTraceEnabled();
  }

  public boolean isWarnEnabled() {
    return logger.isWarnEnabled();
  }

  public void trace(Object o) {
    logger.log(null, FQCN, LocationAwareLogger.TRACE_INT, String.valueOf(o), null);
  }

  public void trace(Object o, Throwable throwable) {
    logger.log(null, FQCN, LocationAwareLogger.TRACE_INT, String.valueOf(o), throwable);
  }

  public void debug(Object o) {
    logger.log(null, FQCN, LocationAwareLogger.DEBUG_INT, String.valueOf(o), null);
  }

  public void debug(Object o, Throwable throwable) {
    logger.log(null, FQCN, LocationAwareLogger.DEBUG_INT, String.valueOf(o), throwable);
  }

  public void info(Object o) {
    logger.log(null, FQCN, LocationAwareLogger.INFO_INT, String.valueOf(o), null);
  }

  public void info(Object o, Throwable throwable) {
    logger.log(null, FQCN, LocationAwareLogger.INFO_INT, String.valueOf(o), throwable);
  }

  public void warn(Object o) {
    logger.log(null, FQCN, LocationAwareLogger.WARN_INT, String.valueOf(o), null);
  }

  public void warn(Object o, Throwable throwable) {
    logger.log(null, FQCN, LocationAwareLogger.WARN_INT, String.valueOf(o), throwable);
  }

  public void error(Object o) {
    logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, String.valueOf(o), null);
  }

  public void error(Object o, Throwable throwable) {
    logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, String.valueOf(o), throwable);
  }

  public void fatal(Object o) {
    logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, String.valueOf(o), null);
  }

  public void fatal(Object o, Throwable throwable) {
    logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, String.valueOf(o), throwable);
  }
}
