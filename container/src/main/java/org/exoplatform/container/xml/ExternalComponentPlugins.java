/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.xml;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Apr 18, 2005
 * @version $Id: ExternalComponentPlugins.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class ExternalComponentPlugins {
  String targetComponent ;
  ArrayList<ComponentPlugin> componentPlugins ;
  
  
  public String getTargetComponent() {  return targetComponent; }
  public void setTargetComponent(String s) {targetComponent = s; }
  
  public List getComponentPlugins() {  return componentPlugins; }
  public void setComponentPlugins(ArrayList<ComponentPlugin> list) {  componentPlugins = list; }
  
  public void merge(ExternalComponentPlugins other) {
    if(other == null) return  ;
    List otherPlugins = other.getComponentPlugins() ;
    if(otherPlugins == null) return ;
    if(componentPlugins == null) componentPlugins = new ArrayList() ;
    componentPlugins.addAll(otherPlugins) ;
  }
}
