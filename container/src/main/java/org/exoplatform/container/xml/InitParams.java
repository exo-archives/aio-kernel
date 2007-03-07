/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
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