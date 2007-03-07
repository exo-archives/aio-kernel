/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.xml.object;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Apr 11, 2005
 * @version $Id: XMLMap.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class XMLMap {
  private ArrayList listmap = new ArrayList() ;
  private String type_ ;
  
  public XMLMap() {
    
  }
  
  
  public XMLMap(Map map) throws Exception {
    Iterator i = map.entrySet().iterator() ;
    while(i.hasNext()) {
      Map.Entry entry = (Map.Entry) i.next();
      Object key = entry.getKey() ;
      Object value = entry.getValue() ;
//      S ystem.out.println("key: " + key + ", value: " + value) ;
      if(key == null || value == null) {
        throw new RuntimeException("key: " + key + ", value: " + value + " cannot be null") ;
      }
      listmap.add(new XMLEntry(key, value)) ;
    }
    type_ = map.getClass().getName() ;
  }
  
  public String getType() { return type_ ; }
  public void   setType(String s) { type_ = s ; } 
  
  public Iterator getEntryIterator() {
    return listmap.iterator()  ; 
  }
  
  public Map getMap() throws Exception { 
    Class clazz = Class.forName(type_) ;
    Map map = (Map)clazz.newInstance() ;
    for(int i = 0; i < listmap.size(); i++) {
      XMLEntry entry = (XMLEntry) listmap.get(i) ;
      XMLBaseObject key = entry.getKey() ;
      XMLBaseObject value = entry.getValue() ;
      map.put(key.getObjectValue(), value.getObjectValue()) ;
    }
    return map ;
  }
}