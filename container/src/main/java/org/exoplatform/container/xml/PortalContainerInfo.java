 /***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/

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
