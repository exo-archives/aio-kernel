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
package org.exoplatform.services.log.impl;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.exoplatform.services.log.AbstractLogConfigurator;
/**
 * Created by The eXo Platform SAS. 
 * 
 * <br/> Log4J configurator. The properties are the same as log4j.properties file's name value pairs.
 *  
 * @author <a href="mailto:gennady.azarenkov@exoplatform.com">Gennady Azarenkov</a>
 * @version $Id: Log4JConfigurator.java 5332 2006-04-29 18:32:44Z geaz $
 */

public class Log4JConfigurator extends AbstractLogConfigurator {

  public void configure(Properties properties) {
    PropertyConfigurator.configure(properties);
    this.properties = properties;
  }

}
