/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.remote.group.impl;

import org.exoplatform.services.remote.group.Message;
import org.exoplatform.services.remote.group.MessageHandler;
/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Mar 5, 2005
 * @version $Id: PingMessageHandler.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class PingMessageHandler extends MessageHandler {
  final static public String IDENTIFIER =  "PingMessageHandler" ;
  
  public PingMessageHandler() { 
    super(IDENTIFIER) ;
  }
  
  public Object handle(Message message) throws Exception {
    String pingMessage = (String) message.getMessage() ;
    return "Received Message: " + pingMessage;
  }
}
