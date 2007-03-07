/**
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 */

package org.exoplatform.services.log.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.exoplatform.commons.utils.ExceptionUtil;
import org.exoplatform.services.log.LogMessage;


/**
 * Created by The eXo Platform SARL        .
 * 
 * <br/> Encapsulates static buffer for log/error messages adding log messages buffering functionality.
 * Normally created by buffered logger implementation. 
 * @author Mestrallet Benjamin  benjmestrallet@users.sourceforge.net
 * @author Tuan Nguyen tuan08@users.sourceforge.net
 * @author Gennady Azarenkov
 * @version $Id: BufferedLog.java 5332 2006-04-29 18:32:44Z geaz $
 */

public class BufferedLog {

//  public static final int LOG_BUFFER_SIZE = 2000;
//  public static final int ERROR_BUFFER_SIZE = 1500;
//  static List logBuffer_ = new ArrayList(LOG_BUFFER_SIZE * 2);
//  static List errorBuffer_ = new ArrayList(ERROR_BUFFER_SIZE * 2);

  // to share buffers with LogService
  public static final int LOG_BUFFER_SIZE = ExoLog.LOG_BUFFER_SIZE;
  public static final int ERROR_BUFFER_SIZE = ExoLog.ERROR_BUFFER_SIZE;
  static List logBuffer_ = ExoLog.getLogBuffer();
  static List errorBuffer_ = ExoLog.getErrorBuffer();
  static final String EXO_PREFIX = " - ";
  ///////////////////////////////////
  
  protected String prefix_;
  
  private Log logger;
  
  public BufferedLog(Log logger, String name) {
    this.logger = logger;

    int index = name.lastIndexOf(".");
    String nameSuffix = name;
    if (index > 0)
      nameSuffix = name.substring(index + 1, name.length());

    prefix_ = EXO_PREFIX + "[" + nameSuffix + "] "   ;

    //this.prefix_ = (new StringBuilder()).append(" - [").append(nameSuffix).append(
    //    "] ").toString();

  }
  
  public void trace(Object message, Throwable t) {
    if(logger.isDebugEnabled()) {
      String strace = ExceptionUtil.getExoStackTrace(t);
      addLogMessage(new LogMessage(prefix_, LogMessage.TRACE, message.toString(), strace));
    }
  }

  public void trace(Object message) {
    if(logger.isDebugEnabled()) 
      addLogMessage(new LogMessage(prefix_, LogMessage.TRACE, message.toString(), null));
  }

  public void debug(Object message, Throwable t) {
    if(logger.isDebugEnabled()) {
      String strace = ExceptionUtil.getExoStackTrace(t);
      addLogMessage(new LogMessage(prefix_, LogMessage.DEBUG, message.toString(), strace));
    }
  }

  public void debug(Object message) {
    if(logger.isDebugEnabled()) 
      addLogMessage(new LogMessage(prefix_, LogMessage.DEBUG, message.toString(), null));
  }

  public void error(Object message, Throwable t) {
    if(logger.isErrorEnabled()) {
      String strace = ExceptionUtil.getExoStackTrace(t);
      addLogMessage(new LogMessage(prefix_, LogMessage.ERROR, message.toString(), strace));
    }
  }

  public void error(Object message) {
    if(logger.isErrorEnabled()) 
      addLogMessage(new LogMessage(prefix_, LogMessage.ERROR, message.toString(), null));
  }

  public void fatal(Object message, Throwable t) {
    if(logger.isFatalEnabled()) {
      String strace = ExceptionUtil.getExoStackTrace(t);
      addLogMessage(new LogMessage(prefix_, LogMessage.FATAL, message.toString(), strace));
    }
  }

  public void fatal(Object message) {
    if(logger.isFatalEnabled()) 
      addLogMessage(new LogMessage(prefix_, LogMessage.FATAL, message.toString(), null));
  }

  public void info(Object message, Throwable t) {
    if(logger.isInfoEnabled()) {
      String strace = ExceptionUtil.getExoStackTrace(t);
      addLogMessage(new LogMessage(prefix_, LogMessage.INFO, message.toString(), strace));
    }
  }

  public void info(Object message) {
    if(logger.isInfoEnabled()) 
      addLogMessage(new LogMessage(prefix_, LogMessage.INFO, message.toString(), null));
  }
  
  public void warn(Object message, Throwable t) {
    if(logger.isWarnEnabled()) {
      String strace = ExceptionUtil.getExoStackTrace(t);
      addLogMessage(new LogMessage(prefix_, LogMessage.WARN, message.toString(), strace));
    }
  }

  public void warn(Object message) {
    if(logger.isWarnEnabled()) 
      addLogMessage(new LogMessage(prefix_, LogMessage.WARN, message.toString(), null));
  }
  
  protected Log logger() {
    return logger;
  }
  
  private static void addLogMessage(LogMessage lm) {
    logBuffer_.add(lm);
    if (logBuffer_.size() == LOG_BUFFER_SIZE * 2) {
      List list = new ArrayList(LOG_BUFFER_SIZE * 2);
      for (int i = LOG_BUFFER_SIZE; i < logBuffer_.size(); i++)
        list.add(logBuffer_.get(i));

      logBuffer_ = list;
    }
    if (lm.getType() == LogMessage.ERROR || lm.getType() == LogMessage.FATAL) {
      errorBuffer_.add(lm);
      if (errorBuffer_.size() == ERROR_BUFFER_SIZE * 2) {
        List list = new ArrayList(ERROR_BUFFER_SIZE * 2);
        for (int i = ERROR_BUFFER_SIZE; i < errorBuffer_.size(); i++)
          list.add(errorBuffer_.get(i));

        errorBuffer_ = list;
      }
    }
  }
 
}
