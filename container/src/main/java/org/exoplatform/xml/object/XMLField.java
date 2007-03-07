/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.xml.object;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Apr 10, 2005
 * @version $Id: XMLField.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class XMLField extends XMLBaseObject {
  
  private String name ;
  
  public XMLField() { }
  
  public XMLField(String fieldName , Class type, Object val) throws Exception {
    super(type, val) ;
    this.name = fieldName ;
  }
  
  public String getName() { return name ; }
  public void   setName(String s) { name = s ; }
  
  public String toString() {
    return "{" + name + ", " + type + ", " + value + "}";
  }
}
