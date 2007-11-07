/***************************************************************************
 * Copyright 2001-2007 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.monitor.jvm;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import junit.framework.TestCase;

import org.exoplatform.container.configuration.ConfigurationException;

/**
 * Created by The eXo Platform SARL
 * Author : Alex Reshetnyak
 *          alex.reshetnyak@exoplatform.com.ua
 *          reshetnyak.alex@exoplatform.com.ua
 * Nov 7, 2007  
 */
public class TestJ2EEServerInfo extends TestCase{
  
  private static URL configurationURL = null;
  
  private File confFile;
  
  private String confPath;
  
  private String confDir;
  
  public void setUp() {
    try {
      confFile = new File("exo-configuration.xml");
      if (confFile.createNewFile()) { 
        confPath = confFile.getAbsolutePath();
        confDir = confPath.replace(System.getProperty("file.separator")+"exo-configuration.xml", "");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public void testServerDirs() throws Exception {
    try {
      testServerDir("catalina.home");
      testServerDir("jonas.base");
      testServerDir("jboss.home.dir");
      testServerDir("jetty.home");
      testServerDir("was.install.root");
      testServerDir("wls.home");
      testServerDir("maven.exoplatform.dir");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  private void testServerDir(String systemProperty) throws Exception {
    System.setProperty(systemProperty, confDir);
    
    initConfigurationURL(null);
    
    System.out.println(configurationURL.getPath());
    assertEquals(configurationURL.getPath(), confPath);
    
    System.clearProperty(systemProperty);
  }
  
  public void tearDown() {
   if(confFile.delete())
     System.out.println("deleate ok!");
  }
  
  
  private static void initConfigurationURL(ClassLoader configClassLoader) throws MalformedURLException, ConfigurationException {
    // (1) set by setConfigurationURL or setConfigurationPath
    // or
    if (configurationURL == null) {

      // (2) exo-configuration.xml in AS (standalone) home directory
      configurationURL = new URL("file:"
            + (new J2EEServerInfo()).getServerHome() + "/exo-configuration.xml");
      
      // (3) conf/exo-configuration.xml in war/ear(?)
      if(!fileExists(configurationURL) && configClassLoader != null) {
        configurationURL = configClassLoader.getResource("conf/exo-configuration.xml");
      }

      // (4) conf/standalone/configuration.xml in jar
      // Note: this option may be suitable for test only as there can be more than
      // one jars with conf/standalone/configuration.xml file
/*
      if(!fileExists(configurationURL)) {
        configurationURL = Thread.currentThread().getContextClassLoader().getResource("conf/standalone/configuration.xml");

        if (configurationURL == null)
          throw new ConfigurationException(
            "No StandaloneContainer config found. Check if conf/standalone/configuration.xml exists !");
      }
*/
//      container.getContext().setAttribute(CONFIGURATION_URL_ATTR, configurationURL);
    }

  }
  
  private static boolean fileExists(URL url) {
    try {
      url.openStream().close();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

}
