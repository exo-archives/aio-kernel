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
package org.exoplatform.xml.object;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Apr 11, 2005
 * @version $Id: XMLArray.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class XMLArray {

  private List list_ = new ArrayList();

  public XMLArray() {
  }

  public XMLArray(Object o) throws Exception {
    if (o instanceof int[]) {
      int[] array = (int[]) o;
      for (int i = 0; i < array.length; i++) {
        list_.add(new Integer(array[i]));
      }
    }
  }

  public void addValue(Object object) {
    XMLValue value = (XMLValue) object;
    list_.add(value);
  }

  public Iterator getIterator() {
    return list_.iterator();
  }
}
