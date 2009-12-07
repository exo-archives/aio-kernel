/*
 * Copyright (C) 2003-2008 eXo Platform SAS.
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
package org.exoplatform.services.cache.test;

import org.exoplatform.services.log.impl.BufferedSimpleLog;

/**
 * A Mock Logger that records how many times a log method was called.
 * Does not actually log anything. Usefull to asserty that we log what we wanted.
 * <br>
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Oct 4, 2008  
 */
public class MockTestLogger extends BufferedSimpleLog {
  

  int debugCount=0;
  int warnCount=0;
  int traceCount=0;
  
  public MockTestLogger(String name) {
    super(name);
  }  
  public void debug(Object message) {
   debugCount++;
   //super.debug(message);
  }

  public void debug(Object message, Throwable t) {
    debugCount++;
    //super.debug(message);
  }

 


  public void trace(Object message) {
    traceCount++;
    //super.trace(message);
  }

  public void trace(Object message, Throwable t) {
    traceCount++;
    //super.trace(message);
  }

  public void warn(Object message) {
    warnCount++;
    //super.warn(message);
  }

  public void warn(Object message, Throwable t) {
    warnCount++;
    //super.warn(message);

  }

  public int getDebugCount() {
    return debugCount;
  }

  public void setDebugCount(int debugCount) {
    this.debugCount = debugCount;
  }

  public int getWarnCount() {
    return warnCount;
  }

  public void setWarnCount(int warnCount) {
    this.warnCount = warnCount;
  }

  public int getTraceCount() {
    return traceCount;
  }

  public void setTraceCount(int traceCount) {
    this.traceCount = traceCount;
  }

}
