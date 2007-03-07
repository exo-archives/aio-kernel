/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.monitor;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.container.RootContainer;
import org.exoplatform.container.SessionContainer;
import org.exoplatform.container.monitor.jvm.JVMRuntimeInfo;
import org.exoplatform.test.BasicTestCase;
/**
 * Thu, May 15, 2003 @   
 * @author: Tuan Nguyen
 * @version: $Id: TestPortalMonitorService.java 5799 2006-05-28 17:55:42Z geaz $
 * @since: 0.0
 * @email: tuan08@yahoo.com
 */
public class TestPortalMonitorService extends BasicTestCase {

  PortalContainer pcontainer_  ;
  SessionContainer scontainer_ ;

  public TestPortalMonitorService(String name) {
    super(name);
  }

  public void setUp() throws Exception {
    setTestNumber(1) ;
    pcontainer_ = PortalContainer.getInstance();
    scontainer_ = pcontainer_.createSessionContainer("session.container", "anon") ;
  }

  public void tearDown() throws Exception {
  }
  
  public void testOSEnvironment() {
    System.out.println(RootContainer.getInstance().getOSEnvironment()) ;
  }

  public void testRuntimInfo() {
    JVMRuntimeInfo info = 
      (JVMRuntimeInfo)RootContainer.getInstance().getComponentInstanceOfType(JVMRuntimeInfo.class) ;
    System.out.println(info) ;
  }
  
  
  protected String getDescription() {
    return "Test portal monitor service " ;
  }
}
