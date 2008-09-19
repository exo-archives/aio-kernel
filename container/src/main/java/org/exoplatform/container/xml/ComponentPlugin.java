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

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Apr 18, 2005
 * @version $Id: ComponentPlugin.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class ComponentPlugin {
  String     name;

  String     type;

  String     setMethod;

  String     description;

  InitParams initParams;

  public String getName() {
    return name;
  }

  public void setName(String s) {
    this.name = s;
  }

  public String getType() {
    return type;
  }

  public void setType(String s) {
    this.type = s;
  }

  public String getSetMethod() {
    return setMethod;
  }

  public void setSetMethod(String s) {
    setMethod = s;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String desc) {
    this.description = desc;
  }

  public InitParams getInitParams() {
    return initParams;
  }

  public void setInitParams(InitParams ips) {
    this.initParams = ips;
  }
}
