/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.xml.object;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Apr 11, 2005
 * @version $Id: XMLEntry.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class XMLEntry {
  
  private XMLBaseObject key_ ;
  private XMLBaseObject value_ ;
  
  public XMLEntry() {} 
  
  public XMLEntry(Object key, Object val) throws Exception {
    key_ = new XMLKey(null, key) ;
    value_ = new XMLValue(null, val) ;
  } 
  
  public XMLBaseObject getKey() { return key_ ; }
  public void setKey(XMLBaseObject key) { key_ = key ; }
  
  public XMLBaseObject getValue() { return value_ ; }
  public void setValue(XMLBaseObject value) { value_ = value ; }
}
