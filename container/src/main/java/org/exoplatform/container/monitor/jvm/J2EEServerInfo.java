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
package org.exoplatform.container.monitor.jvm;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Nov 8, 2004
 * @version $Id: J2EEServerInfo.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class J2EEServerInfo {
  private String   serverName_;

  private String   serverHome_;

  private String   exoConfDir_;

  protected String sharedLibDirecotry_;

  protected String appDeployDirecotry_;

  public J2EEServerInfo() {
    String jonasHome = System.getProperty("jonas.base");
    String jbossHome = System.getProperty("jboss.home.dir");
    String jettyHome = System.getProperty("jetty.home");
    String websphereHome = System.getProperty("was.install.root");
    String weblogicHome = System.getProperty("wls.home");
    String catalinaHome = System.getProperty("catalina.home");
    String testHome = System.getProperty("maven.exoplatform.dir");

    if (jonasHome != null) {
      serverName_ = "jonas";
      serverHome_ = jonasHome;
      exoConfDir_ = serverHome_ + "/exo-conf";
    } else if (jbossHome != null) {
      serverName_ = "jboss";
      serverHome_ = jbossHome;
      exoConfDir_ = serverHome_ + "/exo-conf";
    } else if (jettyHome != null) {
      serverName_ = "jetty";
      serverHome_ = jettyHome;
      exoConfDir_ = serverHome_ + "/exo-conf";
    } else if (websphereHome != null) {
      serverName_ = "websphere";
      serverHome_ = websphereHome;
      exoConfDir_ = serverHome_ + "/exo-conf";
    } else if (weblogicHome != null) {
      serverName_ = "weblogic";
      serverHome_ = weblogicHome;
      exoConfDir_ = serverHome_ + "/exo-conf";
      // Catalina has to be processed at the end as other servers may embed it
    } else if (catalinaHome != null) {
      serverName_ = "tomcat";
      serverHome_ = catalinaHome;
      exoConfDir_ = serverHome_ + "/exo-conf";
    } else if (testHome != null) {
      serverName_ = "test";
      serverHome_ = testHome;
      exoConfDir_ = serverHome_ + "/exo-conf";
    } else {
      // throw new UnsupportedOperationException("unknown server platform") ;
      serverName_ = "standalone";
      serverHome_ = System.getProperty("user.dir");
      exoConfDir_ = serverHome_ + "/exo-conf";
    }
    serverHome_ = serverHome_.replace('\\', '/');
    exoConfDir_ = exoConfDir_.replace('\\', '/');
  }

  public String getServerName() {
    return serverName_;
  }

  public String getServerHome() {
    return serverHome_;
  }

  public String getExoConfigurationDirectory() {
    return exoConfDir_;
  }

  public String getSharedLibDirectory() {
    return sharedLibDirecotry_;
  }

  public String getApplicationDeployDirectory() {
    return appDeployDirecotry_;
  }
}
