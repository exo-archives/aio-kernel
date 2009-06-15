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
import java.io.IOException;
import java.net.URL;

/**
 * @author: Tuan Nguyen
 * @version: $Id: IOUtil.java,v 1.4 2004/09/14 02:41:19 tuan08 Exp $
 * @since: 0.0
 * @email: tuan08@yahoo.com
 */
public class IOUtil {

  /** The buffer size for reading input streams. */
  private static final int DEFAULT_BUFFER_SIZE = 256;

  /**
   * Returns the content of the specified file as a string using the <code>UTF-8</code> charset.
   *
   * @param file the file
   * @return the content
   * @throws IOException any io exception
   * @throws NullPointerException if any argument is null
   */
  static public String getFileContentAsString(File file) throws IOException, NullPointerException {
    return getFileContentAsString(file, "UTF-8");
  }

  /**
   * Returns the content of the specified file as a string using the specified charset.
   *
   * @param file the file
   * @param charset the charset
   * @return the content
   * @throws IOException any io exception
   * @throws NullPointerException if any argument is null
   */
  static public String getFileContentAsString(File file, String charset) throws IOException, NullPointerException {
    if (file == null) {
      throw new NullPointerException("No null file accepted");
    }
    FileInputStream is = new FileInputStream(file);
    return new String(getStreamContentAsBytes(is), charset);
  }

  /**
   * Returns the content of the specified file as a string using the specified charset.
   *
   * @param fileName the file name
   * @param charset the charset
   * @return the content
   * @throws IOException any io exception
   * @throws NullPointerException if any argument is null
   */
  static public String getFileContentAsString(String fileName, String charset) throws IOException, NullPointerException {
    if (fileName == null) {
      throw new NullPointerException("No null file name accepted");
    }
    return getFileContentAsString(new File(fileName), charset);
  }

  /**
   * Returns the content of the specified file as a string using the <code>UTF-8<code> charset.
   *
   * @param fileName the file name
   * @return the content
   * @throws IOException any io exception
   * @throws NullPointerException if any argument is null
   */
  static public String getFileContentAsString(String fileName) throws IOException, NullPointerException {
    return getFileContentAsString(fileName, "UTF-8");
  }

  /**
   * Returns the content of the specified stream as a string using the <code>UTF-8</code> charset.
   *
   * @param is the stream
   * @return the content
   * @throws IOException any io exception
   * @throws NullPointerException if the specified stream is null
   */
  static public String getStreamContentAsString(InputStream is) throws IOException, NullPointerException {
    byte buf[] = getStreamContentAsBytes(is);
    return new String(buf, "UTF-8");
  }

  /**
   * Returns the content of the file specified by its name as a byte array.
   *
   * @param fileName the file name
   * @return the content
   * @throws IOException any io exception
   * @throws NullPointerException if the specified file name is null
   */
  static public byte[] getFileContentAsBytes(String fileName) throws IOException, NullPointerException {
    if (fileName == null) {
      throw new NullPointerException("No null file name accepted");
    }
    FileInputStream is = new FileInputStream(fileName);
    return getStreamContentAsBytes(is);
  }

  /**
   * Reads a stream until its end and returns its content as a byte array. The provided
   * stream will be closed by this method. Any runtime exception thrown when the stream is closed will
   * be ignored and not rethrown. 
   *
   * @param is the input stream
   * @return the data read from the input stream its end
   * @throws IOException if any IOException occurs during a read
   * @throws NullPointerException if the provided input stream is null
   */
  static public byte[] getStreamContentAsBytes(InputStream is) throws IOException, NullPointerException {
    if (is == null) {
      throw new NullPointerException("No null input stream accepted");
    }
    try {
      ByteArrayOutputStream output = new ByteArrayOutputStream();
      byte[] data = new byte[DEFAULT_BUFFER_SIZE];
      int available;
      while ((available = is.read(data)) > -1) {
        output.write(data, 0, available);
      }
      return output.toByteArray();
    }
    finally {
      if (is != null) {
        try {
          is.close();
        }
        catch (IOException ignore) {
        }
      }
    }
  }

  /**
   * Get a resource from the thread context classloader and returns its content as a string. The resource
   * is obtained by calling the method {@link java.lang.ClassLoader#getResource(String)} on the context classloader
   * associated with the current thread of execution. The charset used for encoding the resource as a string is
   * <code>UTF-8</code>.
   *
   * @param resource the resource name
   * @return the resource content
   * @throws NullPointerException if the specified argument is null or the loaded resource does not exist
   * @throws IOException thrown by accessing the resource
   */
  static public String getResourceAsString(String resource) throws IOException {
    byte[] bytes = getResourceAsBytes(resource);
    return new String(bytes, "UTF-8");
  }

  /**
   * Get a resource from the thread context classloader and returns its content as a byte array. The resource
   * is obtained by calling the method {@link java.lang.ClassLoader#getResource(String)} on the context classloader
   * associated with the current thread of execution.
   *
   * @param resource the resource name
   * @return the resource content
   * @throws NullPointerException if the specified argument is null or the loaded resource does not exist
   * @throws IOException thrown by accessing the resource
   */
  static public byte[] getResourceAsBytes(String resource) throws IOException {
    if (resource == null) {
      throw new NullPointerException("Cannot provide null resource values");
    }
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    URL url = cl.getResource(resource);
    if (url == null) {
      throw new NullPointerException("The resource " + resource + " was not found in the thread context classloader");
    }
    InputStream is = url.openStream();
    return getStreamContentAsBytes(is);
  }

  // Deprecated stuf ***************************************************************************************************

  @Deprecated
  static public byte[] serialize(Object obj) throws Exception {
    // long start = System.currentTimeMillis() ;
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    ObjectOutputStream out = new ObjectOutputStream(bytes);
    out.writeObject(obj);
    out.close();
    byte[] ret = bytes.toByteArray();
    // long end = System.currentTimeMillis() ;
    return ret;
  }

  @Deprecated
  static public Object deserialize(byte[] bytes) throws Exception {
    if (bytes == null)
      return null;
    // long start = System.currentTimeMillis() ;
    ByteArrayInputStream is = new ByteArrayInputStream(bytes);
    ObjectInputStream in = new ObjectInputStream(is);
    Object obj = in.readObject();
    in.close();
    return obj;
  }

  /**
   * Use {@link #getFileContentAsString(File)} instead.
   */
  @Deprecated
  static public String getFileContenntAsString(File file) throws Exception {
    return getFileContentAsString(file);
  }

  /**
   * Use {@link #getFileContentAsString(File,String)} instead.
   */
  @Deprecated
  static public String getFileContenntAsString(File file, String encoding) throws Exception {
    return getFileContentAsString(file, encoding);
  }

  /**
   * Use {@link #getFileContentAsString(String,String)} instead.
   */
  @Deprecated
  static public String getFileContenntAsString(String fileName, String encoding) throws Exception {
    return getFileContentAsString(fileName, encoding);
  }

  /**
   * Use {@link #getFileContentAsString(String)} instead.
   */
  @Deprecated
  static public String getFileContenntAsString(String fileName) throws Exception {
    return getFileContentAsString(fileName);
  }

}
