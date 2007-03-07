/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.xml;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.exoplatform.commons.utils.ExoProperties;
/**
 * Jul 19, 2004 
 * @author: Tuan Nguyen
 * @email:   tuan08@users.sourceforge.net
 * @version: $Id: PropertiesParam.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class PropertiesParam extends Parameter {
	private ExoProperties  properties  = new ExoProperties();
  
  public ExoProperties getProperties() { return properties ; }
  
  public String getProperty(String name) { return properties.getProperty(name) ; }
  public void  setProperty(String name, String value) { properties.setProperty(name, value) ;}
  
  public void addProperty(Object value) { 
    Property property = (Property) value ;
    properties.put(property.getName(), property.getValue()) ;
  }
  
  public Iterator getPropertyIterator() {
    List list = new ArrayList() ;
    Iterator i =  properties.entrySet().iterator() ;
    while(i.hasNext()) {
      Map.Entry entry = (Map.Entry) i.next() ;
      list.add(new Property((String) entry.getKey(), (String) entry.getValue())) ;
    }
    return list.iterator() ;
  }
}