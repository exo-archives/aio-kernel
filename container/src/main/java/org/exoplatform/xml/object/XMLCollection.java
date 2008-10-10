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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Apr 11, 2005
 * @version $Id: XMLCollection.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class XMLCollection {

  private ArrayList list_ = new ArrayList();

  private String    type_;

  public XMLCollection() {
  }

  public XMLCollection(Collection list) throws Exception {
    Iterator i = list.iterator();
    while (i.hasNext()) {
      Object value = i.next();
      if (value != null) {
        list_.add(new XMLValue(null, value));
      }
    }
    type_ = list.getClass().getName();
  }

  public String getType() {
    return type_;
  }

  public void setType(String s) {
    type_ = s;
  }

  public Collection getCollection() throws Exception {
    Class clazz = Class.forName(type_);
    Collection collection = (Collection) clazz.newInstance();
    for (int i = 0; i < list_.size(); i++) {
      XMLValue value = (XMLValue) list_.get(i);
      collection.add(value.getObjectValue());
    }
    return collection;
  }

  public Iterator getIterator() {
    return list_.iterator();
  }

  public String toXML(String encoding) throws Exception {
    return new String(toByteArray(encoding), encoding);
  }

  public byte[] toByteArray(String encoding) throws Exception {
    IBindingFactory bfact = BindingDirectory.getFactory(XMLObject.class);
    IMarshallingContext mctx = bfact.createMarshallingContext();
    mctx.setIndent(2);
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    mctx.marshalDocument(this, encoding, null, os);
    return os.toByteArray();
  }

  static public XMLCollection getXMLCollection(InputStream is) throws Exception {
    IBindingFactory bfact = BindingDirectory.getFactory(XMLObject.class);
    IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
    return (XMLCollection) uctx.unmarshalDocument(is, null);
  }

  static public Collection getCollection(InputStream is) throws Exception {
    return getXMLCollection(is).getCollection();
  }
}
