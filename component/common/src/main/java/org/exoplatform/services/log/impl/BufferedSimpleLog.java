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


/**
 * Created by The eXo Platform SAS.
 * 
 * <br/> Extends org.apache.commons.logging.impl.SimpleLog adding the buffering feature.  
 * 
 * @author <a href="mailto:gennady.azarenkov@exoplatform.com">Gennady Azarenkov</a>
 * @version $Id: BufferedSimpleLog.java 5332 2006-04-29 18:32:44Z geaz $
 */

public class BufferedSimpleLog extends SimpleLogWrapper {
  
  private BufferedLog bufLogger;

  public BufferedSimpleLog(String name) {
    super(name);
    this.bufLogger = new BufferedLog(this, name);
  }

  public void debug(Object message) {
    super.debug(message);
    bufLogger.debug(message);
  }

  public void debug(Object message, Throwable t) {
    super.debug(message, t);
    bufLogger.debug(message, t);
  }

  public void info(Object message) {
    super.info(message);
    bufLogger.info(message);
  }

  public void info(Object message, Throwable t) {
    super.info(message, t);
    bufLogger.info(message, t);
  }

  public void warn(Object message) {
    super.warn(message);
    bufLogger.warn(message);
  }

  public void warn(Object message, Throwable t) {
    super.warn(message, t);
    bufLogger.warn(message, t);
  }

  public void error(Object message) {
    super.error(message);
    bufLogger.error(message);
  }

  public void error(Object message, Throwable t) {
    super.error(message, t);
    bufLogger.error(message, t);
  }

  public void fatal(Object message) {
    super.fatal(message);
    bufLogger.fatal(message);
  }

  public void fatal(Object message, Throwable t) {
    super.error(message, t);
    bufLogger.fatal(message, t);
  }
}