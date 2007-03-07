/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.       *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.commons.utils;

import java.util.* ;
import java.text.MessageFormat ;
import java.text.DateFormat ;
import org.apache.commons.lang.StringUtils;

public class Formater {
  static private Formater defaultFormater_ = new Formater() ;
  DateFormat dateFormater_ ;

  public Formater() {
    dateFormater_ = 
      DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT); 
  }

  public String format(String s) {
    if(StringUtils.isEmpty(s))return "" ;
    return s ;
  }

  public String format(String s , String defaultValue) {
    if(StringUtils.isEmpty(s)) return defaultValue ;
    return s ;
  }
  
  public String format(String s, Object[] params) {
    return MessageFormat.format(s,params) ;
  }

  public String head(String s) {
    if(StringUtils.isEmpty(s))return "" ;
    if (s.length() < 100) return s ;
    int index = s.indexOf(' ', 50) ;
    if (index > 0) {
      s = s.substring(0, index);
      s = s + "..." ;
    }
    return s ;
  }

  final public String head(String s, int length) {
    if(StringUtils.isEmpty(s))return "" ;
    if (s.length() < length) return s ;
    int index = s.indexOf(' ', length) ;
    if (index > 0) {
      s = s.substring(0, index);
      s = s + "..." ;
    }
    return s ;
  }

  final public String format(Date d) {
    if(d == null) return "N/A" ;
    return dateFormater_.format(d) ;
  }

  final public String format(Integer number) {
    if(number == null) return "" ;
    return number.toString() ;
  }

  static public Formater getDefaultFormater() {
    return defaultFormater_ ;
  }
  
  static public Formater getFormater(Locale local) {
    return defaultFormater_ ;
  }

  final public String format(Object obj) {
    if(obj == null) return "" ;
    if (obj instanceof Date) {
      return dateFormater_.format((Date) obj) ;
    }
    return obj.toString() ;
  }
}