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

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A property manager that acts as a facade of the system properties. The manager has a cache that is only disabled
 * if the property exo.product.developing is set to the false string. The cache usage is read once during the static
 * initialization of the cache and it can be programmatically triggered by calling the {@link #refresh()} method.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class PropertyManager {

  /** . */
  public static final String DEVELOPING = "exo.product.developing";

  /** . */
  private static final ConcurrentMap<String, String> cache = new ConcurrentHashMap<String, String>();

  /** This is read only once at startup. */
  private static volatile boolean useCache;

  /** . */
  private static volatile boolean developping;

  static {
    refresh();
  }

  /**
   * Returns a property from the provided property name. If the property value is not found it returns null.
   *
   * @param propertyName the property name
   * @return the property value
   */
  public static String getProperty(String propertyName) {
    if (useCache) {
      if (DEVELOPING.equals(propertyName)) {
        return developping ? "true" : "false";
      } else {
        String propertyValue = cache.get(propertyName);
        if (propertyValue == null) {
          propertyValue = System.getProperty(propertyName);
          if (propertyValue != null) {
            cache.put(propertyName, propertyValue);
          }
        }
        return propertyValue;
      }
    } else {
      return System.getProperty(propertyName);
    }
  }

  /**
   * Returns true if the product developing mode is enabled.
   *
   * @return the product developing mode
   */
  public static boolean isDevelopping() {
    if (useCache ) {
      return developping;
    } else {
      return internalIsDevelopping();
    }
  }

  private static boolean internalIsDevelopping() {
    return "true".equals(System.getProperty(DEVELOPING, "false"));
  }

  /**
   * Update a property in the system properties and in the cache.
   *
   * @param propertyName the property name
   * @param propertyValue the property value
   */
  public synchronized static void setProperty(String propertyName, String propertyValue) {
    System.setProperty(propertyName, propertyValue);

    // Remove instead of put to avoid concurrent race
    cache.remove(propertyName);

    //
    if (DEVELOPING.equals(propertyName)) {
      developping = internalIsDevelopping();
    }
  }

  /**
   * Returns true if the cache is enabled.
   *
   * @return the use cache value
   */
  public synchronized static boolean getUseCache() {
    return useCache;
  }

  /**
   * Refresh the property manager. The cache is cleared and the cache usage is read from the system properties.
   */
  public synchronized static void refresh() {
    useCache = !internalIsDevelopping();
    developping = internalIsDevelopping();
    cache.clear();
  }
}
