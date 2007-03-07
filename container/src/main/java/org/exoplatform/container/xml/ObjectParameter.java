/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.xml;

import org.exoplatform.xml.object.XMLObject;
/**
 * Jul 19, 2004 
 * @author: Tuan Nguyen
 * @email:   tuan08@users.sourceforge.net
 * @version: $Id: ObjectParameter.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class ObjectParameter extends Parameter {
  Object object ;
  
  public Object getObject() { return object ; }
  public void   setObject(Object obj) { object = obj ; }
  
  public XMLObject getXMLObject() throws Exception { 
    if(object == null) return null ;
    return new XMLObject(object) ;
  }
  
  public void setXMLObject(XMLObject xmlobject) throws Exception {
    if(xmlobject == null) object  = null ;
    try {
      object =  xmlobject.toObject() ;
    } catch(Exception t) {
//      System .err.println("ERRORL: Cannot set value for  param : " + getName()) ;
      throw t ;
    }
  }
}