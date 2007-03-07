/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container;

import java.util.List;

/**
 * Created by The eXo Platform SARL
 * Author : Tuan Nguyen
 *          tuan08@users.sourceforge.net
 * Nov 4, 2005
 */
public interface SessionManager  {
  
  public List<SessionContainer> getLiveSessions()  ;
  public SessionContainer getSessionContainer(String id) ;
  public void  removeSessionContainer(String id) ;
  public void  addSessionContainer(SessionContainer scontainer) ;
}
