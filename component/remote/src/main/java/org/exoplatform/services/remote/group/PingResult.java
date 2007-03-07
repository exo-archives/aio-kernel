/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.remote.group;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Mar 4, 2005
 * @version $Id: PingResult.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class PingResult {
  private String message_ ;
  private MemberInfo info_ ;
  
  public PingResult(MemberInfo info, String message) {
    info_ = info ;
    message_ = message ;
  }
  
  public String getReplyMessage()  { return message_ ; }
  
  public MemberInfo getMemberInfo() { return info_ ; }
  
}
