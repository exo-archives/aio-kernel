/**
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.
 * Please look at license.txt in info directory for more license detail.
 **/
package org.exoplatform.container;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanParameterInfo;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
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
import org.picocontainer.ComponentAdapter;
import org.picocontainer.PicoContainer;
import org.picocontainer.defaults.ComponentAdapterFactory;

/**
 * Created by The eXo Platform SAS Author : Tuan Nguyen
 * tuan08@users.sourceforge.net Date: Jul 18, 2004 Time: 12:15:28 AM
 */
public class ExoContainer extends CachingContainer {

  /**
   * Logger
   */
  private static final Log                      LOG                             = LogFactory.getLog(ExoContainer.class);
  
  /**
   * Default domain name
   */
  private static final String                   DEFAULT_DOMAIN                  = "component";
  
  /**
   * Default domain name prefix
   */
  private static final String                   DEFAULT_DOMAIN_PREFIX           = "eXo.";
  
  /**
   * Use existing MBean server parameter name 
   */
  private static final String                   USE_EXISTING_SERVER_PARAM       = "org.exoplatform.container.jmx.useExistingServer";
  
  /**
   * Find existing MBean server parameter name
   */
  private static final String                   FIND_EXISTING_SERVER_PARAM           = "org.exoplatform.container.jmx.findExistingServer";

  /**
   * Find existing MBean server from default domain parameter name
   */
  private static final String                   FIND_EXISTING_SERVER_FROM_DEFAULT_DOMAIN_PARAM     = "org.exoplatform.container.jmx.findExistingServerFromDefaultDomain";
  
  /**
   * Indicates either a local MBean server has to be used or not
   */
  private final static boolean                  USE_EXISTING_SERVER        = System.getProperty(USE_EXISTING_SERVER_PARAM) != null;
  
  /**
   * The local MBean server
   */
  private static volatile MBeanServer           EXISTING_MBEAN_SERVER;
  
  private final Map<String, ComponentLifecyclePlugin> componentLifecylePlugin_  = new HashMap<String, ComponentLifecyclePlugin>();

  private final List<ContainerLifecyclePlugin>        containerLifecyclePlugin_ = new ArrayList<ContainerLifecyclePlugin>();

  protected final ExoContainerContext                 context;

  public ExoContainer(PicoContainer parent) {
    super(parent);
    context = new ExoContainerContext(this);
    context.setName(this.getClass().getName());
    registerComponentInstance(context);
  }

