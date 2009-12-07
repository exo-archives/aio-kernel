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
package org.exoplatform.container.xml;

import org.exoplatform.xml.object.XMLObject;

/**
 * Jul 19, 2004
 * 
 * @author: Tuan Nguyen
 * @email: tuan08@users.sourceforge.net
 * @version: $Id: ObjectParameter.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class ObjectParameter extends Parameter {
  Object object;

  public Object getObject() {
    return object;
  }

  public void setObject(Object obj) {
    object = obj;
  }

  public XMLObject getXMLObject() throws Exception {
    if (object == null)
      return null;
    return new XMLObject(object);
  }

  public void setXMLObject(XMLObject xmlobject) throws Exception {
    if (xmlobject == null)
      object = null;
    try {
      object = xmlobject.toObject();
    } catch (Exception t) {
      // System .err.println("ERRORL: Cannot set value for  param : " +
      // getName()) ;
      throw t;
    }
  }

  public String toString() {
    return object.toString();
  }
}
