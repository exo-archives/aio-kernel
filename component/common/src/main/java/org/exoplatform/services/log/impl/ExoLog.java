package org.exoplatform.services.log.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exoplatform.commons.utils.ExceptionUtil;
import org.exoplatform.services.log.LogMessage;
/**
 * Copyright 2001-2003 The eXo platform SARL All rights reserved.
 * Please look at license.txt in info directory for more license detail.
 **/

/**
 * Created by the Exo Development team.
 * Author : Mestrallet Benjamin
 *          benjmestrallet@users.sourceforge.net
 * Date: 14 nov. 2003
 * Time: 21:47:20
 */
public class ExoLog implements Log {
  static public int FATAL = 0;
  static public int ERROR = 1;
  static public int WARN = 2;
  static public int INFO = 3;
  static public int DEBUG = 4;
  static public int TRACE = 5;

  static final String EXO_PREFIX = " - ";
  
  static private Log log_ = LogFactory.getLog("eXo");
  
  static public int LOG_BUFFER_SIZE = 2000;
  static public int ERROR_BUFFER_SIZE = 1500;

  static private List logBuffer_;
  static private List errorBuffer_;
  
  private String category_ ;
  private int level_ ;
  private String prefix_ ;


  public ExoLog(String name, int level) {
    category_ = name ;
    level_ = level ;
    int index = name.lastIndexOf(".") ;
    String nameSuffix = name ;
    if (index > 0 ) {
      nameSuffix = name.substring(index + 1 , name.length()) ;
    }
    prefix_ = EXO_PREFIX + "[" + nameSuffix + "] "   ;
  }
  
  public String getLogCategory() { return category_ ; }
  
  public int  getLevel() { return level_  ; }
  public void setLevel(int level) { level_ = level ; }
  
  public void trace(Object message) {   log_.trace(prefix_ + message); }

  public void trace(Object message, Throwable t) { 
    log_.trace(prefix_ + message, t);
  }

  public void debug(Object message) {
    if (level_ >= DEBUG) {
      log_.debug(prefix_ + message);  
      addLogMessage(new LogMessage(prefix_ , DEBUG, message.toString(), null)) ;
    }
  }

  public void debug(Object message, Throwable t) {
    if (level_ >= DEBUG)  {
      log_.debug(prefix_ + message, t);  
      String strace = ExceptionUtil.getExoStackTrace(t) ;
      addLogMessage(new LogMessage(prefix_ , DEBUG, message.toString(), strace )) ;
    }
  }
   
  public void info(Object message) {
    if (level_ >= INFO) {
      log_.info(prefix_ + message);    
      addLogMessage(new LogMessage(prefix_ , INFO, message.toString(), null)) ;
    }
  }

  public void info(Object message, Throwable t) {
    if (level_ >= INFO) {
      log_.info(prefix_ + message, t);  
      String strace = ExceptionUtil.getExoStackTrace(t) ;
      addLogMessage(new LogMessage(prefix_ , INFO, message.toString(), strace )) ;
    }
  }

  public void warn(Object message) {
    if (level_ >= WARN) {
      log_.warn(prefix_ + message);  
      addLogMessage(new LogMessage(prefix_ , WARN, message.toString(), null )) ;
    }
  }

  public void warn(Object message, Throwable t) {
    if (level_ >= WARN) {
      log_.warn(prefix_ + message, t);
      String strace = ExceptionUtil.getExoStackTrace(t) ;
      addLogMessage(new LogMessage(prefix_ , WARN, message.toString(), strace )) ;
    }
  }

  public void error(Object message) {
    if (level_ >= ERROR) {
      log_.error(prefix_ + message);        
      addLogMessage(new LogMessage(prefix_ , ERROR, message.toString(), null )) ;
/*
      PortalContainer pcontainer = PortalContainer.getInstance() ;
      if(pcontainer != null) {
        pcontainer.getMonitor().error(message.toString(), null) ;
      }
*/
    }
  }

  public void error(Object message, Throwable t) {
    if (level_ >= ERROR) {
      log_.error(prefix_ + message, t);        
      String strace = ExceptionUtil.getExoStackTrace(t) ;
      addLogMessage(new LogMessage(prefix_ , ERROR, message.toString(), strace )) ;
/*
      PortalContainer pcontainer = PortalContainer.getInstance() ;
      if(pcontainer != null) {
        pcontainer.getMonitor().error(message.toString(), t) ;
      }
*/
    }
  }

  public void fatal(Object message) {
    if (level_ >= FATAL) {
      log_.fatal(prefix_ + message);        
      addLogMessage(new LogMessage(prefix_ , FATAL, message.toString(), null)) ;
    }
  }

  public void fatal(Object message, Throwable t) {
    if (level_ >= FATAL) {
      log_.fatal(prefix_ + message, t);        
      String strace = ExceptionUtil.getExoStackTrace(t) ;
      addLogMessage(new LogMessage(prefix_ , FATAL, message.toString(), strace )) ;
    }
  }

  public final boolean isDebugEnabled() { return level_ >= DEBUG; }

  public final boolean isErrorEnabled() { return level_ >= ERROR; }

  public final boolean isFatalEnabled() { return level_ >= FATAL; }

  public final boolean isInfoEnabled() { return level_ >= INFO; }

  public final boolean isTraceEnabled() { return level_ >= TRACE; }

  public final boolean isWarnEnabled() { return level_ >= WARN; }
  
  private void addLogMessage(LogMessage lm) {
    getLogBuffer().add(lm) ;
    if (getLogBuffer().size() == LOG_BUFFER_SIZE * 2) {
      List list = new ArrayList(LOG_BUFFER_SIZE * 2) ;
      for (int i = LOG_BUFFER_SIZE; i < getLogBuffer().size(); i++) {
        list.add(getLogBuffer().get(i)) ;
      }
      logBuffer_ = list ;
    }
    
    if (lm.getType() == ERROR) {
      getErrorBuffer().add(lm) ;
      if (getErrorBuffer().size() == ERROR_BUFFER_SIZE  * 2) {
        List list = new ArrayList(ERROR_BUFFER_SIZE * 2) ;
        for (int i = ERROR_BUFFER_SIZE; i < getErrorBuffer().size(); i++) {
          list.add(getErrorBuffer().get(i)) ;
        }
        errorBuffer_ = list ;
      }
    }
  }

  static public List getLogBuffer() {
    if (logBuffer_ == null) {
      LOG_BUFFER_SIZE = 2000;
      logBuffer_ = new ArrayList(LOG_BUFFER_SIZE * 2);
    }
    return logBuffer_;
  }

  static public List getErrorBuffer() {
    if (errorBuffer_ == null) {
      ERROR_BUFFER_SIZE = 1500;
      errorBuffer_ = new ArrayList(ERROR_BUFFER_SIZE * 2);
    }
    return errorBuffer_;
  }
}
