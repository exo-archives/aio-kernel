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

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

/**
 * @author: Tuan Nguyen
 * @version: $Id: IOUtil.java,v 1.4 2004/09/14 02:41:19 tuan08 Exp $
 * @since: 0.0
 * @email: tuan08@yahoo.com
 */
public class IOUtil {
  static public String getFileContenntAsString(File file, String encoding) throws Exception {
    FileInputStream is = new FileInputStream(file);
    return new String(getStreamContentAsBytes(is), encoding);
  }

  static public String getFileContenntAsString(File file) throws Exception {
    FileInputStream is = new FileInputStream(file);
    return new String(getStreamContentAsBytes(is));
  }

  static public String getFileContenntAsString(String fileName, String encoding) throws Exception {
    FileInputStream is = new FileInputStream(fileName);
    return new String(getStreamContentAsBytes(is), encoding);
  }

  static public String getFileContenntAsString(String fileName) throws Exception {
    FileInputStream is = new FileInputStream(fileName);
    return new String(getStreamContentAsBytes(is));
  }

  static public byte[] getFileContentAsBytes(String fileName) throws Exception {
    FileInputStream is = new FileInputStream(fileName);
    return getStreamContentAsBytes(is);
  }

  static public String getStreamContentAsString(InputStream is) throws Exception {
    byte buf[] = new byte[is.available()];
    is.read(buf);
    return new String(buf, "UTF-8");
  }

  static public byte[] getStreamContentAsBytes(InputStream is) throws Exception {
    BufferedInputStream buffer = new BufferedInputStream(is);
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    byte[] data = new byte[buffer.available()];
    int available = -1;
    while ((available = buffer.read(data)) > -1) {
      output.write(data, 0, available);
    }
    return output.toByteArray();
  }

  static public String getResourceAsString(String resource) throws Exception {
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    URL url = cl.getResource(resource);
    InputStream is = url.openStream();
    return getStreamContentAsString(is);
  }

  static public byte[] getResourceAsBytes(String resource) throws Exception {
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    URL url = cl.getResource(resource);
    InputStream is = url.openStream();
    return getStreamContentAsBytes(is);
  }

  @Deprecated
  static public byte[] serialize(Object obj) throws Exception {
    //long start = System.currentTimeMillis() ;
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    ObjectOutputStream out = new ObjectOutputStream(bytes);
    out.writeObject(obj);
    out.close();
    byte[] ret = bytes.toByteArray();
    //long end = System.currentTimeMillis() ;
    return ret;
  }

  @Deprecated
  static public Object deserialize(byte[] bytes) throws Exception {
    if (bytes == null)
      return null;
    //long start = System.currentTimeMillis() ;
    ByteArrayInputStream is = new ByteArrayInputStream(bytes);
    ObjectInputStream in = new ObjectInputStream(is);
    Object obj = in.readObject();
    in.close();
    return obj;
  }
}
