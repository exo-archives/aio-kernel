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
package org.exoplatform.services.net.impl;

import java.net.Socket;

import org.exoplatform.services.net.NetService;

/**
 * Created by The eXo Platform SAS Author : HoaPham phamvuxuanhoa@yahoo.com Jan
 * 10, 2006
 */
public class NetServiceImpl implements NetService {

  public long ping(String host, int port) throws Exception {
    long startTime = 0;
    long endTime = 0;
    try {
      startTime = System.currentTimeMillis();
      Socket socket = new Socket(host, port);
      endTime = System.currentTimeMillis();
    } catch (Exception e) {
      // e.printStackTrace() ;
      return -1;
    }
    return endTime - startTime;
  }
}
