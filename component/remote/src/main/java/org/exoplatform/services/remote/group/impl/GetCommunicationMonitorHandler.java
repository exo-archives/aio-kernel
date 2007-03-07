/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.remote.group.impl;

import org.exoplatform.services.remote.group.CommunicationService;
import org.exoplatform.services.remote.group.Message;
import org.exoplatform.services.remote.group.MessageHandler;
/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Mar 5, 2005
 * @version $Id: GetCommunicationMonitorHandler.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class GetCommunicationMonitorHandler extends MessageHandler {
  final static public String IDENTIFIER =  "GetCommunicationMonitorHandler" ;
  
  private CommunicationService service_ ;
  
  public GetCommunicationMonitorHandler() { 
    super(IDENTIFIER) ;
  }
  
  public  void init(CommunicationService service)  {
    service_ = service ;
  }
  
  public Object handle(Message message) throws Exception {
    return service_.getCommunicationServiceMonitor(null) ;
  }
}