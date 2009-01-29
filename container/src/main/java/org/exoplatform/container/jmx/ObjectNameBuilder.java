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
import org.exoplatform.commons.reflect.AnnotationIntrospector;

import javax.management.ObjectName;
import javax.management.MalformedObjectNameException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.InvocationTargetException;
import java.util.Hashtable;

/**
 * A builder for object name templates.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class ObjectNameBuilder {

  private Object object;

  /**
   * Create a new builder.
   *
   * @param object
   * @throws IllegalArgumentException if the object is null
   */
  public ObjectNameBuilder(Object object) throws IllegalArgumentException {
    if (object == null) {
      throw new IllegalArgumentException("Object cannot be null");
    }
    this.object = object;
  }

  /**
   * Build the object name or return null if the class is not annotated by
   * {@link NameTemplate}.
   *
   * @return the built name
   * @throws IllegalStateException raised by a build time issue 
   */
  public ObjectName build() throws IllegalStateException {
    Class clazz = object.getClass();

    //
    NameTemplate tpl = AnnotationIntrospector.resolveClassAnnotations(clazz, NameTemplate.class);

    //
    if (tpl != null) {

      //
      try {
        ObjectName tmp = new ObjectName(tpl.value());
        Hashtable props = new Hashtable();
        for (Object o : tmp.getKeyPropertyList().keySet()) {
          String propertyName = (String)o;
          String propertyValue = tmp.getKeyProperty(propertyName);
          int length = propertyValue.length();
          if (length > 2) {
            if (propertyValue.charAt(0) == '{' && propertyValue.charAt(length - 1) == '}') {
              String s = propertyValue.substring(1, length - 1);
              String getterName = "get" + s;
              Method getter;
              try {
                getter = clazz.getMethod(getterName);
              }
              catch (NoSuchMethodException e) {
                throw new IllegalArgumentException("Getter parameter for property " + s + " on class " +
                  clazz.getName() + " does not exist", e);
              }

              //
              if (getter.getReturnType() == void.class) {
                throw new IllegalArgumentException("Getter return type for property " + s + " on class " +
                  clazz.getName() + " cannot be void");
              }
              if (getter.getParameterTypes().length > 0) {
                throw new IllegalArgumentException("Getter parameter type for property " + s + " on class " +
                  clazz.getName() + " is not empty");
              }
              if (Modifier.isStatic(getter.getModifiers())) {
                throw new IllegalArgumentException("Getter for property " + s + " on class " +
                  clazz.getName() + " is static");
              }

              //
              Object value;
              try {
                value = getter.invoke(object);
              }
              catch (IllegalAccessException e) {
                throw new IllegalArgumentException("Getter for property " + s + " on class " +
                  clazz.getName() + " cannot be invoked", e);
              }
              catch (InvocationTargetException e) {
                throw new IllegalArgumentException("Getter for property " + s + " on class " +
                  clazz.getName() + " threw an exception during invocation", e);
              }
              if (value == null) {
                throw new IllegalArgumentException("Getter for property " + s + " on class " +
                  clazz.getName() + " returned a null value");
              }
              propertyValue = ObjectName.quote(value.toString());
            }
          }
          props.put(propertyName, propertyValue);
        }
        return new ObjectName(tmp.getDomain(), props);
      }
      catch (MalformedObjectNameException e) {
        throw new IllegalArgumentException("ObjectName template " + tpl.value() + " is malformed", e);
      }
    }

    //
    return null;
  }
}
