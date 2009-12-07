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
package org.exoplatform.container.configuration.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.container.RootContainer;
import org.exoplatform.container.configuration.ConfigurationManager;
import org.exoplatform.container.monitor.jvm.JVMRuntimeInfo;
import org.exoplatform.container.xml.Configuration;
import org.exoplatform.container.xml.InitParams;
import org.exoplatform.container.xml.ObjectParameter;
import org.exoplatform.test.BasicTestCase;

/*
 * Thu, May 15, 2003 @   
 * @author: Tuan Nguyen
 * @version: $Id: TestConfigurationService.java 5799 2006-05-28 17:55:42Z geaz $
 * @since: 0.0
 * @email: tuan08@yahoo.com
 */
public class TestConfigurationService extends BasicTestCase {
  private ConfigurationManager service_;

  public TestConfigurationService(String name) {
    super(name);
  }

  public void setUp() throws Exception {
    PortalContainer manager = PortalContainer.getInstance();
    service_ = (ConfigurationManager) manager.getComponentInstanceOfType(ConfigurationManager.class);
  }

  public void testMarshallAndUnmarshall() throws Exception {
    String basedir = System.getProperty("basedir");
    IBindingFactory bfact = BindingDirectory.getFactory(Configuration.class);
    IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
    Object obj = uctx.unmarshalDocument(new FileInputStream(basedir
        + "/src/main/resources/configuration.xml"), null);
    System.out.print(obj);

    IMarshallingContext mctx = bfact.createMarshallingContext();
    mctx.setIndent(2);
    mctx.marshalDocument(obj, "UTF-8", null, new FileOutputStream(basedir
        + "/target/configuration.xml"));
  }

  public void testConfigurationService(InitParams params) throws Exception {
    ObjectParameter objParam = params.getObjectParam("new.user.configuration");
    objParam.getObject();
  }

  public void testJVMEnvironment() throws Exception {
    JVMRuntimeInfo jvm = (JVMRuntimeInfo) RootContainer.getInstance()
                                                       .getComponentInstanceOfType(JVMRuntimeInfo.class);
    System.out.println(jvm.getSystemPropertiesAsText());
  }

  protected String getDescription() {
    return "Test Configuration Service";
  }
}
