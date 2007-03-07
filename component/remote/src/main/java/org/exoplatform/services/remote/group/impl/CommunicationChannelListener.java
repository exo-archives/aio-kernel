/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.remote.group.impl;

import org.jgroups.Address;
import org.jgroups.Channel;
import org.jgroups.ChannelListener;
/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Feb 21, 2005
 * @version $Id: CommunicationChannelListener.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class CommunicationChannelListener implements ChannelListener {

  public void channelConnected(Channel arg0) {
//    S ystem.out.println("Channel connect.................") ;
  }

  public void channelDisconnected(Channel arg0) {
  }

 
  public void channelClosed(Channel arg0) {
  }

  public void channelShunned() {
 
  }

  public void channelReconnected(Address arg0) {
  }
  
}
