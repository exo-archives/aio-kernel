/**
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 */

package org.exoplatform.services.log.impl;


/**
 * Created by The eXo Platform SARL        .
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