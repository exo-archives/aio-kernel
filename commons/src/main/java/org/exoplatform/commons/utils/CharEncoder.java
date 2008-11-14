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

import java.nio.charset.CharsetEncoder;
import java.nio.charset.Charset;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.io.OutputStreamWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class CharEncoder {

  private static final byte[] EMPTY = new byte[0];
  private final Charset charset;
  private final java.nio.CharBuffer in;
  private final ByteBuffer out;
  private final byte[][] arrays;

  public CharEncoder(Charset charset) {
    this.charset = charset;
    this.in = CharBuffer.allocate(1);
    this.out = ByteBuffer.allocate(100);
    this.arrays = new byte[][]{new byte[0], new byte[1], new byte[2], new byte[3], new byte[4], new byte[5]};
  }

  public byte[] encode(char c) {
/*
    switch (Character.getType(c)) {
      case Character.SURROGATE:
      case Character.PRIVATE_USE:
        return EMPTY;
      default:
        if (encoder.canEncode(c)) {
          in.rewind();
          out.rewind();
          in.put(0, c);
          encoder.reset();
          encoder.encode(in, out, true);
          encoder.flush(out);
          int length = out.position();
          byte[] bytes = arrays[length];
          System.arraycopy(out.array(), 0, bytes, 0, length);
          return bytes;
        }
        else {
          return EMPTY;
        }
    }
*/
    try {
      CharsetEncoder encoder = charset.newEncoder();
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      OutputStreamWriter writer = new OutputStreamWriter(baos, encoder);
      writer.write(c);
      writer.close();
      return baos.toByteArray();
    }
    catch (IOException e) {
      throw new AssertionError(e);
    }
  }
}
