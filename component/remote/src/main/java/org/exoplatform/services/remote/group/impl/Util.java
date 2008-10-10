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

import java.util.List;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.View;
import org.jgroups.stack.IpAddress;

import org.exoplatform.services.remote.group.MemberInfo;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Mar 4, 2005
 * @version $Id: Util.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class Util {
  static public Address findAddress(JChannel channel, MemberInfo info) {
    View view = channel.getView();
    List members = view.getMembers();
    String ip = info.getIpAddress();
    for (int i = 0; i < members.size(); i++) {
      Object member = members.get(i);
      if (member instanceof IpAddress) {
        IpAddress addr = (IpAddress) member;
        if (ip.equals(addr.getIpAddress().getHostAddress()))
          return addr;
      } else {
        if (info.getIpAddress().equals(member.toString())) {
          return (Address) member;
        }
      }
    }
    return null;
  }

  static public MemberInfo createMemberInfo(Object member) {
    MemberInfo info = new MemberInfo();
    if (member instanceof IpAddress) {
      IpAddress addr = (IpAddress) member;
      info.setHostName(addr.getIpAddress().getHostName());
      info.setIpAddress(addr.getIpAddress().getHostAddress());
      info.setPort(addr.getPort());
    } else {
      info.setHostName(member.toString());
      info.setIpAddress(member.toString());
    }
    return info;
  }
}
