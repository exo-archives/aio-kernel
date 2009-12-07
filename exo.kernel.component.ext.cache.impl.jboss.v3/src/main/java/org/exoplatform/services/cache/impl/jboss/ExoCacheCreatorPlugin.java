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

import java.util.ArrayList;
import java.util.List;

import org.exoplatform.container.component.BaseComponentPlugin;
import org.exoplatform.container.xml.InitParams;

/**
 * This class allows us to define new creators
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * 20 juil. 2009  
 */
public class ExoCacheCreatorPlugin extends BaseComponentPlugin {
  
  /**
   * The list of all the creators defined for this ComponentPlugin
   */
  private final List<ExoCacheCreator> creators;

  public ExoCacheCreatorPlugin(InitParams params) {
    creators = new ArrayList<ExoCacheCreator>();
    List<?> configs = params.getObjectParamValues(ExoCacheCreator.class);
    for (int i = 0; i < configs.size(); i++) {
      ExoCacheCreator config = (ExoCacheCreator) configs.get(i);
      creators.add(config);
    }
  }

  /**
   * Returns all the creators defined for this ComponentPlugin
   */
  public List<ExoCacheCreator> getCreators() {
    return creators;
  }
}
