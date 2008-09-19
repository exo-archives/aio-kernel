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
package org.exoplatform.commons.utils;

import java.net.InetAddress;
import java.security.SecureRandom;

/**
 * Jul 19, 2004
 * 
 * @author: Tuan Nguyen
 * @email: tuan08@users.sourceforge.net
 * @version: $Id: IdentifierUtil.java,v 1.1 2004/07/20 12:22:42 tuan08 Exp $
 */
public class IdentifierUtil {

  private static String             hexServerIP_ = null;

  private static final SecureRandom seeder_      = new SecureRandom();

  //============================================================================
  // ==
  static public String generateUUID(Object o) {
    StringBuffer tmpBuffer = new StringBuffer(16);
    if (hexServerIP_ == null) {
      InetAddress localInetAddress = null;
      try {
        // get the inet address
        localInetAddress = InetAddress.getLocalHost();
      } catch (java.net.UnknownHostException uhe) {
        // System .err .println(
        // "ContentSetUtil: Could not get the local IP address using InetAddress.getLocalHost()!"
        // );
        // todo: find better way to get around this...
        uhe.printStackTrace();
        return null;
      }
      byte serverIP[] = localInetAddress.getAddress();
      hexServerIP_ = hexFormat(getInt(serverIP), 8);
    }
    String hashcode = hexFormat(System.identityHashCode(o), 8);
    tmpBuffer.append(hexServerIP_);
    tmpBuffer.append(hashcode);

    long timeNow = System.currentTimeMillis();
    int timeLow = (int) timeNow & 0xFFFFFFFF;
    int node = seeder_.nextInt();

    StringBuffer guid = new StringBuffer(32);
    guid.append(hexFormat(timeLow, 8));
    guid.append(tmpBuffer.toString());
    guid.append(hexFormat(node, 8));
    return guid.toString();
  }

  private static int getInt(byte bytes[]) {
    int i = 0;
    int j = 24;
    for (int k = 0; j >= 0; k++) {
      int l = bytes[k] & 0xff;
      i += l << j;
      j -= 8;
    }
    return i;
  }

  private static String hexFormat(int i, int j) {
    String s = Integer.toHexString(i);
    return padHex(s, j) + s;
  }

  private static String padHex(String s, int i) {
    StringBuffer tmpBuffer = new StringBuffer();
    if (s.length() < i) {
      for (int j = 0; j < i - s.length(); j++) {
        tmpBuffer.append('0');
      }
    }
    return tmpBuffer.toString();
  }
}
