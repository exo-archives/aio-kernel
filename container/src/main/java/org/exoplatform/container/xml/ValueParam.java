/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.xml;

/**
 * Jul 19, 2004 
 * @author: Tuan Nguyen
 * @email:   tuan08@users.sourceforge.net
 * @version: $Id: ValueParam.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class ValueParam extends Parameter {
	private String  value  ;
  
  public String getValue() { return value ; }
  public void   setValue(String s) { value = s ; }
}