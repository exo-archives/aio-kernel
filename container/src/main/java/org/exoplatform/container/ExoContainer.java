/**
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.
 * Please look at license.txt in info directory for more license detail.
 **/
package org.exoplatform.container;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.picocontainer.PicoContainer;
import org.picocontainer.MutablePicoContainer;
import org.picocontainer.defaults.ComponentAdapterFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.exoplatform.container.component.ComponentLifecyclePlugin;
import org.exoplatform.container.configuration.ConfigurationManager;
import org.exoplatform.container.jmx.ManageableContainer;
import org.exoplatform.container.jmx.ManagementContextImpl;
import org.exoplatform.container.util.ContainerUtil;
import org.exoplatform.container.xml.InitParams;
import org.exoplatform.management.ManagementContext;

/**
 * Created by The eXo Platform SAS Author : Tuan Nguyen
 * tuan08@users.sourceforge.net Date: Jul 18, 2004 Time: 12:15:28 AM
 */
public class ExoContainer extends ManageableContainer {

  Log                                           log                       = LogFactory.getLog(ExoContainer.class);

  private Map<String, ComponentLifecyclePlugin> componentLifecylePlugin_  = new HashMap<String, ComponentLifecyclePlugin>();

  private List<ContainerLifecyclePlugin>        containerLifecyclePlugin_ = new ArrayList<ContainerLifecyclePlugin>();

  protected ExoContainerContext                 context;

  protected PicoContainer parent;

  public ExoContainer(ManagementContextImpl managementContext) {
    super(managementContext);
    context = new ExoContainerContext(this);
    context.setName(this.getClass().getName());
    registerComponentInstance(context);
    this.parent = null;
  }

  public ExoContainer(PicoContainer parent) {
    super(parent);
    context = new ExoContainerContext(this);
    context.setName(this.getClass().getName());
    registerComponentInstance(context);
    this.parent = parent;
  }

  public ExoContainer(ComponentAdapterFactory factory, PicoContainer parent) {
    super(factory, parent);
    context = new ExoContainerContext(this);
    context.setName(this.getClass().getName());
    registerComponentInstance(context);
    this.parent = parent;
  }

  public ExoContainerContext getContext() {
    return context;
  }

  public void initContainer() throws Exception {
    ConfigurationManager manager = (ConfigurationManager) getComponentInstanceOfType(ConfigurationManager.class);
    ContainerUtil.addContainerLifecyclePlugin(this, manager);
    ContainerUtil.addComponentLifecyclePlugin(this, manager);
    for (ContainerLifecyclePlugin plugin : containerLifecyclePlugin_) {
      plugin.initContainer(this);
    }
    ContainerUtil.addComponents(this, manager);
  }

  public void startContainer() throws Exception {
    for (ContainerLifecyclePlugin plugin : containerLifecyclePlugin_) {
      plugin.startContainer(this);
    }
  }

  public void stopContainer() throws Exception {
    for (ContainerLifecyclePlugin plugin : containerLifecyclePlugin_) {
      plugin.stopContainer(this);
    }
  }

  public void destroyContainer() throws Exception {
    for (ContainerLifecyclePlugin plugin : containerLifecyclePlugin_) {
      plugin.destroyContainer(this);
    }
  }

  public void addComponentLifecylePlugin(ComponentLifecyclePlugin plugin) {
    List<String> list = plugin.getManageableComponents();
    for (String component : list)
      componentLifecylePlugin_.put(component, plugin);
  }

  public void addContainerLifecylePlugin(ContainerLifecyclePlugin plugin) {
    containerLifecyclePlugin_.add(plugin);
  }

  public <T> T createComponent(Class<T> clazz) throws Exception {
    return createComponent(clazz, null);
  }

  public <T> T createComponent(Class<T> clazz, InitParams params) throws Exception {
    if (log.isDebugEnabled())
      log.debug(clazz.getName() + " " + ((params != null) ? params : "") + " added to "
          + getContext().getName());
    Constructor<?>[] constructors = new Constructor<?>[0];
    try {
      constructors = ContainerUtil.getSortedConstructors(clazz);
    }
    catch (NoClassDefFoundError err) {
      throw new Exception("Cannot resolve constructor for class " + clazz.getName(), err);
    }
    Class<?> unknownParameter = null;
    for (int k = 0; k < constructors.length; k++) {
      Constructor<?> constructor = constructors[k];
      Class<?>[] parameters = constructor.getParameterTypes();
      Object[] args = new Object[parameters.length];
      boolean satisfied = true;
      for (int i = 0; i < args.length; i++) {
        if (parameters[i].equals(InitParams.class)) {
          args[i] = params;
        } else {
          args[i] = getComponentInstanceOfType(parameters[i]);
          if (args[i] == null) {
            satisfied = false;
            unknownParameter = parameters[i];
            break;
          }
        }
      }
      if (satisfied)
        return clazz.cast(constructor.newInstance(args));
    }
    throw new Exception("Cannot find a satisfying constructor for " + clazz + " with parameter "
        + unknownParameter);
  }
}
