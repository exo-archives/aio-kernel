/**
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 */
package org.exoplatform.services.log;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Created by The eXo Platform SARL        .
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