/*
 * Copyright (C) 2003-2009 eXo Platform SAS.
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
package org.exoplatform.services.cache.impl.jboss;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.exoplatform.container.component.BaseComponentPlugin;
import org.exoplatform.container.xml.InitParams;
import org.exoplatform.container.xml.ValueParam;

/**
 * This class is used to define custom configurations
 * 
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * 23 juil. 2009  
 */
public class ExoCacheFactoryConfigPlugin extends BaseComponentPlugin {
  
  /**
   * The map of all the creators defined for this ComponentPlugin
   */
  private final Map<String, String> configs;

  @SuppressWarnings("unchecked")
  public ExoCacheFactoryConfigPlugin(InitParams params) {
    configs = new HashMap<String, String>();
    for (Iterator<ValueParam> iterator = params.getValueParamIterator(); iterator.hasNext();) {
      ValueParam vParam = iterator.next();
      configs.put(vParam.getName(), vParam.getValue());
    }
  }

  /**
   * Returns all the configurations defined for this ComponentPlugin
   */
  public Map<String, String> getConfigs() {
    return configs;
  }  
}
