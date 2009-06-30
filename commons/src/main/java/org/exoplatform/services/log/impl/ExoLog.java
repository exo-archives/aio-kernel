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
package org.exoplatform.services.log.impl;

import org.exoplatform.services.log.Log;
import org.slf4j.Logger;

/**
 * Created by The eXo Platform SAS
 * 
 * @author <a href="work.visor.ck@gmail.com">Dmytro Katayev</a>
 *
 * Jun 26, 2009  
 */
public class ExoLog implements Log {
  
  private Logger logger = null;

  public void debug(Object message) {
    logger.debug(message.toString());
  }

  public void debug(Object message, Throwable t) {
    logger.debug(message.toString(), t);
  }

  public void error(Object message) {
    logger.error(message.toString());
  }

  public void error(Object message, Throwable t) {
    logger.error(message.toString(), t);
  }

  public void fatal(Object message) {
    
  }

  public void fatal(Object message, Throwable t) {

  }

  public void info(Object message) {
    logger.info(message.toString());
  }

  public void info(Object message, Throwable t) {
    logger.info(message.toString(), t);
  }

  public boolean isDebugEnabled() {
    return logger.isDebugEnabled();
  }

  public boolean isErrorEnabled() {
    return logger.isErrorEnabled();
  }

  public boolean isFatalEnabled() {
    return false;
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

  public void trace(Object message) {
    logger.trace(message.toString());
  }

  public void trace(Object message, Throwable t) {
    logger.trace(message.toString(), t);
  }

  public void warn(Object message) {
    logger.warn(message.toString());
  }

  public void warn(Object message, Throwable t) {
    logger.warn(message.toString(), t);
  }

}
