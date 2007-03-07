/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.commons.xml;

import org.xmlpull.mxp1_serializer.MXSerializer;

/**
 * Jul 8, 2004 
 * @author: Tuan Nguyen
 * @email:   tuan08@users.sourceforge.net
 * @version: $Id: ExoXMLSerializer.java,v 1.1 2004/07/08 19:24:47 tuan08 Exp $
 */
public class ExoXMLSerializer extends MXSerializer{
	final static public String INDENTATION = 
    "http://xmlpull.org/v1/doc/properties.html#serializer-indentation" ;
  final static public String LINE_SEPARATOR =
    "http://xmlpull.org/v1/doc/properties.html#serializer-line-separator" ;
  
  public void element(String ns, String tag, String text) throws Exception {
    if(text == null) return ;
  	startTag(ns, tag); text(text); endTag(ns, tag) ;
  }
  
  static public ExoXMLSerializer getInstance() {
  	ExoXMLSerializer ser =  new ExoXMLSerializer() ;
    ser.setProperty(ExoXMLSerializer.INDENTATION, "  ") ;
    ser.setProperty(ExoXMLSerializer.LINE_SEPARATOR, "\n") ;
    return ser ;
  }
}