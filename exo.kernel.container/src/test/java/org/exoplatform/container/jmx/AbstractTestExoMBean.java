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

import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.RequiredModelMBean;
import javax.management.MBeanServerFactory;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public abstract class AbstractTestExoMBean extends TestCase {

  protected MBeanServer server;

  @Override
  protected void setUp() throws Exception {
    server = MBeanServerFactory.createMBeanServer();
  }

  @Override
  protected void tearDown() throws Exception {
    MBeanServerFactory.releaseMBeanServer(server);
  }

  protected void assertNotBuildable(Class clazz) {
    try {
      new ExoMBeanInfoBuilder(clazz).build();
      fail();
    }
    catch (Exception ignore) {
    }
  }

  protected Bean register(String name, Class clazz) {
    try {
      ObjectName objectName = ObjectName.getInstance(name);
      ModelMBeanInfo info = new ExoMBeanInfoBuilder(clazz).build();
      RequiredModelMBean mbean = new RequiredModelMBean(info);
      mbean.setManagedResource(clazz.newInstance(), "ObjectReference");
      server.registerMBean(mbean, objectName);
      return new Bean(objectName, (ModelMBeanInfo)server.getMBeanInfo(objectName));
    }
    catch (Exception e) {
      throw new AssertionError(e);
    }
  }

  public static class Bean {

    final ObjectName name;
    final ModelMBeanInfo info;

    public Bean(ObjectName name, ModelMBeanInfo info) {
      this.name = name;
      this.info = info;
    }
  }
}