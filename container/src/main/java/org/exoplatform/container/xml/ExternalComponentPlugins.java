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
package org.exoplatform.container.xml;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Apr 18, 2005
 * @version $Id: ExternalComponentPlugins.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class ExternalComponentPlugins {
  String                     targetComponent;

  ArrayList<ComponentPlugin> componentPlugins;

  public String getTargetComponent() {
    return targetComponent;
  }

  public void setTargetComponent(String s) {
    targetComponent = s;
  }

  public List getComponentPlugins() {
    return componentPlugins;
  }

  public void setComponentPlugins(ArrayList<ComponentPlugin> list) {
    componentPlugins = list;
  }

  public void merge(ExternalComponentPlugins other) {
    if (other == null)
      return;
    List otherPlugins = other.getComponentPlugins();
    if (otherPlugins == null)
      return;
    if (componentPlugins == null)
      componentPlugins = new ArrayList();
    componentPlugins.addAll(otherPlugins);
  }
}
