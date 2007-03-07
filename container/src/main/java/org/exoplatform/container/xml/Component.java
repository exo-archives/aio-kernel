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
  ArrayList<RulePlugin> rulePlugins ;
  InitParams initParams ;
  boolean showDeployInfo = false ;
   
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
  
  public List<RulePlugin> getRulePlugins() {   return rulePlugins; }
  public void setRulePlugins(ArrayList<RulePlugin> list) { rulePlugins = list; }
  
  public InitParams getInitParams() {  return initParams; }
  public void setInitParams(InitParams ips) { initParams = ips; }
  
  public boolean getShowDeployInfo() {  return showDeployInfo ; }
  public void    setShowDeployInfo(boolean b) { showDeployInfo = b ; }
  
}