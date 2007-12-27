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

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.CatalogFactory;
import org.apache.commons.chain.config.ConfigParser;
import org.apache.commons.chain.impl.CatalogFactoryBase;
import org.apache.commons.digester.Digester;
import org.exoplatform.container.component.ComponentPlugin;
import org.xml.sax.SAXException;

/**
 * Created by The eXo Platform SAS.
 * @author <a href="mailto:gennady.azarenkov@exoplatform.com">Gennady Azarenkov</a>
 * @version $Id: CommandService.java 12832 2007-02-15 12:41:32Z geaz $
 */

public class CommandService {
  
//  protected Catalog catalog;
  
  protected CatalogFactory catalogFactory;
  
  protected Digester digester;
  
  public CommandService() {
    this.catalogFactory = CatalogFactoryBase.getInstance();
    
    ConfigParser parser = new ConfigParser();
    this.digester = parser.getDigester();
  }
  
  public void addPlugin(ComponentPlugin plugin) {
    // no needs to do anything as CatalogFactory is initialized in plugin
    
//    if (plugin instanceof CommonsXMLConfigurationPlugin) {
//      CommonsXMLConfigurationPlugin cplugin = (CommonsXMLConfigurationPlugin) plugin;
      // can just reinitialize it every time as have single instance
//      catalog = cplugin.getCatalog();
//      Iterator names = cplugin.getCatalogNames();
//      while(names.hasNext()) {
//        String name = (String)names.next(); 
//        catalogs.put(name, cplugin.getCatalog(name));
//      }
//    }
  }
  
  /**
   * puts catalog (add or update) using XML input stream 
   * @param xml
   * @throws IOException
   * @throws SAXException
   */
  public void putCatalog(InputStream xml) throws IOException, SAXException {
//    ConfigParser parser = new ConfigParser();
    // Prepare our Digester instance
//    Digester digester = parser.getDigester();
    digester.clear();
    digester.parse(xml);

//    parser.getDigester().parse(xml);
  }

  
  /**
   * @return default catalog
   */
  public Catalog getCatalog() {
    Catalog catalog = catalogFactory.getCatalog();
    return catalog;
  }
  
  /**
   * @param name
   * @return named catalog
   */
  public Catalog getCatalog(String name) {
    Catalog catalog = catalogFactory.getCatalog(name);
    return catalog;

  }

  /**
   * @return iterator of catalog names. default catalog is not listed here!
   */
  public Iterator getCatalogNames() {
    return catalogFactory.getNames();
  }


}
