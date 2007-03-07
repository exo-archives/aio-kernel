/**
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.
 * Please look at license.txt in info directory for more license detail.
 **/
package org.exoplatform.commons.utils;

/**
 * Created by The eXo Platform SARL        .
 * Author : Tuan Nguyen
 *          tuan08@users.sourceforge.net
 * Date: July 4, 2003
 * Time: 9:10:02 PM
 **/
public class HtmlUtil  {

  static public String showXmlTags(String s) { 
    char[] buf = s.toCharArray() ;
    StringBuffer b = new StringBuffer() ;
    for (int i = 0; i < buf.length; i++) {
      if(buf[i] == '<') {
        b.append("&lt;") ;
      } else if (buf[i] == '>') {
        b.append("&gt;") ;
      } else {
        b.append(buf[i]) ;
      }
    }
    return b.toString() ;
  } 
  
  static public String convertNewLineToBR(String s) { 
    if(s == null) return "" ;
    char[] buf = s.toCharArray() ;
    StringBuffer b = new StringBuffer() ;
    for (int i = 0; i < buf.length; i++) {
      if(buf[i] == '\n') {
        b.append("<br/>") ;
      } else {
        b.append(buf[i]) ;
      }
    }
    return b.toString() ;
  } 
}