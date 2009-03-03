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
package org.exoplatform.services.cache.test;

import org.exoplatform.management.annotations.Managed;
import org.exoplatform.management.jmx.annotations.NameTemplate;
import org.exoplatform.management.jmx.annotations.Property;

import javax.management.MBeanRegistration;
import javax.management.ObjectName;
import javax.management.MBeanServer;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
@Managed
@NameTemplate(@Property(key="type",value="locator"))
public class MBeanServerLocator implements MBeanRegistration {

  public MBeanServer server;

  public MBeanServerLocator() {
  }

  public ObjectName preRegister(MBeanServer server, ObjectName name) throws Exception {
    this.server = server;
    return name;  
  }

  public void postRegister(Boolean registrationDone) {

  }

  public void preDeregister() throws Exception {

  }

  public void postDeregister() {

  }
}
