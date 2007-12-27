/*
 * Copyright (C) 2003-2007 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
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
