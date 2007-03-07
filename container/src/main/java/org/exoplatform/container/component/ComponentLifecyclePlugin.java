/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.component;

import java.util.List;

import org.exoplatform.container.ExoContainer;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Apr 18, 2005
 * @version $Id: ComponentLifecyclePlugin.java 5799 2006-05-28 17:55:42Z geaz $
 */
public interface ComponentLifecyclePlugin {
  public String getName() ;
  public void setName(String s) ;
  
  public String getDescription() ;
  public void setDescription(String s) ;
  
  public List<String> getManageableComponents() ;
  public void setManageableComponents(List<String> list) ;
  
  public void  initComponent(ExoContainer container, Object component) throws Exception ;
  public void  startComponent(ExoContainer container, Object component) throws Exception ;
  public void  stopComponent(ExoContainer container, Object component) throws Exception ;
  public void  destroyComponent(ExoContainer container, Object component) throws Exception ;
}