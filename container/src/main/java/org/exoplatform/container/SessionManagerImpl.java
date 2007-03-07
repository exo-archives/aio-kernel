/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by The eXo Platform SARL
 * Author : Tuan Nguyen
 *          tuan08@users.sourceforge.net
 * Nov 4, 2005
 */
public class SessionManagerImpl 
  extends Hashtable<String, SessionContainer> implements SessionManager {
  
  public List<SessionContainer> getLiveSessions() {
    List<SessionContainer> list = new ArrayList<SessionContainer>(size() + 1);
    list.addAll(values()) ;
    return list ;
  }

  final public SessionContainer getSessionContainer(String id) {  return get(id) ; }

  final public void removeSessionContainer(String id) {
    remove(id) ; 
  }

  final public void addSessionContainer(SessionContainer scontainer) {
    put(scontainer.getSessionId() , scontainer) ;
  }
}
