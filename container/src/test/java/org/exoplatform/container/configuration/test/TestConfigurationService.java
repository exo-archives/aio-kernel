/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.configuration.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.container.RootContainer;
import org.exoplatform.container.configuration.ConfigurationManager;
import org.exoplatform.container.monitor.jvm.JVMRuntimeInfo;
import org.exoplatform.container.xml.Configuration;
import org.exoplatform.container.xml.InitParams;
import org.exoplatform.container.xml.ObjectParameter;
import org.exoplatform.test.BasicTestCase;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
/*
 * Thu, May 15, 2003 @   
 * @author: Tuan Nguyen
 * @version: $Id: TestConfigurationService.java 5799 2006-05-28 17:55:42Z geaz $
 * @since: 0.0
 * @email: tuan08@yahoo.com
 */
public class TestConfigurationService extends BasicTestCase {
	private ConfigurationManager service_ ;
  
  public TestConfigurationService(String name) {
    super(name);
  }

  public void setUp() throws Exception {
  	PortalContainer manager  = PortalContainer.getInstance();
    service_ = (ConfigurationManager) manager.getComponentInstanceOfType(ConfigurationManager.class) ;
  }
  
  public void testMarshallAndUnmarshall() throws Exception {
    String basedir = System.getProperty("basedir") ;
    IBindingFactory bfact = BindingDirectory.getFactory(Configuration.class);
    IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
    Object obj = 
      uctx.unmarshalDocument(new FileInputStream(basedir +"/src/main/resources/configuration.xml"), null);
    System.out.print(obj) ;
    
    IMarshallingContext mctx = bfact.createMarshallingContext();
    mctx.setIndent(2);
    mctx.marshalDocument(obj, "UTF-8", null,  new FileOutputStream(basedir + "/target/configuration.xml")) ;
  }
  
  public void testConfigurationService(InitParams params) throws Exception {
    ObjectParameter objParam = params.getObjectParam("new.user.configuration") ;
    objParam.getObject() ;
  }
  
  public void testJVMEnvironment() throws Exception {
    JVMRuntimeInfo jvm = 
      (JVMRuntimeInfo)RootContainer.getInstance().getComponentInstanceOfType(JVMRuntimeInfo.class) ;
    System.out.println(jvm.getSystemPropertiesAsText()) ;
  }
  
  protected String getDescription() {
    return "Test Configuration Service" ;
  }
}
