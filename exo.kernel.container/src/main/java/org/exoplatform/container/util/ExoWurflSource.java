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
package org.exoplatform.container.util;

import java.io.InputStream;

//import net.sourceforge.wurfl.wurflapi.WurflSource;

public class ExoWurflSource /* implements WurflSource */{

  public InputStream getWurflInputStream() {
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    java.net.URL wurflUrl = cl.getResource("conf/wurfl.xml");
    try {
      return wurflUrl.openStream();
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public InputStream getWurflPatchInputStream() {
    return null;
  }
}