  public ExoContainer(ComponentAdapterFactory factory, PicoContainer parent) {
    super(factory, parent);
    context = new ExoContainerContext(this);
    context.setName(this.getClass().getName());
    registerComponentInstance(context);
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

  public MBeanServer getMBeanServer() {
    throw new UnsupportedOperationException("This container do not support jmx management");
  }

  private MBeanServer getExistingServer() {
    String agentId = System.getProperty(FIND_EXISTING_SERVER_PARAM);
    if (agentId != null) {      
      if (agentId.length() == 0) {
        if (LOG.isInfoEnabled()) LOG.info("No agent id has been given to retrieve the local MBean server.");
        agentId = null;
      } else if (LOG.isInfoEnabled()) {
        LOG.info("Trying to retrieve the local MBean server with agent id '" + agentId + "'.");
      }
      final ArrayList<MBeanServer> servers = MBeanServerFactory.findMBeanServer(agentId);
      if (servers != null && servers.size() > 0) {
        final String domain = System.getProperty(FIND_EXISTING_SERVER_FROM_DEFAULT_DOMAIN_PARAM);
        if (domain == null) {
          if (servers.size() > 1) {
            if (LOG.isWarnEnabled()) LOG.warn("More than one MBean server has been found" + (agentId != null ? " (Agent Id: " + agentId + ")" : "") + ", the first MBean server will be used.");
            for (MBeanServer server : servers) {
              if (LOG.isInfoEnabled()) LOG.info("Local MBean server default domain name '" + server.getDefaultDomain() + "'.");
            }
          }
          if (LOG.isInfoEnabled()) LOG.info("The default domain name of the selected MBean server is '" + servers.get(0).getDefaultDomain() + "'.");
          return servers.get(0);          
        } else {
          if (LOG.isInfoEnabled()) LOG.info("Trying to retrieve the local MBean server with default domain name '" + domain + "'.");          
          for (MBeanServer server : servers) {
            if (domain.equals(server.getDefaultDomain())) {
              return server;
            }
          }
        }
      } 
      if (LOG.isWarnEnabled()) LOG.warn("No MBean server has been found with the given criteria, the platform MBean server will be used");
    }
    return ManagementFactory.getPlatformMBeanServer();
  }
  
  protected MBeanServer createMBeanServer(String domain)  {
    if (USE_EXISTING_SERVER) {
      return null;
    } else {
      return MBeanServerFactory.createMBeanServer(domain);
    }
  }
  
  private MBeanServer getCurrentMBeanServer() {
    if (USE_EXISTING_SERVER) {
      MBeanServer server = EXISTING_MBEAN_SERVER;
      if (server == null) {
        synchronized (ExoContainer.class) {
          server = EXISTING_MBEAN_SERVER;
          if (server == null) {
            if (LOG.isInfoEnabled()) LOG.info("An existing MBean server will be used.");
            EXISTING_MBEAN_SERVER = getExistingServer();            
          }
        }
      }
      return EXISTING_MBEAN_SERVER;
    }
    return getMBeanServer();
  }
  
  public String getMBeanContext() {
    return null;
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
    if (LOG.isDebugEnabled())
      LOG.debug(clazz.getName() + " " + ((params != null) ? params : "") + " added to "
          + getContext().getName());
    Constructor<?>[] constructors = ContainerUtil.getSortedConstructors(clazz);
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

  /**
   * Unregister the MBean when the corresponding component is unregistered 
   */
  public synchronized ComponentAdapter unregisterComponent(Object componentKey) {
	unregisterMBean(componentKey);  
	return super.unregisterComponent(componentKey);
  }
  
  private void unregisterMBean(Object key) {
	String componentKey = null;
	if (key instanceof String) {
		componentKey = (String) key;
	} else if (key instanceof Class) {
	    componentKey = ((Class<?>) key).getName();
	} else {
		return;
	}
	ObjectName name = null;  
	MBeanServer mbeanServer = getCurrentMBeanServer();
	synchronized (mbeanServer) {
		try {
			name = asObjectName(null, componentKey);
			if (mbeanServer.isRegistered(name)) {
				mbeanServer.unregisterMBean(name);				
			}
		} catch (Exception e) {
	        if (LOG.isWarnEnabled()) LOG.warn("The MBean '" + name + "' could not be unregistered.", e);
		}
	}
  }
  
  public void manageMBean(Component component, String componentKey, Object bean) {
    ObjectName name = null;
    MBeanServer mbeanServer = getCurrentMBeanServer();

    ExoContainerMBean mbean = null;

    synchronized (mbeanServer) {
      try {
        name = asObjectName(component, componentKey);
        mbean = new ExoContainerMBean(bean);
        // Discard all the unnecessary MBeans 
        if (mbean.canBeMonitored()) {
        	if (mbeanServer.isRegistered(name)) {
                if (LOG.isWarnEnabled()) LOG.warn("The MBean '" + name + "' has already been registered.");
                mbeanServer.unregisterMBean(name);        		
        	}
	        mbeanServer.registerMBean(mbean, name);
        } else if (LOG.isDebugEnabled()) {
          LOG.debug("The MBean '" + name + "' cannot be monitored since there is nothing to monitor.");
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
  private ObjectName asObjectName(Component component, String componentKey) throws MalformedObjectNameException {
    String name = null;
    if (component != null) {
      name = component.getJMXName();
    }
    String mbeanContext, domainPrefix;
    if (USE_EXISTING_SERVER) {
      mbeanContext = getMBeanContext();
      domainPrefix = DEFAULT_DOMAIN_PREFIX;
    } else {
      mbeanContext = "";
      domainPrefix = "";
    }
    if (mbeanContext == null) {
      mbeanContext = "";
    } else if (mbeanContext.length() > 0) {
      mbeanContext = "," + mbeanContext;      
    }
    // Fix, so it works under WebSphere ver. 5
    if (name == null || name.indexOf('=') == -1 || name.indexOf(':') == -1) {
      name = DEFAULT_DOMAIN + ":type=" + componentKey;
    }
    return new ObjectName(domainPrefix + name + mbeanContext);
  }

  public void printMBeanServer() {
    MBeanServer server = getCurrentMBeanServer();
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
