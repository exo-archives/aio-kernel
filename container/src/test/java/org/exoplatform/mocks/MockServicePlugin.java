/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.mocks;

import org.exoplatform.container.component.ComponentPlugin;
import org.exoplatform.container.xml.InitParams;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Apr 18, 2005
 * @version $Id: MockServicePlugin.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class MockServicePlugin implements ComponentPlugin {
  String name ;
  String description ;
  
  public MockServicePlugin(InitParams params) {
    System.out.println("plugin init params: " + params) ;
  }
  
  public String getName() {  return name; }
  public void setName(String s) { name =  s ;}
  
  public String getDescription() {   return description ; }
  public void setDescription(String s) {  description = s ; }
  
}
