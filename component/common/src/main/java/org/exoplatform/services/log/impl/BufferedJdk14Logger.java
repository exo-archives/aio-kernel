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

import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.logging.impl.Jdk14Logger;

/**
 * Created by The eXo Platform SAS.
 * 
 * <br/> Buffered org.apache.commons.logging.impl.Jdk14Logger.
 * 
 * @author <a href="mailto:gennady.azarenkov@exoplatform.com">Gennady Azarenkov</a>
 * @version $Id: BufferedJdk14Logger.java 5332 2006-04-29 18:32:44Z geaz $
 */

public class BufferedJdk14Logger extends Jdk14Logger {

  private BufferedLog bufLogger;
  
  public BufferedJdk14Logger(String name) {
    super(name);
    this.bufLogger = new BufferedLog(this, name);
  }

  public void debug(Object message) {
    log(Level.FINE, String.valueOf(message), null);
    bufLogger.debug(message);
  }

  public void debug(Object message, Throwable t) {
    log(Level.FINE, String.valueOf(message), t);
    bufLogger.debug(message, t);
  }

  public void info(Object message) {
    log(Level.INFO, String.valueOf(message), null);
    bufLogger.info(message);
  }

  public void info(Object message, Throwable t) {
    log(Level.INFO, String.valueOf(message), t);
    bufLogger.info(message, t);
  }

  public void warn(Object message) {
    log(Level.WARNING, String.valueOf(message), null);
    bufLogger.warn(message);
  }

  public void warn(Object message, Throwable t) {
    log(Level.WARNING, String.valueOf(message), t);
    bufLogger.warn(message, t);
  }

  public void error(Object message) {
    log(Level.SEVERE, String.valueOf(message), null);
    bufLogger.error(message);
  }

  public void error(Object message, Throwable t) {
    log(Level.SEVERE, String.valueOf(message), t);
    bufLogger.error(message, t);
  }

  public void fatal(Object message) {
    log(Level.SEVERE, String.valueOf(message), null);
    bufLogger.fatal(message);
  }

  public void fatal(Object message, Throwable t) {
    log(Level.SEVERE, String.valueOf(message), t);
    bufLogger.fatal(message, t);
  }
  
  // borrowed from superclass
  // why would not do it protected?
  private void log( Level level, String msg, Throwable ex ) {

    Logger logger = getLogger();
    if (logger.isLoggable(level)) {
        // Hack (?) to get the stack trace.
        Throwable dummyException=new Throwable();
        StackTraceElement locations[]=dummyException.getStackTrace();
        // Caller will be the third element
        String cname="unknown";
        String method="unknown";
        if( locations!=null && locations.length >2 ) {
            StackTraceElement caller=locations[2];
            cname=caller.getClassName();
            method=caller.getMethodName();
        }
        if( ex==null ) {
            logger.logp( level, cname, method, msg );
        } else {
            logger.logp( level, cname, method, msg, ex );
        }
    }

}

}