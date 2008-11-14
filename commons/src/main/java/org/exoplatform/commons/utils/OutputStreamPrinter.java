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

import java.io.OutputStream;
import java.io.IOException;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class OutputStreamPrinter extends Printer {

  private final OutputStream out;
  private final TextEncoder encoder;

  public OutputStreamPrinter(TextEncoder encoder, OutputStream out) throws IllegalArgumentException {
    if (encoder == null) {
      throw new IllegalArgumentException("No null encoder accepted");
    }
    if (out == null) {
      throw new IllegalArgumentException("No null output stream accepted");
    }
    this.encoder = encoder;
    this.out = out;
  }

  @Override
  public void write(int c) throws IOException {
    encoder.encode((char)c, out);
  }

  @Override
  public void write(char[] cbuf) throws IOException {
    encoder.encode(cbuf, 0, cbuf.length, out);
  }

  @Override
  public void write(String str) throws IOException {
    encoder.encode(str, 0, str.length(), out);
  }

  @Override
  public void write(String str, int off, int len) throws IOException {
    encoder.encode(str, off, len, out);
  }

  public void write(char[] cbuf, int off, int len) throws IOException {
    encoder.encode(cbuf, off, len, out);
  }

  public void flush() throws IOException {
    out.flush();
  }

  public void close() throws IOException {
    out.close();
  }
}
