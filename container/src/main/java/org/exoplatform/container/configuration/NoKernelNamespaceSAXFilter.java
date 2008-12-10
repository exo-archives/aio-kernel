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
package org.exoplatform.container.configuration;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.AttributesImpl;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.Attributes;

import java.util.HashSet;

/**
 * Removes kernel namespace declaration from the document to not confuse the jibx thing.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
class NoKernelNamespaceSAXFilter extends DefaultHandler {

  private ContentHandler contentHandler;

  NoKernelNamespaceSAXFilter(ContentHandler contentHandler) {
    this.contentHandler = contentHandler;
  }

  public void setDocumentLocator(Locator locator) {
    contentHandler.setDocumentLocator(locator);
  }

  public void startDocument() throws SAXException {
    contentHandler.startDocument();
  }

  public void endDocument() throws SAXException {
    contentHandler.endDocument();
  }

  public void startPrefixMapping(String prefix, String uri) throws SAXException {
    contentHandler.startPrefixMapping(prefix, uri);
  }

  public void endPrefixMapping(String prefix) throws SAXException {
    contentHandler.endPrefixMapping(prefix);
  }

  public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
    HashSet<Integer> removals = null;
    for (int i = 0;i < atts.getLength();i++) {
      String attsValue = atts.getValue(i);
      String attsQName = atts.getQName(i);
      if (attsQName.startsWith("xmlns") && attsValue.endsWith("http://www.exoplaform.org/xml/ns/kernel_1_0.xsd")) {
        if (removals == null) {
          removals = new HashSet<Integer>();
        }
        removals.add(i);
      }
    }

    // Filter if needed
    if (removals != null) {
      AttributesImpl tmp = new AttributesImpl();
      for (int i = 0;i < atts.getLength();i++) {
        if (!removals.contains(i)) {
          tmp.addAttribute(atts.getURI(i), atts.getLocalName(i), atts.getQName(i), atts.getType(i), atts.getValue(i));
        }
      }
      atts = tmp;
    }

    //
    contentHandler.startElement(uri, localName, qName, atts);
  }

  public void endElement(String uri, String localName, String qName) throws SAXException {
    contentHandler.endElement(uri, localName, qName);
  }

  public void characters(char[] ch, int start, int length) throws SAXException {
    contentHandler.characters(ch, start, length);
  }

  public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
    contentHandler.ignorableWhitespace(ch, start, length);
  }

  public void processingInstruction(String target, String data) throws SAXException {
    contentHandler.processingInstruction(target, data);
  }

  public void skippedEntity(String name) throws SAXException {
    contentHandler.skippedEntity(name);
  }
}
