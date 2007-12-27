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

import javax.servlet.ServletContext;

/**
 * @author Benjamin Mestrallet
 * benjamin.mestrallet@exoplatform.com
 */

public class PortalContainerInfo {
  
  private String containerName;
  
  public PortalContainerInfo() { 
  }
  
  public PortalContainerInfo(ServletContext context) {  
    containerName = context.getServletContextName(); 
  }
  
  public String getContainerName() {  return containerName; }
  public void setContainerName(String name) { containerName = name; }
}
