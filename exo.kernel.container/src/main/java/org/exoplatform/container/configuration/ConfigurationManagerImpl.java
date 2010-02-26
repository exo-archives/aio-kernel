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
package org.exoplatform.container.configuration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import org.exoplatform.container.xml.Component;
import org.exoplatform.container.xml.Configuration;

/**
 * Jul 19, 2004
 * 
 * @author: Tuan Nguyen
 * @email: tuan08@users.sourceforge.net
 * @version: $Id: ConfigurationServiceImpl.java,v 1.8 2004/10/30 02:29:51 tuan08
 *           Exp $
 */
public class ConfigurationManagerImpl implements ConfigurationManager {
  final static public String  WAR_CONF_LOCATION  = "/WEB-INF";

  final static public String  LOG_DEBUG_PROPERTY = "org.exoplatform.container.configuration.debug";

  final static public boolean LOG_DEBUG          = System.getProperty(LOG_DEBUG_PROPERTY) != null;

  protected Configuration     configurations_;

  private ServletContext      scontext_;

  private ClassLoader         scontextClassLoader_;

  private String              contextPath        = null;

  /** The URL of the current document being unmarshalled. */
  private static final ThreadLocal<URL> currentURL = new ThreadLocal<URL>();

  /**
   * Returns the URL of the current document being unmarshalled or null.
   * @return the URL
   */
  public static URL getCurrentURL() {
    return currentURL.get();
  }

  public ConfigurationManagerImpl() {
  }

  public ConfigurationManagerImpl(ServletContext context) {
    scontext_ = context;
  }

  public ConfigurationManagerImpl(ClassLoader loader) {
    scontextClassLoader_ = loader;
  }

  public Configuration getConfiguration() {
    return configurations_;
  }

  public void addConfiguration(String url) throws Exception {
    if (url == null)
      return;
    addConfiguration(getURL(url));
  }

  public void addConfiguration(Collection urls) throws Exception {
    Iterator i = urls.iterator();
    while (i.hasNext()) {
      URL url = (URL) i.next();
      addConfiguration(url);
    }
  }

  public void addConfiguration(URL url) throws Exception {
    if (LOG_DEBUG)
      System.out.println("Add configuration " + url);
    if (url == null)
      return;
    try {
      contextPath = (new File(url.toString())).getParent() + "/";
      contextPath = contextPath.replaceAll("\\\\", "/");
    } catch (Exception e) {
      contextPath = null;
    }
    // Just to prevent some nasty bug to happen
    if (currentURL.get() != null) {
      throw new IllegalStateException("Would not expect that");
    } else {
      currentURL.set(url);
    }
    try {
      ConfigurationUnmarshaller unmarshaller = new ConfigurationUnmarshaller();
      Configuration conf = unmarshaller.unmarshall(url);

      if (configurations_ == null)
        configurations_ = conf;
      else
        configurations_.mergeConfiguration(conf);
      List urls = conf.getImports();
      if (urls != null) {
        for (int i = 0; i < urls.size(); i++) {
          String uri = (String) urls.get(i);
          URL urlObject = getURL(uri);
          if (urlObject != null) {
            conf = unmarshaller.unmarshall(urlObject);
            configurations_.mergeConfiguration(conf);
            if (LOG_DEBUG)
              System.out.println("\timport " + urlObject);
          } else {
            System.err.println("WARNING: Couldn't process the URL for " + uri
                + " configuration file ignored ");
          }
        }
      }
    } catch (Exception ex) {
      // System .err.println("Error: " + ex.getMessage());
      System.err.println("ERROR: cannot process the configuration " + url);
      ex.printStackTrace();
    } finally {
      currentURL.set(null);
    }
  }

  public void processRemoveConfiguration() {
    if (configurations_ == null)
      return;
    List list = configurations_.getRemoveConfiguration();
    if (list != null) {
      for (int i = 0; i < list.size(); i++) {
        String type = (String) list.get(i);
        configurations_.removeConfiguration(type);
      }
    }
  }

  public Component getComponent(String service) {
    return configurations_.getComponent(service);
  }

  public Component getComponent(Class clazz) throws Exception {
    return configurations_.getComponent(clazz.getName());
  }

  public Collection getComponents() {
    if (configurations_ == null)
      return null;
    return configurations_.getComponents();
  }

  public URL getResource(String url, String defaultURL) throws Exception {
    return null;
  }

  public URL getResource(String uri) throws Exception {
    return getURL(uri);
  }

  public InputStream getInputStream(String url, String defaultURL) throws Exception {
    if (url == null)
      url = defaultURL;
    return getInputStream(url);
  }

  public InputStream getInputStream(String uri) throws Exception {
    URL url = getURL(uri);
    if (url == null) {
      throw new IOException("Resource ("
          + uri
          + ") could not be found or the invoker doesn't have adequate privileges to get the resource");
    }
    return url.openStream();
  }

  public URL getURL(String url) throws Exception {
    if (url.startsWith("jar:")) {
      String path = removePrefix("jar:/", url);
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      return cl.getResource(path);
    } else if (url.startsWith("classpath:")) {
      String path = removePrefix("classpath:/", url);
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      return cl.getResource(path);
    } else if (url.startsWith("war:")) {
      String path = removePrefix("war:", url);
      if (scontext_ != null) {
        return scontext_.getResource(WAR_CONF_LOCATION + path);
      }
      if (scontextClassLoader_ != null) {
        return scontextClassLoader_.getResource(path);
      }
      throw new Exception("unsupport war uri in this configuration service");
    } else if (url.startsWith("file:")) {
      url = resolveSystemProperties(url);
      return new URL(url);
    } else if (url.indexOf(":") < 0 && contextPath != null) {
      return new URL(contextPath + url);
    }
    return null;
  }

  /**
   *
   * @param input the input
   * @return the resolved input
   */
  public static String resolveSystemProperties(String input) {
    final int NORMAL = 0;
    final int SEEN_DOLLAR = 1;
    final int IN_BRACKET = 2;
    if (input == null)
      return input;
    char[] chars = input.toCharArray();
    StringBuffer buffer = new StringBuffer();
    boolean properties = false;
    int state = NORMAL;
    int start = 0;
    for (int i = 0; i < chars.length; ++i) {
      char c = chars[i];
      if (c == '$' && state != IN_BRACKET)
        state = SEEN_DOLLAR;
      else if (c == '{' && state == SEEN_DOLLAR) {
        buffer.append(input.substring(start, i - 1));
        state = IN_BRACKET;
        start = i - 1;
      } else if (state == SEEN_DOLLAR)
        state = NORMAL;
      else if (c == '}' && state == IN_BRACKET) {
        if (start + 2 == i) {
          buffer.append("${}");
        } else {
          String value = null;
          String key = input.substring(start + 2, i);
          value = System.getProperty(key);
          if (value != null) {
            properties = true;
            buffer.append(value);
          }
        }
        start = i + 1;
        state = NORMAL;
      }
    }
    if (properties == false)
      return input;
    if (start != chars.length)
      buffer.append(input.substring(start, chars.length));
    return buffer.toString();

  }

  public boolean isDefault(String value) {
    return value == null || value.length() == 0 || "default".equals(value);
  }

  protected String removePrefix(String prefix, String url) {
    return url.substring(prefix.length(), url.length());
  }
}
