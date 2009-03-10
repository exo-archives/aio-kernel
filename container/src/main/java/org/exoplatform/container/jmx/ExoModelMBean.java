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

import org.exoplatform.management.ManagementContext;
import org.exoplatform.management.ManagementAware;
import org.exoplatform.management.jmx.annotations.NamingContext;

import javax.management.modelmbean.RequiredModelMBean;
import javax.management.modelmbean.ModelMBeanInfo;
import javax.management.modelmbean.InvalidTargetObjectTypeException;
import javax.management.MBeanException;
import javax.management.RuntimeOperationsException;
import javax.management.ObjectName;
import javax.management.MBeanServer;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanRegistration;
import java.util.Map;
import java.util.HashMap;

/**
 * A convenient subclass of {@link RequiredModelMBean) that routes the invocation of the interface
 * {@link MBeanRegistration} to the managed resource when it implements the method. 
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class ExoModelMBean extends RequiredModelMBean implements ManagementContext {

  /** . */
  private Object mr;

  /** . */
  private ManagementContextImpl parentContext;

  /** . */
  private ManagementContextImpl context;

  public ExoModelMBean(
    ManagementContextImpl parentContext,
    Object mr,
    ModelMBeanInfo mbi) throws MBeanException, RuntimeOperationsException, InstanceNotFoundException, InvalidTargetObjectTypeException {
    super(mbi);

    //
    this.parentContext = parentContext;
    this.mr = mr;

    //
    setManagedResource(mr, "ObjectReference");
  }

  @Override
  public ObjectName preRegister(MBeanServer server, ObjectName name) throws Exception {
    name = super.preRegister(server, name);

    //
    if (mr instanceof MBeanRegistration) {
      ((MBeanRegistration)mr).preRegister(server, name);
    }

    //
    return name;
  }

  @Override
  public void postRegister(Boolean registrationDone) {
    super.postRegister(registrationDone);

    //
    PropertiesInfo info = PropertiesInfo.resolve(mr.getClass(), NamingContext.class);

    //
    Map<String, String> scopingProperties = new HashMap<String, String>();
    if (info != null) {
      for (PropertyInfo property : info.getProperties()) {
        scopingProperties.put(property.getKey(), property.resolveValue(mr));
      }
    }

    //
    context = new ManagementContextImpl(parentContext, scopingProperties);

    //
    if (mr instanceof ManagementAware) {
      ((ManagementAware)mr).setContext(this);
    }

    //
    if (mr instanceof MBeanRegistration) {
      ((MBeanRegistration)mr).postRegister(registrationDone);
    }
  }

  @Override
  public void preDeregister() throws Exception {
    if (mr instanceof MBeanRegistration) {
      ((MBeanRegistration)mr).preDeregister();
    }

    //
    if (mr instanceof ManagementAware) {
      ((ManagementAware)mr).setContext(null);
    }

    //
    super.preDeregister();
  }

  @Override
  public void postDeregister() {
    if (mr instanceof MBeanRegistration) {
      ((MBeanRegistration)mr).postDeregister();
    }

    //
    super.postDeregister();
  }

  //

  public Object getManagedResource() {
    return mr;
  }

  //

  public void register(Object o) throws IllegalArgumentException, NullPointerException {
    context.register(o);
  }

  public void unregister(Object o) throws IllegalArgumentException, NullPointerException {
    context.unregister(o);
  }
}
