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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Jul 20, 2004
 * 
 * @author: Tuan Nguyen
 * @email: tuan08@users.sourceforge.net
 * @version: $Id: ExoProperties.java,v 1.1 2004/09/11 14:08:53 tuan08 Exp $
 */
public class ExoProperties extends HashMap<String, String> {
  public ExoProperties() {
  }

  public ExoProperties(int size) {
    super(size);
  }

  public String getProperty(String key) {
    return (String) get(key);
  }

  public void setProperty(String key, String value) {
    put(key, value);
  }

  public void addPropertiesFromText(String text) {
    String[] temp = text.split("\n");
    for (int i = 0; i < temp.length; i++) {
      temp[i] = temp[i].trim();
      if (temp[i].length() > 0) {
        String[] value = temp[i].split("=");
        if (value.length == 2)
          put(value[0].trim(), value[1].trim());
      }
    }
  }

  public String toText() {
    StringBuffer b = new StringBuffer();
    Set<Map.Entry<String, String>> set = entrySet();
    Iterator<Map.Entry<String, String>> i = set.iterator();
    while (i.hasNext()) {
      Map.Entry<String, String> entry = (Map.Entry<String, String>) i.next();
      b.append(entry.getKey()).append("=").append(entry.getValue()).append("\n");
    }
    return b.toString();
  }

  public String toXml() {
    StringBuffer b = new StringBuffer();
    b.append("<properties>");
    Set<Map.Entry<String, String>> set = entrySet();
    Iterator<Map.Entry<String, String>> i = set.iterator();
    while (i.hasNext()) {
      Map.Entry<String, String> entry = (Map.Entry<String, String>) i.next();
      b.append("<property key=\"")
       .append(entry.getKey())
       .append("\" value=\"")
       .append(entry.getValue())
       .append("\"/>");
    }
    b.append("</properties>");
    return b.toString();
  }
}
