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
 * @version $Id: Component.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class Component {
  String key ;
  String type;
  String jmxName ;
  String description ;
  ArrayList plugins ;
  ArrayList<ComponentPlugin> componentPlugins ;
  ArrayList listeners ;
  InitParams initParams ;
  boolean showDeployInfo = false ;
  boolean multiInstance = false;
   
  public String getKey() {  return key; }
  public void setKey(String s) {   this.key = s; }
  
  
  public String getJMXName() {  return jmxName ; }
  public void   setJMXName(String s) { jmxName = s; }
  
  public String getType() {   return type; }
  public void setType(String s) {  type = s; }
  
  public String getDescription() {   return description; }
  public void setDescription(String s) { description = s; }
  
  public List getPlugins() {   return plugins; }
  public void setPlugins(ArrayList list) { plugins = list; }
  
  
  public List getComponentPlugins() {   return componentPlugins; }
  public void setComponentPlugins(ArrayList list) { componentPlugins = list; }
  
  public List getListeners() {   return listeners; }
  public void setListeners(ArrayList list) { listeners = list; }
  
  public InitParams getInitParams() {  return initParams; }
  public void setInitParams(InitParams ips) { initParams = ips; }
  
  public boolean getShowDeployInfo() {  return showDeployInfo ; }
  public void    setShowDeployInfo(boolean b) { showDeployInfo = b ; }
  
  public boolean isMultiInstance() {  return multiInstance; }
  public void    setMultiInstance(boolean b) { multiInstance = b ; }

}