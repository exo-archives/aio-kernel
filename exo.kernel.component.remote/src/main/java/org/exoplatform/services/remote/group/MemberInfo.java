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
package org.exoplatform.services.remote.group;

import java.io.Serializable;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Feb 21, 2005
 * @version $Id: MemberInfo.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class MemberInfo implements Serializable {
  private String hostName_;

  private String ipAddress_;

  private int    port_;

  public String getHostName() {
    return hostName_;
  }

  public void setHostName(String s) {
    hostName_ = s;
  }

  public String getIpAddress() {
    return ipAddress_;
  }

  public void setIpAddress(String s) {
    ipAddress_ = s;
  }

  public int getPort() {
    return port_;
  }

  public void setPort(int port) {
    port_ = port;
  }

  public String toString() {
    return hostName_ + ":" + port_;
  }
}
