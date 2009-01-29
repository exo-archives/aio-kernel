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
package org.exoplatform.container.jmx;

import junit.framework.TestCase;
import org.exoplatform.container.RootContainer;
import org.exoplatform.container.jmx.support.ManagedManagementAware;
import org.exoplatform.container.jmx.support.ManagedDependent;
import org.exoplatform.container.configuration.ConfigurationManagerImpl;
import org.exoplatform.container.configuration.ConfigurationManager;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.net.URL;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class TestRootContainerManagedIntegration extends TestCase {

  public RootContainer createRootContainer(String relativeConfigurationFile) {
    try {
      RootContainer container = new RootContainer();
      ConfigurationManager manager = new ConfigurationManagerImpl();
      URL url = TestRootContainerManagedIntegration.class.getResource(relativeConfigurationFile);
      assertNotNull(url);
      manager.addConfiguration(url);
      container.registerComponentInstance(ConfigurationManager.class, manager);
      container.initContainer();
      container.start();
      return container;
    }
    catch (Exception e) {
      AssertionError err = new AssertionError("Could not start root container");
      err.initCause(e);
      throw err;
    }
  }
  
  public void testExplicitObjectName() throws Exception {
    RootContainer container = createRootContainer("configuration1.xml");
    Object expectedObject = container.getComponentInstance("ManagedWithExplicitObjectName");
    assertNotNull(expectedObject);
    MBeanServer server = container.getMBeanServer();
    assertNotNull(server);
    Object object = server.getAttribute(ObjectName.getInstance("exo:object=ManagedWithExplicitObjectName"), "Reference");
    assertNotNull(object);
    assertEquals(expectedObject, object);
  }

  public void testObjectNameTemplate() throws Exception {
    RootContainer container = createRootContainer("configuration2.xml");
    Object expectedFoo = container.getComponentInstance("Foo");
    Object expectedBar = container.getComponentInstance("Bar");
    assertNotNull(expectedFoo);
    assertNotNull(expectedBar);
    MBeanServer server = container.getMBeanServer();
    assertNotNull(server);
    Object foo = server.getAttribute(ObjectName.getInstance("exo:object=\"Foo\""), "Reference");
    assertNotNull(foo);
    Object bar = server.getAttribute(ObjectName.getInstance("exo:object=\"Bar\""), "Reference");
    assertNotNull(bar);
    assertEquals(expectedFoo, foo);
    assertEquals(expectedBar, bar);
  }

  public void testObjectNameTemplateOverriddenByExplicitObjectName() throws Exception {
    RootContainer container = createRootContainer("configuration3.xml");
    Object expectedObject = container.getComponentInstance("ManagedWithObjectNameTemplateOverriddenByExplicitObjectName");
    assertNotNull(expectedObject);
    MBeanServer server = container.getMBeanServer();
    assertNotNull(server);
    Object object = server.getAttribute(ObjectName.getInstance("exo:object=ManagedWithObjectNameTemplateOverriddenByExplicitObjectName"), "Reference");
    assertNotNull(object);
    assertEquals(expectedObject, object);
  }

  public void testManagementAware() throws Exception {
    RootContainer container = createRootContainer("configuration4.xml");
    ManagedManagementAware aware = (ManagedManagementAware)container.getComponentInstance("ManagedManagementAware");
    assertNotNull(aware.context);
    MBeanServer server = container.getMBeanServer();
    assertNotNull(server);
    Object foo = server.getAttribute(ObjectName.getInstance("exo:object=\"Foo\""), "Reference");
    assertNotNull(foo);
    assertEquals(aware.foo, foo);
    ManagedDependent expectedBar = new ManagedDependent("Bar");
    aware.context.register(expectedBar);
    assertEquals(1, server.queryMBeans(ObjectName.getInstance("exo:object=\"Bar\""), null).size());
    Object bar = server.getAttribute(ObjectName.getInstance("exo:object=\"Bar\""), "Reference");
    assertEquals(expectedBar, bar);
    aware.context.unregister(expectedBar);
    assertEquals(0, server.queryMBeans(ObjectName.getInstance("exo:object=\"Bar\""), null).size());
  }
}
