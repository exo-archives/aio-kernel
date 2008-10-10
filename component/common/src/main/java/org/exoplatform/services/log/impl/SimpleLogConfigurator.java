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

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.exoplatform.services.log.AbstractLogConfigurator;

/**
 * Created by The eXo Platform SAS. <br/> Simple commons configurator. See
 * org.apache.commons.logging.impl.SimpleLog javadoc for details.
 * 
 * @author <a href="mailto:gennady.azarenkov@exoplatform.com">Gennady
 *         Azarenkov</a>
 * @version $Id: SimpleLogConfigurator.java 5332 2006-04-29 18:32:44Z geaz $
 */

public class SimpleLogConfigurator extends AbstractLogConfigurator {

  public void configure(Properties properties) {

    // it is ok in AS env, but maven test throws something not understandable
    // System.setProperties(properties);

    for (Iterator it = properties.entrySet().iterator(); it.hasNext();) {
      Map.Entry entry = (Map.Entry) it.next();
      System.setProperty((String) entry.getKey(), (String) entry.getValue());
    }

    this.properties = properties;
  }
}
