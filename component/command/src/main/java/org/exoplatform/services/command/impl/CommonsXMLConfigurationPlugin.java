/**
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 */

package org.exoplatform.services.command.impl;

import java.net.URL;

import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.config.ConfigParser;
import org.apache.commons.chain.impl.CatalogFactoryBase;
import org.exoplatform.container.component.BaseComponentPlugin;
import org.exoplatform.container.configuration.ConfigurationManager;
import org.exoplatform.container.xml.InitParams;
import org.exoplatform.container.xml.ValueParam;

/**
 * Created by The eXo Platform SARL        .<br/>
 * The plugin for configuring command/chain catalog using "native" Apache Commons Chain's XML file
 * @author <a href="mailto:gennady.azarenkov@exoplatform.com">Gennady Azarenkov</a>
 * @version $Id: CommonsXMLConfigurationPlugin.java 9846 2006-10-27 11:03:37Z geaz $
 */

public class CommonsXMLConfigurationPlugin extends BaseComponentPlugin {

//  protected Catalog defaultCatalog;
  
  public  CommonsXMLConfigurationPlugin(InitParams params, ConfigurationManager configurationManager) throws Exception {
    ValueParam confFile = params.getValueParam("config-file");
    if (confFile != null) {
      String path = confFile.getValue();
      ConfigParser parser = new ConfigParser();
      // may work for StandaloneContainer
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      URL res = cl.getResource(path);
      // for PortalContainer
      if(res == null)
       res = configurationManager.getResource(path);
      if(res == null)
        throw new Exception("Resource not found "+path);
      System.out.println("Catalog configuration found at "+res);
      parser.parse(res);
    }
 
  }
}
