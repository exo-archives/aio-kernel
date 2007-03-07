/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.remote.group.impl;

import java.util.List;
import org.exoplatform.services.remote.group.MemberInfo;
import org.jgroups.stack.IpAddress;
import org.jgroups.Address;
import org.jgroups.JChannel ;
import org.jgroups.View;
/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Mar 4, 2005
 * @version $Id: Util.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class Util {
  static public Address findAddress(JChannel channel, MemberInfo info) {
    View view = channel.getView();
    List members = view.getMembers() ;
    String ip = info.getIpAddress() ;
    for(int i = 0; i < members.size() ; i++) {
      Object member = members.get(i) ;
      if(member instanceof IpAddress) {
        IpAddress addr = (IpAddress) member;
        if(ip.equals(addr.getIpAddress().getHostAddress())) return addr ;
      } else {
        if(info.getIpAddress().equals(member.toString())) {
          return (Address) member ;
        }
      }
    }
    return null ;
  }
  
  static public MemberInfo createMemberInfo(Object member) {
    MemberInfo info = new MemberInfo() ;
    if(member instanceof IpAddress) {
      IpAddress addr = (IpAddress) member;
      info.setHostName(addr.getIpAddress().getHostName()) ;
      info.setIpAddress(addr.getIpAddress().getHostAddress()) ;
      info.setPort(addr.getPort());
    } else {
      info.setHostName(member.toString()) ;
      info.setIpAddress(member.toString()) ;
    }
    return info ;
  }
}
