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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
/**
 * Jul 19, 2004 
 * @author: Tuan Nguyen
 * @email:   tuan08@users.sourceforge.net
 * @version: $Id: InitParams.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class InitParams extends HashMap {
  
  public InitParams() {
  }
  
  public ValuesParam  getValuesParam(String name) {
  	return (ValuesParam) get(name) ;
  }
  
  public ValueParam  getValueParam(String name) {
    return (ValueParam) get(name) ;
  }
  
  public PropertiesParam  getPropertiesParam(String name) {
    return (PropertiesParam) get(name) ;
  }
  
  public ObjectParameter  getObjectParam(String name) {
    return (ObjectParameter) get(name) ;
  }
  
  public List  getObjectParamValues(Class type) {
    List list = new ArrayList() ;
    Iterator i = values().iterator() ;
    while(i.hasNext()) {
      Object o = i.next() ;
      if(o instanceof ObjectParameter) {
        ObjectParameter param = (ObjectParameter) o ;
        Object paramValue = param.getObject() ;
        if(type.isInstance(paramValue)) list.add(paramValue) ;
      }
    }
    return list ;
  }
  
  public Parameter getParameter(String name) {
    return (Parameter) get(name) ;
  }
  
  public void addParameter(Parameter param) {
    put(param.getName(), param) ;
  }
  
  public Parameter  removeParameter(String name) {
    return (Parameter) remove(name) ;
  }
  
  //--------------xml binding---------------------------------
  public void addParam(Object o) {
    Parameter param = (Parameter) o ;
    put(param.getName(), param) ;
  }
  
  public Iterator getValueParamIterator() {
    List list = new ArrayList() ;
    Iterator i =  values().iterator() ;
    while(i.hasNext()) {
      Object o =  i.next() ;
      if(o instanceof ValueParam) list.add(o) ;
    }
    return list.iterator() ;
  }
  
  public Iterator getValuesParamIterator() {
    List list = new ArrayList() ;
    Iterator i =  values().iterator() ;
    while(i.hasNext()) {
      Object o =  i.next() ;
      if(o instanceof ValuesParam) list.add(o) ;
    }
    return list.iterator() ;
  }
  
  public Iterator getPropertiesParamIterator() {
    List list = new ArrayList() ;
    Iterator i =  values().iterator() ;
    while(i.hasNext()) {
      Object o =  i.next() ;
      if(o instanceof PropertiesParam) list.add(o) ;
    }
    return list.iterator() ;
  }
  
  public Iterator getObjectParamIterator() {
    List list = new ArrayList() ;
    Iterator i =  values().iterator() ;
    while(i.hasNext()) {
      Object o =  i.next() ;
      if(o instanceof ObjectParameter) list.add(o) ;
    }
    return list.iterator() ;
  }
}