/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.commons.utils;

import java.util.ResourceBundle;

/**
 * Jul 18, 2004 
 * @author: Tuan Nguyen
 * @email:   tuan08@users.sourceforge.net
 * @version: $Id: ExpressionUtil.java,v 1.1 2004/07/21 19:59:11 tuan08 Exp $
 */
public class ExpressionUtil {
	static public String getExpressionValue(ResourceBundle res, String key)  {
		if (res == null)  return key ;
		if(!isResourceBindingExpression(key)) return key  ; 
		String value = key ;
		key = key.substring(2, key.length() - 1) ;
		try {
			value = res.getString(key) ;
		} catch (java.util.MissingResourceException ex) { }
		return value ;
	}
  
  static public boolean isResourceBindingExpression(String key) {
    if (key == null || key.length() < 3) return false ;
    if(key.charAt(0) == '#' && key.charAt(1) == '{' && key.charAt(key.length() - 1) == '}') {
      return true ; 
    }
    return false ;
  }
  
  static public String getValue(ResourceBundle res, String key)  {
    try {
      return res.getString(key) ;
    } catch (java.util.MissingResourceException ex) {  }
    return key ;
  }
  
  
  static public boolean isDataBindingExpression(String key) {
    if (key == null || key.length() < 3) return false ;
    if(key.charAt(0) == '$' && key.charAt(1) == '{' && key.charAt(key.length() - 1) == '}') {
      return true ; 
    }
    return false ;
  }
  
  static public String removeBindingExpression(String key)  {
    key = key.substring(2, key.length() - 1) ;
    return key ;
  }
}