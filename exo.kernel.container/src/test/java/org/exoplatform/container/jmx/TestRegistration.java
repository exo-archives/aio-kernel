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

import org.exoplatform.container.RootContainer;
import org.exoplatform.container.jmx.support.ManagedWithObjectNameTemplate;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.ObjectInstance;
import java.util.Set;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class TestRegistration extends AbstractTestContainer {

  public void testFoo( ) throws Exception {
    RootContainer root = createRootContainer("registration-configuration.xml");
    assertNotNull(root.getMBeanServer());

    Object instance = root.getComponentInstance("Foo");
    assertNotNull(instance);

    MBeanServer server = root.getMBeanServer();

    Set set = server.queryMBeans(ObjectName.getInstance("exo:object=\"Foo\""), null);
    assertEquals(1, set.size());

    // Manual

    root.registerComponentInstance("Bar", new ManagedWithObjectNameTemplate("Bar"));

    Object instance2 = root.getComponentInstance("Bar");
    assertNotNull(instance2);

    Set set2 = server.queryMBeans(ObjectName.getInstance("exo:object=\"Bar\""), null);
    assertEquals(1, set2.size());

  }

  

}
