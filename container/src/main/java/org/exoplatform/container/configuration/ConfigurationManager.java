/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.configuration;

import java.io.InputStream;
import java.net.URL;
import java.util.Collection;

import org.exoplatform.container.xml.Component;
import org.exoplatform.container.xml.Configuration;
/**
 * Jul 19, 2004 
 * @author: Tuan Nguyen
 * @email:   tuan08@users.sourceforge.net
 * @version: $Id: ConfigurationManager.java 5799 2006-05-28 17:55:42Z geaz $
 */
public interface ConfigurationManager {
  public Configuration getConfiguration() ;
  
  public Component getComponent(String service) throws Exception ;
  public Component getComponent(Class clazz) throws Exception ;
  public Collection getComponents()  ;
  
  public void addConfiguration(String url) throws Exception ;
  public void addConfiguration(Collection urls) throws Exception ;
  public void addConfiguration(URL url) throws Exception ;
  public URL getResource(String url, String defaultURL) throws Exception ;
  public URL getResource(String url) throws Exception ;
  public InputStream getInputStream(String url, String defaultURL) throws Exception  ;
  public InputStream getInputStream(String url) throws Exception  ;
  
  public boolean isDefault(String value) ;
  
  public  URL getURL(String uri) throws Exception;
}