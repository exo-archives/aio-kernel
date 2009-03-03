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

import org.exoplatform.management.jmx.annotations.NamingContext;
import org.exoplatform.management.jmx.annotations.Property;
import org.exoplatform.management.jmx.annotations.NameTemplate;
import org.exoplatform.commons.reflect.AnnotationIntrospector;

import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.lang.annotation.Annotation;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class PropertiesInfo {

  /** . */
  private Map<String, PropertyInfo> properties;

  public PropertiesInfo(Map<String, PropertyInfo> properties) {
    this.properties = properties;
  }

  public static PropertiesInfo resolve(Class clazz, Class<? extends Annotation> annotationClass) {
    Annotation tpl2 = AnnotationIntrospector.resolveClassAnnotations(clazz, annotationClass);
    Property[] blah = null;
    if (tpl2 instanceof NamingContext) {
      blah = ((NamingContext)tpl2).value();
    } else if (tpl2 instanceof NameTemplate) {
      blah = ((NameTemplate)tpl2).value();
    }
    if (blah != null) {
      Map<String, PropertyInfo> properties = new HashMap<String, PropertyInfo>();
      for (Property property : blah) {
        PropertyInfo propertyInfo = new PropertyInfo(clazz, property);
        properties.put(propertyInfo.getKey(), propertyInfo);
      }
      return new PropertiesInfo(properties);
    } else {
      return null;
    }
  }

  public Collection<PropertyInfo> getProperties() {
    return properties.values();
  }

  public Map<String, String> resolve(Object instance) {
    Map<String, String> props = new HashMap<String, String>();
    for (PropertyInfo propertyInfo : properties.values()) {
      String key = propertyInfo.getKey();
      String value = propertyInfo.resolveValue(instance);
      props.put(key, value);
    }
    return props;
  }
}
