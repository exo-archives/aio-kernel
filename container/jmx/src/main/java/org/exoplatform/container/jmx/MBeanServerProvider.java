/*
 * Copyright (C) 2003-2008 eXo Platform SAS.
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.exoplatform.container.ExoContainer;
import org.exoplatform.container.ExoContainerContext;
import org.exoplatform.container.PortalContainer;
import org.exoplatform.container.RootContainer;
import org.exoplatform.container.StandaloneContainer;
import org.exoplatform.container.xml.InitParams;
import org.exoplatform.container.xml.PropertiesParam;
import org.exoplatform.container.xml.ValueParam;
import org.picocontainer.Startable;

/**
 * Created by The eXo Platform SAS
 * Author : Nicolas Filotto
 *          nicolas.filotto@exoplatform.com
 * 9 déc. 2008  
 */
public class MBeanServerProvider implements Startable {

  /**
   * Logger
   */
  private static final Log LOG = LogFactory.getLog(ExoContainer.class);
  
  /**
   * The protocol parameter name
   */
  private static final String PROTOCOL_PARAM_NAME = "protocol";

  /**
   * The host parameter name
   */
  private static final String HOST_PARAM_NAME = "host";

  /**
   * The port parameter name
   */
  private static final String PORT_PARAM_NAME = "port";

  /**
   * The path prefix parameter name
   */
  private static final String PATH_PREFIX_PARAM_NAME = "path-prefix";

  /**
   * The name separator parameter name
   */
  private static final String NAME_SEPARATOR_PARAM_NAME = "name-separator";

  /**
   * The environment parameter name
   */
  private static final String ENVIRONMENT_PARAM_NAME = "environment";
  
  /**
   * The domain name's pattern of the workspace container 
   * "jcrws" + name + "at" + repositoryContainer.getName() + "mx"
   */
  private static final Pattern WC_DOMAIN_NAME_PATTERN = Pattern.compile("jcrws(.*)at(.*)mx");
  
  /**
   * The domain name's pattern of the repository container 
   * "jcrrep" + getName() + "mx"
   */
  private static final Pattern RC_DOMAIN_NAME_PATTERN = Pattern.compile("jcrrep(.*)mx");
  
  private String protocol;
  private String host;
  private int    port;
  private String path_prefix;
  private String name_separator;  
  private Map<String, ?> environment;
  
  private static final List<JMXConnectorServer> SERVERS = new ArrayList<JMXConnectorServer>(); 
  
  public MBeanServerProvider(InitParams params) {
    if (params == null) {
      LOG.error("The protocol has not been defined");
    } else {
      final ValueParam parameter = params.getValueParam(PROTOCOL_PARAM_NAME);
      if (parameter == null) {
        LOG.error("The protocol has not been defined");        
      } else {
        this.protocol = parameter.getValue();
        this.host = getValue(params, HOST_PARAM_NAME);
        final String sPort = getValue(params, PORT_PARAM_NAME);
        if (sPort != null && sPort.length() > 0) {
          try {
            this.port = Integer.valueOf(sPort);
          } catch (NumberFormatException e) {
            LOG.error("The port has not been defined properly", e);
          }          
        }
        this.path_prefix = getValue(params, PATH_PREFIX_PARAM_NAME);
        this.name_separator = getValue(params, NAME_SEPARATOR_PARAM_NAME);
        final PropertiesParam properties = params.getPropertiesParam(ENVIRONMENT_PARAM_NAME);
        this.environment = properties == null ? null : properties.getProperties();
      }
    }
  }
  
  /**
   * Gives the parameter value from its name and the InitParams 
   */
  private String getValue(InitParams params, String paramName) {
    final ValueParam parameter = params.getValueParam(paramName);
    return parameter == null ? null : parameter.getValue();
  }
  
  /**
   * Starts the JMXConnectorServer instances
   */  
  public void start() {
    if (protocol == null) {
      return;
    }
    bind(ExoContainerContext.getTopContainer());
    bind(ExoContainerContext.getCurrentContainer());
    final ArrayList<MBeanServer> servers = MBeanServerFactory.findMBeanServer(null);
    for (MBeanServer server : servers) {
      bind(server);
    }
  }

