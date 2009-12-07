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
import org.exoplatform.container.configuration.ConfigurationManager;
import org.exoplatform.container.configuration.ConfigurationManagerImpl;

import java.net.URL;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class AbstractTestContainer extends TestCase {

  public RootContainer createRootContainer(String relativeConfigurationFile) {
    try {
      RootContainer container = new RootContainer();
      ConfigurationManager manager = new ConfigurationManagerImpl();
      URL url = AbstractTestContainer.class.getResource(relativeConfigurationFile);
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

}
