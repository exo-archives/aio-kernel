/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.remote.group;

import java.io.Serializable;
import java.util.List;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Mar 4, 2005
 * @version $Id: CommunicationServiceMonitor.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class CommunicationServiceMonitor implements Serializable{
  private MemberInfo info_ ;
  private List monitors_ ;
  
  public CommunicationServiceMonitor(List monitors , MemberInfo info) {
    monitors_ = monitors ;
    info_ = info ;
  }
  
  public List  getMessageHandlerMonitors()  {
    return monitors_ ;
  }
  
  public MemberInfo getMemberInfo() { return info_ ; }
} 