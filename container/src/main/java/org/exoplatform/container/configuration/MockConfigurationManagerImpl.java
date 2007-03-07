/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.configuration;

import java.net.URL;

import javax.servlet.ServletContext;
/**
 * Jul 19, 2004 
 * @author: Tuan Nguyen
 * @email:   tuan08@users.sourceforge.net
 * @version: $Id: MockConfigurationManagerImpl.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class MockConfigurationManagerImpl extends ConfigurationManagerImpl {
  private String confDir_ ;
  
  public MockConfigurationManagerImpl(ServletContext context) throws Exception {
    super(context) ;
    confDir_ = System.getProperty("mock.portal.dir")  + "/WEB-INF" ;
  }
  
  protected URL getURL(String uri) throws Exception {
    if(uri.startsWith("jar:")) {
      String path = removePrefix("jar:/", uri) ;
      ClassLoader cl = Thread.currentThread().getContextClassLoader() ;
      return cl.getResource(path) ;
    } else if (uri.startsWith("classpath:")) {
      String path = removePrefix("classpath:/", uri);
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      return cl.getResource(path);
    } else if(uri.startsWith("war:")) {
      String path = removePrefix("war:", uri) ;
      URL url =  new URL("file:" + confDir_ + path) ;
      return url ;
    }  else if(uri.startsWith("file:")) {
      return new URL(uri) ; 
    }
    return null ;
  }
}
