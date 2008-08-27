/**
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.
 * Please look at license.txt in info directory for more license detail.
 **/
package org.exoplatform.container;

import java.io.File;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.servlet.ServletContext;

import org.exoplatform.container.configuration.ConfigurationManager;
import org.exoplatform.container.configuration.ConfigurationManagerImpl;
import org.exoplatform.container.configuration.MockConfigurationManagerImpl;
import org.exoplatform.container.jmx.MX4JComponentAdapterFactory;
import org.exoplatform.container.monitor.jvm.J2EEServerInfo;
import org.exoplatform.container.monitor.jvm.OperatingSystemInfo;
import org.exoplatform.container.util.ContainerUtil;
import org.exoplatform.test.mocks.servlet.MockServletContext;

/**
 * Created by The eXo Platform SAS
 * Author : Tuan Nguyen
 *          tuan08@users.sourceforge.net
 * Date: Jul 21, 2004
 * Time: 12:15:28 AM
 */
public class RootContainer extends ExoContainer {
	private static RootContainer singleton_ ;
  
  private  MBeanServer mbeanServer_ ;
  private OperatingSystemInfo osenv_ ;
  private J2EEServerInfo serverenv_ ;

  public RootContainer() {
    super(new MX4JComponentAdapterFactory(), null)  ;
    mbeanServer_ = MBeanServerFactory.createMBeanServer("exomx");
    Runtime.getRuntime().addShutdownHook(new ShutdownThread(this)) ;
    serverenv_ = new J2EEServerInfo() ;
    this.registerComponentInstance(J2EEServerInfo.class, serverenv_) ;
  }
  
  public OperatingSystemInfo getOSEnvironment() { 
    if( osenv_ == null) {
      osenv_ = (OperatingSystemInfo) this.getComponentInstanceOfType(OperatingSystemInfo.class) ;
    }
    return osenv_ ; 
  }
  
  public J2EEServerInfo getServerEnvironment() {  return serverenv_ ; } 
  
  public PortalContainer getPortalContainer(String name) {
    PortalContainer pcontainer  = (PortalContainer)this.getComponentInstance(name) ;
    if(pcontainer == null) {
      J2EEServerInfo senv = getServerEnvironment() ;
      if("standalone".equals(senv.getServerName()) || "test".equals(senv.getServerName())) {
        try {
          MockServletContext scontext = new MockServletContext(name) ;
          pcontainer = new PortalContainer(this, scontext) ;
          ConfigurationManagerImpl cService = new MockConfigurationManagerImpl(scontext) ;
          cService.addConfiguration(ContainerUtil.getConfigurationURL("conf/portal/configuration.xml")) ;
          cService.addConfiguration(ContainerUtil.getConfigurationURL("conf/portal/test-configuration.xml")) ;
          cService.processRemoveConfiguration() ;
          pcontainer.registerComponentInstance(ConfigurationManager.class, cService) ;
          pcontainer.initContainer() ;
          registerComponentInstance(name, pcontainer) ;
          PortalContainer.setInstance(pcontainer) ;
          ExoContainerContext.setCurrentContainer(pcontainer);
          pcontainer.start() ;
        } catch(Exception ex) {
          ex.printStackTrace() ;
        }
      }
    } 
    return pcontainer ; 
  }
  
  synchronized public PortalContainer createPortalContainer(ServletContext context) {
    try {
      PortalContainer pcontainer =    new PortalContainer(this, context) ;
      PortalContainer.setInstance(pcontainer) ;
      ExoContainerContext.setCurrentContainer(pcontainer);
      ConfigurationManagerImpl cService = new ConfigurationManagerImpl(context) ;
      
      // add configs from services
      try {
        cService.addConfiguration(ContainerUtil.getConfigurationURL("conf/portal/configuration.xml")) ;
      } catch(Exception ex){
        System .err.println("ERROR: cannot add configuration conf/portal/configuration.xml. ServletContext: " + context);
        ex.printStackTrace() ;
      }
      
      // add configs from web apps
      try {
        cService.addConfiguration("war:/conf/configuration.xml") ;
      } catch(Exception ex){
        System .err.println("ERROR: cannot add configuration war:/conf/configuration.xml. ServletContext: " + context);
        ex.printStackTrace() ;
      }
      
      // add config from application server, $AH_HOME/exo-conf/portal/configuration.xml
      String overrideConfig = singleton_.getServerEnvironment().getExoConfigurationDirectory() 
          + "/portal/" + pcontainer.getPortalContainerInfo().getContainerName() + "/configuration.xml";
      try {
        File file = new File(overrideConfig) ;
        if (file.exists())
          cService.addConfiguration(file.toURI().toURL()) ;  
      } catch(Exception ex){
        System .err.println("ERROR: cannot add configuration " + overrideConfig + ". ServletContext: " + context);
        ex.printStackTrace() ;
      }
      
      cService.processRemoveConfiguration() ;
      pcontainer.registerComponentInstance(ConfigurationManager.class, cService) ;
      pcontainer.initContainer() ;
      registerComponentInstance(context.getServletContextName(), pcontainer) ; 
      PortalContainer.setInstance(pcontainer) ;
      ExoContainerContext.setCurrentContainer(pcontainer);
      pcontainer.start() ;
      return pcontainer ;
    } catch (Exception ex) {
      System .err.println("ERROR: cannot create portal container. ServletContext: " + context);
      ex.printStackTrace() ;
    }
    return null ;
  }
  
  synchronized public void removePortalContainer(ServletContext servletContext) {
    this.unregisterComponent(servletContext.getServletContextName()) ;
  }
  
  public MBeanServer getMBeanServer() {  return mbeanServer_; }
  
  public static Object getComponent(Class key) {
    return getInstance().getComponentInstanceOfType(key) ;
  }
  
  
  public static RootContainer getInstance() {
    if(singleton_ == null) {
      synchronized(RootContainer.class) {
        if(singleton_ == null) {
          try {
            singleton_ = new RootContainer() ;
            ConfigurationManagerImpl service = new ConfigurationManagerImpl() ;
            service.addConfiguration(ContainerUtil.getConfigurationURL("conf/configuration.xml")) ;
            if(System.getProperty("maven.exoplatform.dir") != null) {
              service.addConfiguration(ContainerUtil.getConfigurationURL("conf/test-configuration.xml")) ;
            }
            String confDir  = singleton_.getServerEnvironment().getExoConfigurationDirectory() ;
            String overrideConf = confDir + "/configuration.xml";
            File file = new File(overrideConf) ;
            if(file.exists()) {
              service.addConfiguration("file:" + overrideConf) ;  
            }
            service.processRemoveConfiguration() ;
            singleton_.registerComponentInstance(ConfigurationManager.class, service) ;
            singleton_.initContainer() ;
            singleton_.start() ;
            ExoContainerContext.setTopContainer(singleton_);
          } catch (Throwable ex) { ex.printStackTrace() ;}
        }
      }
    }
    return singleton_  ;
  }
  
  static public void setInstance(RootContainer rcontainer) { singleton_ = rcontainer ; }
  
  static class ShutdownThread extends Thread {
    RootContainer container_ ;
    ShutdownThread(RootContainer container) {  container_ = container ;   }
    
    public void run() {   container_.stop() ; }
  }

  public void stop() {
    super.stop();
    ExoContainerContext.setTopContainer(null);
  }
}
