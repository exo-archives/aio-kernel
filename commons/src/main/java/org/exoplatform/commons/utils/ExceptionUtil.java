/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
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
