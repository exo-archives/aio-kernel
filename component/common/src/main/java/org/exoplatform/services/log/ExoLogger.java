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
import org.apache.commons.logging.LogFactory;

/**
 * Created by The eXo Platform SAS.
 * <br/> An entry point for static logger's methods
 * @author <a href="mailto:gennady.azarenkov@exoplatform.com">Gennady Azarenkov</a>
 * @version $Id: ExoLogger.java 5332 2006-04-29 18:32:44Z geaz $
 */

public class ExoLogger {
  /**
   * @param name
   * @return logger by name
   */
  public static Log getLogger(String name) {
    return LogFactory.getLog(name);
  }

  /**
   * @param clazz
   * @return logger by class
   */
  public static Log getLogger(Class clazz) {
    return LogFactory.getLog(clazz);
  }
  
  /**
   * This method is not needed until the same method is in the ExoLog
   * @return log buffer
   * @see BufferedLog
   */
  //public static List getLogBuffer() {
  //  return BufferedLog.logBuffer_;
  //}

  /**
   * This method is not needed until the same method is in the ExoLog
   * @return error buffer
   * @see BufferedLog
   */
  //public static List getErrorBuffer() {
  //  return BufferedLog.errorBuffer_;
  //}


}