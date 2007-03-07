/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.component;

import org.exoplatform.container.ExoContainer;
/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Apr 18, 2005
 * @version $Id: ComponentRequestLifecycle.java 5799 2006-05-28 17:55:42Z geaz $
 */
public interface ComponentRequestLifecycle {
  public void  startRequest(ExoContainer container) ;
  public void  endRequest(ExoContainer container) ;
}