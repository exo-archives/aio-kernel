/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.faces.context;

import java.io.IOException;
import java.io.Writer ;
import javax.faces.context.ResponseWriter;
import javax.faces.component.UIComponent ;

/**
 * @email:   tuan08@users.sourceforge.net
 * @version: $Id: HtmlResponseWriter.java,v 1.1 2004/04/29 14:39:24 tuan08 Exp $
 */
public final class HtmlResponseWriter extends ResponseWriter {
  private Writer writer_ ;
  private boolean closeStart_ ;
  
  public HtmlResponseWriter(Writer writer) {
    writer_ = writer ;
    closeStart_ = false ;
  }

  final public void close() throws IOException {
    writer_.close();
  }

  final public void flush() throws IOException { 
    //writer_.flush(); 
  }

  final public void write(char cbuf[]) throws IOException {
    closeStartIfNecessary() ;
    writer_.write(cbuf);
  }

  final public void write(char cbuf[], int off, int len) throws IOException {
    closeStartIfNecessary() ;
    writer_.write(new String(cbuf, off, len));
  }

  final public void write(int c) throws IOException {
    closeStartIfNecessary() ;
    writer_.write((char)(c & 0xffff));
  }

  final public void write(String s) throws IOException {
    closeStartIfNecessary() ;
    writer_.write(s);
  }

  final public void write(String s, int off, int len) throws IOException {
    closeStartIfNecessary() ;
    writer_.write(s.substring(off, len));
  }
  
  public String getContentType() {
    throw new UnsupportedOperationException();
  }
  
  public String getCharacterEncoding() {
    throw new UnsupportedOperationException();
  }

  public void startDocument() throws IOException {
    //writer_.flush() ;
  }

  public void endDocument() throws IOException {
    //writer_.flush();
  }

  public void startElement(String name, UIComponent comp) throws IOException {
    closeStartIfNecessary() ;
    writer_.write("<") ; writer_.write(name) ;
    closeStart_ = true ;
  }

  public void endElement(String name) throws IOException {
    if (closeStart_) {
      writer_.write("/>") ;
      closeStart_ = false ;
      //writer_.flush() ;
    } else {
      writer_.write("</"); writer_.write(name) ; writer_.write('>') ;
      //writer_.flush() ;
    }
  }

  public void writeAttribute(String name, Object value, String prop) throws IOException {
    writer_.write(' '); writer_.write(name); writer_.write("=\""); 
    writer_.write(value.toString()); 
    writer_.write("\"") ;
  }

  public void writeURIAttribute(String name, Object value) throws IOException {
    writer_.write(' '); writer_.write(name); writer_.write("=\""); 
    writer_.write(value.toString()); 
    writer_.write("\"") ;
  }

  public void writeComment(Object comment) throws IOException {
    throw new UnsupportedOperationException();
  }

  public void writeText(Object text, String prop) throws IOException {
    closeStartIfNecessary() ;
    writer_.write(text.toString()); 
  }

  public void writeText(char text[], int off, int len) throws IOException {
    throw new UnsupportedOperationException();
  }
  
  public ResponseWriter cloneWithWriter(Writer writer) {
    return new HtmlResponseWriter(writer) ;
  }

  public void writeURIAttribute(String name, Object value, String property) throws IOException {
    writer_.write(' '); writer_.write(name); writer_.write("=\""); 
    writer_.write(value.toString()); 
    writer_.write("\"") ;
  }

  private void closeStartIfNecessary() throws IOException {
    if(closeStart_) {
      writer_.write(">");
      //writer_.flush() ;
      closeStart_ = false;
    }
  }
}
