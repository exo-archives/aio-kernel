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

import org.apache.commons.logging.Log;
import org.apache.commons.logging.impl.SimpleLog;

/**
 * Created by The eXo Platform SAS.
 * 
 * <br/> A dummy wrapper for the SimpleLog logger as SimpleLog has final methods
 * 
 * @author <a href="mailto:gennady.azarenkov@exoplatform.com">Gennady Azarenkov</a>
 * @version $Id: SimpleLogWrapper.java 5332 2006-04-29 18:32:44Z geaz $
 * 
 */

public abstract class SimpleLogWrapper implements Log {
  
  protected SimpleLog log;
  
  public SimpleLogWrapper(String name) {
    log = new SimpleLog(name);
  }

  public void debug(Object arg0, Throwable arg1) {
    log.debug(arg0, arg1);
  }

  public void debug(Object arg0) {
    log.debug(arg0);
  }

  public void error(Object arg0, Throwable arg1) {
    log.error(arg0, arg1);
  }

  public void error(Object arg0) {
    log.error(arg0);
  }

  public void fatal(Object arg0, Throwable arg1) {
    log.fatal(arg0, arg1);
  }

  public void fatal(Object arg0) {
    log.fatal(arg0);
  }

  public void info(Object arg0, Throwable arg1) {
    log.info(arg0, arg1);
  }

  public void info(Object arg0) {
    log.info(arg0);
  }

  public boolean isDebugEnabled() {
    return log.isDebugEnabled();
  }

  public boolean isErrorEnabled() {
    return log.isErrorEnabled();
  }

  public boolean isFatalEnabled() {
    return log.isFatalEnabled();
  }

  public boolean isInfoEnabled() {
    return log.isInfoEnabled();
  }

  public boolean isTraceEnabled() {
    return log.isTraceEnabled();
  }

  public boolean isWarnEnabled() {
    return log.isWarnEnabled();
  }

  public void trace(Object arg0, Throwable arg1) {
    log.trace(arg0, arg1);
  }

  public void trace(Object arg0) {
    log.trace(arg0);
  }

  public void warn(Object arg0, Throwable arg1) {
    log.warn(arg0, arg1);
  }

  public void warn(Object arg0) {
    log.warn(arg0);
  }

}
