/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.xml;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Apr 17, 2005
 * @version $Id: Property.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class Property {
  String name ;
  String value ;
  
  public Property() {} 
  
  public Property(String n, String v) {
    this.name = n ;
    this.value = v ;
  }
  
  public String getName() { return name; }
  public void setName(String s) {  this.name = s; }
  
  public String getValue() {  return value;}
  public void setValue(String s) {  this.value = s; }
}
