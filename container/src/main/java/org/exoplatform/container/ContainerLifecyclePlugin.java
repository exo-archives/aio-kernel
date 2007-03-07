/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container;

import org.exoplatform.container.xml.InitParams;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Apr 18, 2005
 * @version $Id: ContainerLifecyclePlugin.java 5799 2006-05-28 17:55:42Z geaz $
 */
public interface ContainerLifecyclePlugin {
  public String getName() ;
  public void   setName(String s) ;
  
  public String getDescription() ;
  public void   setDescription(String s) ;
  
  public InitParams getInitParams() ;
  public void  setInitParams(InitParams params) ; 
  
  public void  initContainer(ExoContainer container) throws Exception ;
  public void  startContainer(ExoContainer container) throws Exception ;
  public void  stopContainer(ExoContainer container) throws Exception ;
  public void  destroyContainer(ExoContainer container) throws Exception ;
}