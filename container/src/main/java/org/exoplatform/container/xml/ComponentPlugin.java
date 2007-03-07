/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.xml;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Apr 18, 2005
 * @version $Id: ComponentPlugin.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class ComponentPlugin {
  String name ;
  String type ;
  String setMethod ;
  String description ;
  InitParams initParams ;
  
  public String getName() {   return name; }
  public void setName(String s) {   this.name = s; }
  
  public String getType() {   return type; }
  public void setType(String s) {   this.type = s; }
  
  public String getSetMethod() {   return setMethod ; }
  public void   setSetMethod(String s) {   setMethod = s; }
  
  public String getDescription() {    return description;  }
  public void setDescription(String desc) {   this.description = desc;  }
  
  public InitParams getInitParams() {  return initParams; }
  public void setInitParams(InitParams ips) {  this.initParams = ips; }
}
