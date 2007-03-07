/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.remote.group;

import org.exoplatform.container.component.ComponentPlugin;
/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Feb 21, 2005
 * @version $Id: MessageHandler.java 5799 2006-05-28 17:55:42Z geaz $
 */
abstract public class MessageHandler  implements  ComponentPlugin {
  private String name_ ;
  private String description_ ;
  
  private MessageHandlerMonitor monitor_  ;
  
  public MessageHandler(String id) {
    monitor_ = new MessageHandlerMonitor(id) ;
  }
  
  public  void init(CommunicationService service) {
    
  }
  
  public String getName()  { return name_ ; }
  public void   setName(String s) { name_ = s ;}
  
  public String getDescription() { return description_ ; }
  public void   setDescription(String s) { description_ = s ;}
  
  public String getIdentifier() { return monitor_.getMessageHandlerId(); }
  
  public MessageHandlerMonitor getMonitor() { return monitor_ ; }
  
  abstract public Object handle(Message message) throws Exception ;
  
}
