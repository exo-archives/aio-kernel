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
import org.exoplatform.container.jmx.support.Manager;
import org.exoplatform.container.jmx.support.ManagedByManager;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.ObjectInstance;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class TestManagedBy extends AbstractTestContainer {

  public void testFoo( ) throws Exception {
    RootContainer root = createRootContainer("managedby-configuration.xml");

    MBeanServer server = root.getMBeanServer();

    ManagedByManager mbm = (ManagedByManager)root.getComponentInstance("ManagedByManager");

    assertNotNull(mbm);

    ObjectInstance instance = server.getObjectInstance(new ObjectName("exo:object=ManagedByManager"));

    Manager manager = (Manager)server.getAttribute(instance.getObjectName(), "Reference");

    assertNotNull(manager);


  }

}