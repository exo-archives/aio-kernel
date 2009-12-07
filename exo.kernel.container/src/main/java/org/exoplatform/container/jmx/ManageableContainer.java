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

import org.exoplatform.container.CachingContainer;
import org.exoplatform.management.ManagementAware;
import org.exoplatform.management.ManagementContext;
import org.exoplatform.management.annotations.Managed;
import org.picocontainer.defaults.ComponentAdapterFactory;
import org.picocontainer.defaults.DuplicateComponentKeyRegistrationException;
import org.picocontainer.defaults.DefaultComponentAdapterFactory;
import org.picocontainer.PicoContainer;
import org.picocontainer.ComponentAdapter;
import org.picocontainer.PicoRegistrationException;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.MBeanInfo;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanRegistration;
import java.util.Set;
import java.util.Iterator;
import java.util.HashMap;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class ManageableContainer extends CachingContainer {

  /** . */
  private static final ThreadLocal<ManageableComponentAdapterFactory> hack = new ThreadLocal<ManageableComponentAdapterFactory>();

  /** . */
  protected ManagementContextImpl managementContext;

  public ManageableContainer(ManagementContextImpl managementContext) {
    super(getComponentAdapterFactory(new MX4JComponentAdapterFactory()));
    this.managementContext = managementContext;
    init(null);
  }

  public ManageableContainer(PicoContainer parent) {
    super(getComponentAdapterFactory(new MX4JComponentAdapterFactory()), parent);
    init(parent);
  }

  public ManageableContainer(ComponentAdapterFactory componentAdapterFactory, PicoContainer parent) {
    super(getComponentAdapterFactory(componentAdapterFactory), parent);
    init(parent);
  }

  public ManageableContainer(ComponentAdapterFactory componentAdapterFactory) {
    super(getComponentAdapterFactory(componentAdapterFactory));
    init(null);
  }

  private static ManageableComponentAdapterFactory getComponentAdapterFactory(ComponentAdapterFactory componentAdapterFactory) {
    ManageableComponentAdapterFactory factory = new ManageableComponentAdapterFactory(componentAdapterFactory);
    hack.set(factory);
    return factory;
  }

  private void init(PicoContainer parent) {
    // Yeah this is not pretty but a necessary evil to make it work
    ManageableComponentAdapterFactory factory = hack.get();
    factory.container = this;
    hack.set(null);

    // Reference the same mbean server that the parent has
    if (parent instanceof ManageableContainer) {
      ManagementContextImpl parentManagementContext = ((ManageableContainer)parent).managementContext;
      if (parentManagementContext != null) {
        managementContext = new ManagementContextImpl(parentManagementContext, new HashMap<String, String>());
      }
    }
  }

  public final MBeanServer getMBeanServer() {
    return managementContext != null ? managementContext.server : null;
  }

  @Override
  public ComponentAdapter registerComponent(ComponentAdapter componentAdapter) throws DuplicateComponentKeyRegistrationException {
    return super.registerComponent(componentAdapter);
  }

  public ComponentAdapter registerComponentInstance(Object componentKey, Object componentInstance) throws PicoRegistrationException {
    ComponentAdapter adapter = super.registerComponentInstance(componentKey, componentInstance);
    if (managementContext != null) {
      managementContext.register(componentInstance);
    }
    return adapter;
  }

  //

  public void printMBeanServer() {
    MBeanServer server = getMBeanServer();
    final Set names = server.queryNames(null, null);
    for (final Iterator i = names.iterator(); i.hasNext();) {
      ObjectName name = (ObjectName) i.next();
      try {
        MBeanInfo info = server.getMBeanInfo(name);
        MBeanAttributeInfo[] attrs = info.getAttributes();
        if (attrs == null)
          continue;
        for (int j = 0; j < attrs.length; j++) {
          if (attrs[j].isReadable()) {
            try {
              Object o = server.getAttribute(name, attrs[j].getName());
            } catch (Exception x) {
              x.printStackTrace();
            }
          }
        }
        MBeanOperationInfo[] methods = info.getOperations();
        for (int j = 0; j < methods.length; j++) {
          MBeanParameterInfo[] params = methods[j].getSignature();
          for (int k = 0; k < params.length; k++) {
          }
        }
      } catch (Exception x) {
        // x.printStackTrace(System. err);
      }
    }
  }
}
