/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container;

import java.util.List;

/**
 * Created by The eXo Platform SARL
 * Author : Roman Pedchenko
 *          roman.pedchenko@exoplatform.com.ua
 * May 24, 2006
 */

public interface SessionManagerContainer {
  
  public List<SessionContainer> getLiveSessions();
  public void removeSessionContainer(String id);
  public SessionContainer createSessionContainer(String id, String owner);
  public SessionManager getSessionManager();

}
