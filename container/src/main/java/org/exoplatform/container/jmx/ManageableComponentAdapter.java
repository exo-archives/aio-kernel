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

import org.picocontainer.ComponentAdapter;
import org.picocontainer.PicoContainer;
import org.picocontainer.PicoInitializationException;
import org.picocontainer.PicoIntrospectionException;
import org.picocontainer.PicoVisitor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class ManageableComponentAdapter implements ComponentAdapter {

  /** . */
  private Log log = LogFactory.getLog(ManageableComponentAdapter.class);

  /** . */
  private ComponentAdapter delegate;

  /** . */
  private final ManageableContainer container;

  /** . */
  private volatile boolean registered = false;

  public ManageableComponentAdapter(ManageableContainer container, ComponentAdapter delegate) {
    this.delegate = delegate;
    this.container = container;
  }

  public Object getComponentKey() {
    return delegate.getComponentKey();
  }

  public Class getComponentImplementation() {
    return delegate.getComponentImplementation();
  }

  public Object getComponentInstance(PicoContainer pico) throws PicoInitializationException, PicoIntrospectionException {
    Object instance = delegate.getComponentInstance(pico);

    //
    if (!registered) {
      registered = true;

      //
      if (container.managementContext != null) {
        log.debug("==> add " + instance + " to a mbean server");
        container.managementContext.register(instance);
      }
    }
    return instance;
  }

  public void verify(PicoContainer container) throws PicoIntrospectionException {
    delegate.verify(container);
  }

  public void accept(PicoVisitor visitor) {
    delegate.accept(visitor);
  }
}
