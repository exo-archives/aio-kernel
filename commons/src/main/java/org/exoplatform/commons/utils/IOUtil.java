/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.commons.utils;

import java.io.* ;
import java.net.URL;

/**
 * @author: Tuan Nguyen
 * @version: $Id: IOUtil.java,v 1.4 2004/09/14 02:41:19 tuan08 Exp $
 * @since: 0.0
 * @email: tuan08@yahoo.com
 */
public class IOUtil {
  static public String getFileContenntAsString(File file, String encoding) throws Exception {
    FileInputStream is = new FileInputStream(file) ;
    return new String(getStreamContentAsBytes(is), encoding) ;
  }
  
  static public String getFileContenntAsString(File file) throws Exception {
    FileInputStream is = new FileInputStream(file) ;
    return new String(getStreamContentAsBytes(is)) ;
  }
  
  static public String getFileContenntAsString(String fileName, String encoding) throws Exception {
    FileInputStream is = new FileInputStream(fileName) ;
    return new String(getStreamContentAsBytes(is), encoding) ;
  }
  
	static public String getFileContenntAsString(String fileName) throws Exception {
		FileInputStream is = new FileInputStream(fileName) ;
		return new String(getStreamContentAsBytes(is)) ;
	}
	
	static public byte[] getFileContentAsBytes(String fileName) throws Exception {
		FileInputStream is = new FileInputStream(fileName) ;
		return getStreamContentAsBytes(is) ;
	}
	
	static public String getStreamContentAsString(InputStream is) throws Exception {
    char buf[] = new char[is.available()];
    BufferedReader in = new BufferedReader(new InputStreamReader(is, "UTF-8"));    
    in.read(buf);   	
	  return new String(buf) ;
	}
	
	static public byte[] getStreamContentAsBytes(InputStream is) throws Exception {
    BufferedInputStream buffer = new BufferedInputStream(is);    
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    byte[] data  = new byte[buffer.available()];      
    int available = -1;
    while( (available = buffer.read(data)) > -1){
      output.write(data, 0, available);
    }   
    return output.toByteArray();
	}
  
  static public String getResourceAsString(String resource) throws Exception {
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    URL url = cl.getResource(resource);
    InputStream is = url.openStream();
    return getStreamContentAsString(is) ;
  }
  
  static public byte[] getResourceAsBytes(String resource) throws Exception {
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    URL url = cl.getResource(resource);
    InputStream is = url.openStream();
    return getStreamContentAsBytes(is) ;
  }

	static public byte[] serialize(Object obj) throws Exception {
	  //long start = System.currentTimeMillis() ;
	  ByteArrayOutputStream bytes = new  ByteArrayOutputStream() ;
	  ObjectOutputStream out = new ObjectOutputStream(bytes);
	  out.writeObject(obj);
	  out.close();
	  byte[] ret = bytes.toByteArray() ;
	  //long end = System.currentTimeMillis() ;
	  return ret ;
	}

	static public Object deserialize(byte[] bytes) throws Exception {
	  if (bytes == null) return null ;
	  //long start = System.currentTimeMillis() ;
	  ByteArrayInputStream is = new  ByteArrayInputStream(bytes) ;
	  ObjectInputStream in = new ObjectInputStream(is);
	  Object obj =  in.readObject() ;
	  in.close();
	  return obj ;
	}
}