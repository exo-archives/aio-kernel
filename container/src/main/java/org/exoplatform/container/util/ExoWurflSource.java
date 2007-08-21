/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.util;

import java.io.InputStream;
//import net.sourceforge.wurfl.wurflapi.WurflSource;

public class ExoWurflSource /*implements WurflSource*/ {

	public InputStream getWurflInputStream() {
	  ClassLoader cl = Thread.currentThread().getContextClassLoader() ;
	  java.net.URL wurflUrl = cl.getResource("conf/wurfl.xml") ;
	  try {
	    return wurflUrl.openStream();
	  }
	  catch (Exception e) {
	    e.printStackTrace();
	    return null;
	  }
	}
    
	public InputStream getWurflPatchInputStream() {
		return null;
	}
}
