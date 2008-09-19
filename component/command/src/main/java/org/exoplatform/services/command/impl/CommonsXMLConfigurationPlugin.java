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
package org.exoplatform.services.command.impl;

import java.net.URL;

import org.apache.commons.chain.config.ConfigParser;

import org.exoplatform.container.component.BaseComponentPlugin;
import org.exoplatform.container.configuration.ConfigurationManager;
import org.exoplatform.container.xml.InitParams;
import org.exoplatform.container.xml.ValueParam;

/**
 * Created by The eXo Platform SAS.<br/> The plugin for configuring
 * command/chain catalog using "native" Apache Commons Chain's XML file
 * 
 * @author <a href="mailto:gennady.azarenkov@exoplatform.com">Gennady
 *         Azarenkov</a>
 * @version $Id: CommonsXMLConfigurationPlugin.java 9846 2006-10-27 11:03:37Z
 *          geaz $
 */

public class CommonsXMLConfigurationPlugin extends BaseComponentPlugin {

  // protected Catalog defaultCatalog;

  public CommonsXMLConfigurationPlugin(InitParams params, ConfigurationManager configurationManager) throws Exception {
    ValueParam confFile = params.getValueParam("config-file");
    if (confFile != null) {
      String path = confFile.getValue();
      ConfigParser parser = new ConfigParser();
      // may work for StandaloneContainer
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      URL res = cl.getResource(path);
      // for PortalContainer
      if (res == null)
        res = configurationManager.getResource(path);
      if (res == null)
        throw new Exception("Resource not found " + path);
      System.out.println("Catalog configuration found at " + res);
      parser.parse(res);
    }

  }
}
