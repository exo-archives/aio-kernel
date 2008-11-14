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

import java.nio.charset.Charset;
import java.io.OutputStream;
import java.io.IOException;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class TextEncoder {

  private static final char MAX = (char)0x10FFFD;
  private static final TextEncoder UTF8 = new TextEncoder("UTF8");

  public static TextEncoder getUTF8() {
    return UTF8;
  }

  private final CharEncoder charEncoder;
  private byte[][] table;

  private TextEncoder(CharEncoder charEncoder) {
    this.charEncoder = charEncoder;
    this.table = new byte[MAX + 1][];
  }

  private TextEncoder(String encoding) {
    this(new CharEncoder(Charset.forName(encoding)));
  }

  public void encode(char c, OutputStream out) throws IOException {
    byte[] bytes = table[c];
    if (bytes == null) {
      bytes = charEncoder.encode(c);
      table[c] = bytes;
    }
    out.write(bytes);
  }

  public void encode(char[] chars, int off, int len, OutputStream out) throws IOException {
    for (int i = off; i < len; i++) {
      char c = chars[i];
      byte[] bytes = table[c];
      if (bytes == null) {
        bytes = charEncoder.encode(c);
        table[c] = bytes;
      }
      out.write(bytes);
    }
  }

  public void encode(String str, int off, int len, OutputStream out) throws IOException {
    for (int i = off; i < len; i++) {
      char c = str.charAt(i);
      byte[] bytes = table[c];
      if (bytes == null) {
        bytes = charEncoder.encode(c);
        table[c] = bytes;
      }
      out.write(bytes);
    }
  }
}
