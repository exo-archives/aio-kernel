/**
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.
 * Please look at license.txt in info directory for more license detail.
 **/
package org.exoplatform.container;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exoplatform.container.component.ComponentLifecyclePlugin;
import org.exoplatform.container.configuration.ConfigurationManager;
import org.exoplatform.container.jmx.ExoContainerMBean;
import org.exoplatform.container.util.ContainerUtil;
import org.exoplatform.container.xml.Component;
import org.exoplatform.container.xml.InitParams;
import org.picocontainer.PicoContainer;
import org.picocontainer.defaults.ComponentAdapterFactory;
import org.picocontainer.defaults.DefaultPicoContainer;


/**
 * Created by The eXo Platform SAS
 * Author : Tuan Nguyen
 *          tuan08@users.sourceforge.net
 * Date: Jul 18, 2004
 * Time: 12:15:28 AM
 */
public class ExoContainer extends DefaultPicoContainer {
  
  Log log = LogFactory.getLog(ExoContainer.class);
  
  private Map<String, ComponentLifecyclePlugin> componentLifecylePlugin_ = new HashMap<String, ComponentLifecyclePlugin>();
  private List<ContainerLifecyclePlugin> containerLifecyclePlugin_ = new ArrayList<ContainerLifecyclePlugin>();
  protected ExoContainerContext context;
  
  public ExoContainer(PicoContainer parent) {
    super(parent)  ;
    context = new ExoContainerContext(this);
    context.setName(this.getClass().getName());
    registerComponentInstance(context);
  }
  
  public ExoContainer(ComponentAdapterFactory factory, PicoContainer parent) {
    super(factory, parent)  ;
    context = new ExoContainerContext(this);
    context.setName(this.getClass().getName());
    registerComponentInstance(context);
  }
  
  public ExoContainerContext getContext() { return context; }

  public void  initContainer() throws Exception {
    ConfigurationManager  manager = 
      (ConfigurationManager) getComponentInstanceOfType(ConfigurationManager.class) ;
    ContainerUtil.addContainerLifecyclePlugin(this, manager) ;
    ContainerUtil.addComponentLifecyclePlugin(this, manager) ;
    for(ContainerLifecyclePlugin plugin : containerLifecyclePlugin_) {
      plugin.initContainer(this) ;
    }
    ContainerUtil.addComponents(this, manager) ;
  }
  
  public void  startContainer() throws Exception {
    for(ContainerLifecyclePlugin plugin : containerLifecyclePlugin_) {
      plugin.startContainer(this) ;
    }
  }
  
  public void  stopContainer() throws Exception {
    for(ContainerLifecyclePlugin plugin : containerLifecyclePlugin_) {
      plugin.stopContainer(this) ;
    }
  }
  
  public void  destroyContainer() throws Exception {
    for(ContainerLifecyclePlugin plugin : containerLifecyclePlugin_) {
      plugin.destroyContainer(this) ;
    }
  }
  
  public MBeanServer getMBeanServer() {  
    throw new UnsupportedOperationException("This container do not support jmx management") ; 
  }
  
  public void addComponentLifecylePlugin(ComponentLifecyclePlugin plugin) {
    List<String> list = plugin.getManageableComponents();
    for(String component : list)
    componentLifecylePlugin_.put(component, plugin) ;
  }
  
  public void addContainerLifecylePlugin(ContainerLifecyclePlugin plugin) {
    containerLifecyclePlugin_.add(plugin) ;
  }
  
  public <T> T createComponent(Class<T> clazz) throws Exception {
    return createComponent(clazz, null) ;
  }
  
  public <T> T createComponent(Class<T> clazz, InitParams params) throws Exception {
    log.info(clazz.getName() + " " + ((params!=null)?params:"") + " added to " + getContext().getName());    
    Constructor<?>[] constructors = ContainerUtil.getSortedConstructors(clazz) ;
    Class<?> unknownParameter = null;
    for(int k = 0; k < constructors.length; k++) {
      Constructor<?> constructor =  constructors[k];
      Class<?>[] parameters = constructor.getParameterTypes() ;
      Object[] args = new Object[parameters.length] ;
      boolean satisfied = true ;
      for (int i = 0; i < args.length; i++) {
        if(parameters[i].equals(InitParams.class)) {
          args[i] = params ;
        } else {
          args[i] = getComponentInstanceOfType(parameters[i]) ;
          if (args[i] == null) {
            satisfied = false ;
            unknownParameter = parameters[i];
            break ;
          }
        }
      }
      if(satisfied) return  clazz.cast(constructor.newInstance(args)) ;
    }
    throw new Exception("Cannot find a satisfying constructor for " + clazz 
                        + " with parameter "+unknownParameter) ;
  }
  
  public void manageMBean(Component component, String componentKey, Object bean) {
    ObjectName name = null;
    MBeanServer mbeanServer = getMBeanServer();

   Object mbean = null;

    synchronized (mbeanServer) {
      try {
        name = asObjectName(component, componentKey);
        mbean = new ExoContainerMBean(bean);
        mbeanServer.registerMBean(mbean, name);
      } catch (InstanceAlreadyExistsException e) {
        try {

          mbeanServer.unregisterMBean(name);
          mbeanServer.registerMBean(mbean, name);

        } catch (Exception e1) {
          throw new RuntimeException("Failed to register MBean '" + name + " due to "
              + e.getMessage(), e);
        }
      } catch (Exception e) {
        throw new RuntimeException("Failed to register MBean '" + name + " due to "
            + e.getMessage(), e);
      }

    }
  }
  
  /**
   * Ensures that the given componentKey is converted to a JMX ObjectName
   * 
   * @param componentKey
   * @return an ObjectName based on the given componentKey
   */
  private static ObjectName asObjectName(Component component, String componentKey) throws MalformedObjectNameException {
   String name = null ;
   if(component != null && component.getJMXName() != null) {
     name = component.getJMXName() ;
   }
    // Fix, so it works under WebSphere ver. 5
    if (name == null  || name.indexOf(':') == -1) {
      name = "component:type=" + componentKey ;
    }
    return new ObjectName(name);
  }
  
  public  void printMBeanServer() {
    MBeanServer server = getMBeanServer() ;
    final Set names = server.queryNames(null, null);
    for (final Iterator i = names.iterator(); i.hasNext();) {
      ObjectName name = (ObjectName) i.next();
      try {
        MBeanInfo info = server.getMBeanInfo(name);
        MBeanAttributeInfo[] attrs = info.getAttributes();
        if (attrs == null) continue;
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
          MBeanParameterInfo[] params =  methods[j].getSignature() ;
          for(int k = 0 ; k < params.length; k++) {
          }
        }
      } catch (Exception x) {
        //x.printStackTrace(System. err);
      }
    }
  }
}