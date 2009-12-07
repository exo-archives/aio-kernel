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
import java.util.Set;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class TestContainerScoping extends AbstractTestContainer {

  public void testFoo( ) throws Exception {
    RootContainer root = createRootContainer("scoped-configuration.xml");

    //
    ManagedContainer child = new ManagedContainer(root);
    child.initContainer();
    child.start();

    //
    MBeanServer server = root.getMBeanServer();
    assertSame(server, child.getMBeanServer());

    //
/*
    child.registerComponentInstance(new ManagedWithObjectNameTemplate("bar"));
    Set bilto2 = server.queryMBeans(new ObjectName("exo:object=\"bar\",foo=bar"), null);
    assertEquals(1, bilto2.size());
*/



  }

}
