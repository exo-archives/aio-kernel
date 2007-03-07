/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.component;

import org.exoplatform.container.ExoContainer;
/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Apr 18, 2005
 * @version $Id: ComponentLifecycle.java 5799 2006-05-28 17:55:42Z geaz $
 */
public interface ComponentLifecycle {
  public void  initComponent(ExoContainer container) throws Exception ;
  public void  startComponent(ExoContainer container) throws Exception ;
  public void  stopComponent(ExoContainer container) throws Exception ;
  public void  destroyComponent(ExoContainer container) throws Exception ;
}