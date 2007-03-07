/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.xml;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Apr 18, 2005
 * @version $Id: LifecyclePlugin.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class LifecyclePlugin {
  String name ;
  String type ;
  String description ;
  InitParams initParams ;
  
  public String getName() {   return name; }
  public void setName(String name) { this.name = name; }
  
  public String getType() {   return type; }
  public void setType(String type) { this.type = type; }
  
  public String getDescription() {  return description; }  
  public void setDescription(String description) {  this.description = description;  }
  
  public InitParams getInitParams() {  return initParams; }
  public void setInitParams(InitParams ips) {  this.initParams = ips; }
}
