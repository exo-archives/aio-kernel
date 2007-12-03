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
package org.exoplatform.container.xml;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
/**
 * Jul 19, 2004 
 * @author: Tuan Nguyen
 * @email:   tuan08@users.sourceforge.net
 * @version: $Id: ObjectParam.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class ObjectParam extends Parameter {
  private String type ;
  private String package_ ;
	private Object  object_  ;
  private ArrayList properties_ = new ArrayList() ;
  
  public  String getType() { return type  ;} 
  public  void   setType(String s) {  
    type = s; 
    int idx = type.lastIndexOf(".") ;
    if(idx > 0 ) {
      package_ = type.substring(0, idx) ;
    }
  }
  
  
  public Object getObject() {
    if(object_ == null) {
      populateBean() ;
    }
    return object_ ;   
  }
  
  public void addProperty(String name , String value)  {
    properties_.add(new Property(name, value)) ;
  }
  
  private void populateBean() { 
    Property prop = null ;
    try {
      Class clazz = Class.forName(type) ;
      object_ =  clazz.newInstance() ;
      for(int i = 0 ; i < properties_.size() ; i++) {
        prop = (Property) properties_.get(i) ;
        if(prop.name.endsWith("]")) {
          //arrary or list
          populateBeanInArray(object_ , prop.name , prop.value) ;
        } else {
          Object valueBean = getValue(prop.value) ;
          PropertyUtils.setProperty(object_, prop.name, valueBean) ;
        }
      }
    } catch(Throwable ex) {
//      if(prop != null) {
        //S ystem.out.println("Exception when try setting the prop.name " + prop.name_ + 
        //                   ", value prop.value " + prop.value_) ;
//      }
      ex.printStackTrace() ;
    }
  }
  
  private void populateBeanInArray(Object bean, String name , String value) throws Exception  {
    int idx = name.lastIndexOf("[") ;
    String arrayBeanName = name.substring(0, idx) ;
    int index = Integer.parseInt(name.substring(idx + 1, name.length() - 1)) ;
    Object arrayBean = PropertyUtils.getProperty(bean, arrayBeanName) ;
    if(arrayBean instanceof List) {
      List list = (List) arrayBean ;
      Object valueBean = getValue(value) ;
      if(list.size() == index) {
        list.add(valueBean) ;
      } else {
        list.set(index, valueBean) ;
      }
    } else  if(arrayBean instanceof Collection) {
      Collection c = (Collection) arrayBean ;
      Object valueBean = getValue(value) ;
      c.add(valueBean) ;
    } else {
      Object[] array = (Object[]) arrayBean ;
      array[index] = getValue(value) ;
    }
  }
  
  private  Object getValue(String value) throws Exception {
    if(value.startsWith("#new")) {
      String[] temp = value.split(" ") ;
      String className = temp[1] ;
      if(className.indexOf(".") < 0) {
        className = package_ + "." + className ;
        Class clazz = Class.forName(className) ;
        return clazz.newInstance() ;
      }
    } else if(value.startsWith("#int")) {
      String[] temp = value.split(" ") ;
      value = temp[1].trim() ;
      return new Integer(value) ;
    } else if(value.startsWith("#long")) {
      String[] temp = value.split(" ") ;
      value = temp[1].trim() ;
      return new Long(value) ;
    } else if(value.startsWith("#boolean")) {
      String[] temp = value.split(" ") ;
      value = temp[1].trim() ;
      return new Boolean("true".equals(value)) ;
    }
    return value ;
  }
  
  public void addProperty(Object value) {  properties_.add(value) ; }
  
  public Iterator getPropertyIterator() {  return properties_.iterator() ; }
}