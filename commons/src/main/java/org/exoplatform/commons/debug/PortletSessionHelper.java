/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL    All rights reserved.       *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.commons.debug;

import java.util.* ;
import java.io.PrintStream ;

import javax.portlet.PortletSession ;
/**
 * Static helper methods for string operations.
 * Author: Tuan Nguyen
 */
public class PortletSessionHelper {
  
  static public void printKeyValues(PrintStream out , PortletSession session) {
    Enumeration e = session.getAttributeNames() ;
    while(e.hasMoreElements()) {
      String key = (String) e.nextElement() ;
      out.println(key + ": " + session.getAttribute(key));
    }
  }
}
