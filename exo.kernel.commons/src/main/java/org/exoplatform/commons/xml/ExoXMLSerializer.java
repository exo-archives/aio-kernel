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
package org.exoplatform.commons.xml;

import org.xmlpull.mxp1_serializer.MXSerializer;

/**
 * Jul 8, 2004
 * 
 * @author: Tuan Nguyen
 * @email: tuan08@users.sourceforge.net
 * @version: $Id: ExoXMLSerializer.java,v 1.1 2004/07/08 19:24:47 tuan08 Exp $
 */
public class ExoXMLSerializer extends MXSerializer {
  final static public String INDENTATION    = "http://xmlpull.org/v1/doc/properties.html#serializer-indentation";

  final static public String LINE_SEPARATOR = "http://xmlpull.org/v1/doc/properties.html#serializer-line-separator";

  public void element(String ns, String tag, String text) throws Exception {
    if (text == null)
      return;
    startTag(ns, tag);
    text(text);
    endTag(ns, tag);
  }

  static public ExoXMLSerializer getInstance() {
    ExoXMLSerializer ser = new ExoXMLSerializer();
    ser.setProperty(ExoXMLSerializer.INDENTATION, "  ");
    ser.setProperty(ExoXMLSerializer.LINE_SEPARATOR, "\n");
    return ser;
  }
}
