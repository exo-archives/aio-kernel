/*
 * Copyright (C) 2003-2007 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package org.exoplatform.services.remote.group.impl;

import java.util.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.exoplatform.services.remote.group.MessageHandler;
import org.exoplatform.services.remote.group.Message ;
import org.exoplatform.services.remote.group.MessageHandlerMonitor;
import org.jgroups.blocks.RequestHandler;
/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Mar 4, 2005
 * @version $Id: RequestHandlerImpl.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class RequestHandlerImpl implements RequestHandler {
  private Map messageHandlers_ = new HashMap();
  private Log log_ ;
  
  public RequestHandlerImpl(Log log) {
    log_ = log ;
  }
  
  public void registerMessageHandler(MessageHandler handler)  {
    messageHandlers_.put(handler.getIdentifier(), handler) ;
  }
  
  public Object handle(org.jgroups.Message jmessage) {
    Message message = (Message)jmessage.getObject() ;
    String handlerId = message.getTargetHandler() ;
    MessageHandler handler = (MessageHandler) messageHandlers_.get(handlerId) ;
    MessageHandlerMonitor monitor = handler.getMonitor() ;
    if(handler != null) {
      try {
        monitor.addMessageCounter(1) ;
        return handler.handle(message) ;
      } catch (Exception ex) {
        monitor.setLastError(ex) ;
        log_.error("Error :", ex) ;
        return null ;
      }
    }
    log_.info("Cannot finf the message handler for the request handler: " + handlerId) ;
    return null ;
  }
  
  public List getMessageHandlerMonitors() {
    List monitors = new ArrayList() ;
    Iterator i = messageHandlers_.values().iterator() ;
    while(i.hasNext()) {
      MessageHandler handler = (MessageHandler) i.next();
      monitors.add(handler.getMonitor()) ;
    }
    return monitors ;
  }
}