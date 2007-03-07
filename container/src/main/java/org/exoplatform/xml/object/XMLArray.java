/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.xml.object;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Apr 11, 2005
 * @version $Id: XMLArray.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class XMLArray {
  
  private List list_ = new ArrayList() ;
  
  public XMLArray() {} 
  
  public XMLArray(Object o) throws Exception {
    if(o instanceof int[]) {
      int[] array = (int[]) o ;
      for(int i = 0; i < array.length; i++) {
        list_.add(new Integer(array[i])) ;
      }
    }
  } 
  
  public void addValue(Object object) {
    XMLValue value = (XMLValue) object ;
    list_.add(value) ;
  }
  
  public Iterator getIterator() {
    return list_.iterator()  ; 
  }
}