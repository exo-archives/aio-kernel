/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.xml;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Apr 18, 2005
 * @version $Id: RulePlugin.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class RulePlugin {
  String name ;
  String type ;
  String ruleDeclaration ;
  String description ;
  InitParams initParams ;
  
  public String getName() {   return name; }
  public void setName(String s) {   this.name = s; }
  
  public String getType() {   return type; }
  public void setType(String s) {   this.type = s; }
  
  public String getRuleDeclaration() {   return ruleDeclaration ; }
  public void   setRuleDeclaration(String s) {   ruleDeclaration = s; }
  
  public String getDescription() {    return description;  }
  public void setDescription(String desc) {   this.description = desc;  }
  
  public InitParams getInitParams() {  return initParams; }
  public void setInitParams(InitParams ips) {  this.initParams = ips; }
}