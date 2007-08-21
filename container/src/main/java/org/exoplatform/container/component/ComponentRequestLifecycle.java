/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.component;

import org.exoplatform.container.ExoContainer;

public interface ComponentRequestLifecycle {
  public void  startRequest(ExoContainer container) ;
  public void  endRequest(ExoContainer container) ;
}