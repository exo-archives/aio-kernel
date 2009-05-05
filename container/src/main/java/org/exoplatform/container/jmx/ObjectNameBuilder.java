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
package org.exoplatform.container.jmx;

import org.exoplatform.management.jmx.annotations.NameTemplate;

import javax.management.ObjectName;
import javax.management.MalformedObjectNameException;
import java.util.Hashtable;
import java.util.Map;

/**
 * A builder for object name templates.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class ObjectNameBuilder<T> {

  /** . */
  private String domain;

  /** . */
  private Class<? extends T> clazz;

  /**
   * Create a new builder.
   *
   * @param clazz the class
   * @throws IllegalArgumentException if the object is null
   */
  public ObjectNameBuilder(String domain, Class<? extends T> clazz) throws IllegalArgumentException {
    if (clazz == null) {
      throw new IllegalArgumentException("Clazz cannot be null");
    }
    this.domain = domain;
    this.clazz = clazz;
  }

  /**
   * Build the object name or return null if the class is not annotated by
   * {@link NameTemplate}.
   *
   * @param object the object
   * @return the built name
   * @throws IllegalStateException raised by a build time issue 
   */
  public ObjectName build(T object) throws IllegalStateException {
    PropertiesInfo info = PropertiesInfo.resolve(clazz, NameTemplate.class);

    //
    if (info != null) {

      try {
        Map<String, String> props = info.resolve(object);
        return JMX.createObjectName(domain, props);
      }
      catch (MalformedObjectNameException e) {
        throw new IllegalArgumentException("ObjectName template is malformed", e);
      }
    }

    //
    return null;
  }
}
