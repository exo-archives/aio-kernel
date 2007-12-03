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