/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.commons.utils;

import java.util.*;
import org.exoplatform.commons.xml.ExoXPPParser;

/**
 * Jul 20, 2004 
 * @author: Tuan Nguyen
 * @email:   tuan08@users.sourceforge.net
 * @version: $Id: ExoProperties.java,v 1.1 2004/09/11 14:08:53 tuan08 Exp $
 */
public class ExoProperties extends HashMap {
	public ExoProperties() {  }
  
  public ExoProperties(int size) {  
    super(size) ;
  }
  
  public String getProperty(String key) {	return (String) get(key) ; }
  
  public void setProperty(String key, String value) { put(key, value) ; }
  
  public void addPropertiesFromText(String text) {
    String[] temp = text.split("\n") ;
    for(int i = 0; i < temp.length; i++ ) {
      temp[i] = temp[i].trim() ;
      if(temp[i].length() > 0) {
      	String[] value = temp[i].split("=") ;
        if(value.length == 2)  	put(value[0].trim(), value[1].trim()) ;
      }
    }
  }
  
  public String toText() {
    StringBuffer b = new StringBuffer() ;
    Set set = entrySet() ;
    Iterator i = set.iterator() ;
    while(i.hasNext()) {
      Map.Entry entry = (Map.Entry) i.next() ;
      b.append(entry.getKey()).append("=").append(entry.getValue()).append("\n") ;
    }
    return b.toString() ;
  }
  
  public void addPropertiesFromXml(String xml) {
    try {
      ExoXPPParser xpp = ExoXPPParser.getInstance()  ;
      xpp.setInput(new java.io.StringReader(xml)) ;
      xpp.mandatoryNode("properties") ;
      while(xpp.node("property")) {
        put(xpp.getNodeAttributeValue("key"), xpp.getNodeAttributeValue("value")) ;
      }
    } catch (Exception ex) {
      ex.printStackTrace() ;
    }
  }
  
  public String toXml() {
    StringBuffer b = new StringBuffer() ;
    b.append("<properties>") ;
    Set set = entrySet() ;
    Iterator i = set.iterator() ;
    while(i.hasNext()) {
      Map.Entry entry = (Map.Entry) i.next() ;
      b.append("<property key=\"").append(entry.getKey()).append("\" value=\"").
        append(entry.getValue()).append("\"/>");
    }
    b.append("</properties>") ;
    return b.toString() ;
  }
}