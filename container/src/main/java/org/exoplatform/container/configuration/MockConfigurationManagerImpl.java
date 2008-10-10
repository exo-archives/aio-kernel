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

import java.net.URL;

import javax.servlet.ServletContext;

/**
 * Jul 19, 2004
 * 
 * @author: Tuan Nguyen
 * @email: tuan08@users.sourceforge.net
 * @version: $Id: MockConfigurationManagerImpl.java 5799 2006-05-28 17:55:42Z
 *           geaz $
 */
public class MockConfigurationManagerImpl extends ConfigurationManagerImpl {
  private String confDir_;

  public MockConfigurationManagerImpl(ServletContext context) throws Exception {
    super(context);
    confDir_ = System.getProperty("mock.portal.dir") + "/WEB-INF";
  }

  public URL getURL(String uri) throws Exception {
    if (uri.startsWith("jar:")) {
      String path = removePrefix("jar:/", uri);
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      return cl.getResource(path);
    } else if (uri.startsWith("classpath:")) {
      String path = removePrefix("classpath:/", uri);
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      return cl.getResource(path);
    } else if (uri.startsWith("war:")) {
      String path = removePrefix("war:", uri);
      URL url = new URL("file:" + confDir_ + path);
      return url;
    } else if (uri.startsWith("file:")) {
      return new URL(uri);
    }
    return null;
  }
}