  /**
   * Stops the JMXConnectorServer instances
   */
  public void stop() {
    synchronized (SERVERS) {
      for (JMXConnectorServer server : SERVERS) {
        try {
          server.stop();
        } catch (IOException e) {
          LOG.error("The server " + server.getMBeanServer().getDefaultDomain() + " could not be stopped", e);
        }
      }
      SERVERS.clear();
    }
  }

  /**
   * Gives the full binding path for the given ExoContainer instance
   */
  private String getURL(ExoContainer container) {
    final StringBuffer result = getURLPrefix();
    result.append(getContainerPath(container));
    return result.toString();
  }
  
  /**
   * Gives the full binding path for the given MBeanServer instance
   */
  private String getURL(MBeanServer mbServer) {
    final String path = getMBeanServerPath(mbServer);
    if (path == null) {
      return null;
    }
    final StringBuffer result = getURLPrefix();
    result.append(path);
    return result.toString();
  }
  
  /**
   * Gives the URL prefix depending on the component configuration
   */
  private StringBuffer getURLPrefix() {
    final StringBuffer result = new StringBuffer(128);
    result.append("service:jmx:");
    result.append(protocol);
    result.append("://");
    if (host != null) {
      result.append(host);
      if (port > 0) {
        result.append(':');
        result.append(port);
      }
    }
    result.append('/');
    if (path_prefix != null) {
      result.append(path_prefix);      
    }
    return result;
  }
  
  /**
   * Gives the ExoContainer binding path
   */  
  private String getContainerPath(ExoContainer container) {
    if (container instanceof RootContainer || container instanceof StandaloneContainer) {
      return "root";
    } else if (container instanceof PortalContainer) {
      return "root" + name_separator + container.getContext().getName();
    } else {
      throw new RuntimeException("Container of type " + container.getClass() + " not supported");
    }
  }

  /**
   * Gives the MBeanServer's binding path
   */
  private String getMBeanServerPath(MBeanServer mbServer) {
    final String domain = mbServer.getDefaultDomain();
    if (domain != null) {
      // "jcrws" + name + "at" + repositoryContainer.getName() + "mx"
      Matcher matcher = WC_DOMAIN_NAME_PATTERN.matcher(domain);
      if (matcher.find()) {
        return getContainerPath(ExoContainerContext.getCurrentContainer()) + name_separator
            + matcher.group(2) + name_separator + matcher.group(1);  
      } else {
        // "jcrrep" + getName() + "mx"
         matcher = RC_DOMAIN_NAME_PATTERN.matcher(domain);
         if (matcher.find()) {
           return getContainerPath(ExoContainerContext.getCurrentContainer()) + name_separator
              + matcher.group(1);             
         }
      }
    }
    return null;
  }
  
  /**
   * Binds the given ExoContainer instance
   */
  private void bind(ExoContainer container) {
    final String url = getURL(container);
    bind(container.getMBeanServer(), url);
  }
  
  /**
   * Binds the given MBeanServer instance
   */
  private void bind(MBeanServer server) {
    final String url = getURL(server);
    if (url == null) {
      return;
    }
    bind(server, url);
  }
  
  /**
   * Binds the given MBeanServer to the given url 
   */
  private void bind(MBeanServer server, String url) {
    if (isBound(server)) {
      if (LOG.isDebugEnabled()) LOG.debug("The server " + server.getDefaultDomain() + " has already been bound");
      return;
    }
    if (LOG.isInfoEnabled()) LOG.info("Defining the server " + server.getDefaultDomain() + " at " + url);
    try {
      JMXServiceURL address = new JMXServiceURL(url);
      JMXConnectorServer cs = JMXConnectorServerFactory.newJMXConnectorServer(address, environment,
          server);
      cs.start();
      synchronized (SERVERS) {
        SERVERS.add(cs);
      }
    } catch (Exception e) {    
      LOG.error("The server " + server.getDefaultDomain() + " could not be started", e);
    }        
  }
  
  /**
   * Checks if the MBeanServer has already been bound
   */
  private boolean isBound(MBeanServer mbServer) {
    synchronized (SERVERS) {
      for (JMXConnectorServer server : SERVERS) {
        if (server.getMBeanServer() == mbServer) {
          return true;
        }
      }
      return false;      
    }
  }
}
