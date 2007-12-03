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
package org.exoplatform.commons.utils;

/**
 * Jul 8, 2004 
 * @author: Tuan Nguyen
 * @email:   tuan08@users.sourceforge.net
 * @version: $Id: ExceptionUtil.java,v 1.2 2004/10/05 14:40:47 tuan08 Exp $
 */
public class ExceptionUtil {
  private static String LINE_SEPARATOR = System.getProperty("line.separator") ;
  
   static public  String getExoStackTrace(Throwable t) {
    StackTraceElement[] elements = t.getStackTrace() ; 
    StringBuffer b = new StringBuffer() ;
    b.append(t.getMessage()).append(LINE_SEPARATOR) ;
    boolean appendDot = true ;
    for(int i = 0; i < elements.length ; i++) {
      if(i < 10) {
        b.append(" at ").append(elements[i].toString()).append(LINE_SEPARATOR) ;
      } else {
        if(elements[i].getClassName().startsWith("exo.")) {
          b.append(" at ").append(elements[i].toString()).append(LINE_SEPARATOR) ;
          appendDot = true  ;
        } else {
          if(appendDot) {
            b.append("  [...................................]").append(LINE_SEPARATOR) ;
            appendDot = false ;
          }
        }
      }
    }
    return b.toString() ;
   }
    
   static public  String getStackTrace(Throwable t, int numberOfLine) {
    StackTraceElement[] elements = t.getStackTrace() ;
    if(numberOfLine > elements.length) numberOfLine = elements.length ; 
    StringBuffer b = new StringBuffer() ;
    b.append(t.getMessage()).append(LINE_SEPARATOR) ;
    for(int i = 0; i < numberOfLine; i++) {
      b.append(elements[i].toString()).append(LINE_SEPARATOR) ;
    }
    return b.toString() ;
  }
   
  static public Throwable getRootCause(Throwable t) {
    Throwable root = t ;
    while(root.getCause() != null) {
      root = root.getCause() ;
    }
    return root ;
  }
}
