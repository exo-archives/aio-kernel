/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.xml;

import java.util.ArrayList;

/**
 * Jul 19, 2004 
 * @author: Tuan Nguyen
 * @email:   tuan08@users.sourceforge.net
 * @version: $Id: ValuesParam.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class ValuesParam extends  Parameter {
  
	private ArrayList		values = new ArrayList(2);

	public ArrayList getValues() {	return values; }
	public void setValues(ArrayList values) { this.values = values; }
  
  public String getValue() {
   if(values.size() == 0) return null ;
   return (String) values.get(0) ;
  }
}