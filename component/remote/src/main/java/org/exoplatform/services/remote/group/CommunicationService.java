/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.remote.group;

import java.util.List;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Feb 21, 2005
 * @version $Id: CommunicationService.java 5799 2006-05-28 17:55:42Z geaz $
 */
public interface CommunicationService {
  
  public List getMembersInfo()   ;
  public CommunicationServiceMonitor getCommunicationServiceMonitor(MemberInfo info) throws Exception ;
  
  public Message createMessage(String handlerId) ; 
  public void broadcast(Message message, boolean include) throws Exception ;
  public void broadcast(Message message, ResultHandler handler, boolean include) throws Exception ;
  public Object send(MemberInfo dest, Message message) throws Exception ;
  
  public PingResult ping(MemberInfo info, String message) throws Exception ;
  public List       pingAll(String message) throws Exception ;

}