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

import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Level;
import org.apache.log4j.Priority;

/**
 * Created by The eXo Platform SAS. <br/> Buffered
 * org.apache.commons.logging.impl.Log4JLogger.
 * 
 * @author <a href="mailto:gennady.azarenkov@exoplatform.com">Gennady
 *         Azarenkov</a>
 * @version $Id: BufferedLog4JLogger.java 5332 2006-04-29 18:32:44Z geaz $
 */

public class BufferedLog4JLogger extends Log4JLogger {

  private BufferedLog         bufLogger;

  private static final String FQCN = BufferedLog4JLogger.class.getName();

  public BufferedLog4JLogger(String name) {
    super(name);
    this.bufLogger = new BufferedLog(this, name);
  }

  public void debug(Object message) {
    getLogger().log(FQCN, (Priority) Level.DEBUG, message, null);
    bufLogger.debug(message);
  }

  public void debug(Object message, Throwable t) {
    getLogger().log(FQCN, (Priority) Level.DEBUG, message, t);
    bufLogger.debug(message, t);
  }

  public void info(Object message) {
    getLogger().log(FQCN, (Priority) Level.INFO, message, null);
    bufLogger.info(message);
  }

  public void info(Object message, Throwable t) {
    getLogger().log(FQCN, (Priority) Level.INFO, message, t);
    bufLogger.info(message, t);
  }

  public void warn(Object message) {
    getLogger().log(FQCN, (Priority) Level.WARN, message, null);
    bufLogger.warn(message);
  }

  public void warn(Object message, Throwable t) {
    getLogger().log(FQCN, (Priority) Level.WARN, message, t);
    bufLogger.warn(message, t);
  }

  public void error(Object message) {
    getLogger().log(FQCN, (Priority) Level.ERROR, message, null);
    bufLogger.error(message);
  }

  public void error(Object message, Throwable t) {
    getLogger().log(FQCN, (Priority) Level.ERROR, message, t);
    bufLogger.error(message, t);
  }

  public void fatal(Object message) {
    getLogger().log(FQCN, (Priority) Level.FATAL, message, null);
    bufLogger.fatal(message);
  }

  public void fatal(Object message, Throwable t) {
    getLogger().log(FQCN, (Priority) Level.FATAL, message, null);
    bufLogger.fatal(message);
  }

}
