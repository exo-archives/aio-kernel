/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.monitor.jvm;

import java.net.URL;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Nov 8, 2004
 * @version $Id: OperatingSystemInfo.java 5799 2006-05-28 17:55:42Z geaz $
 */
public interface OperatingSystemInfo {
  
  public String getArch() ; 
  public String getName() ; 
  public int    getAvailableProcessors()  ;
  public String getVersion() ;
  
  public URL createURL(String file)  throws Exception ;
}