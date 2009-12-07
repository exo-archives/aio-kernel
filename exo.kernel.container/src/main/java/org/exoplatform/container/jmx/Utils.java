/**
 * Copyright (C) The MX4J Contributors.
 * All rights reserved.
 *
 * This software is distributed under the terms of the MX4J License version 1.0.
 * See the terms of the MX4J License in the documentation provided with this software.
 */
package org.exoplatform.container.jmx;

import java.lang.reflect.Array;

/**
 * Several utility functions for the JMX implementation
 * 
 * @version $Revision: 1.18 $
 */
public class Utils {
  public static Class loadClass(ClassLoader loader, String name) throws ClassNotFoundException {
    if (name == null)
      throw new ClassNotFoundException("null");
    name = name.trim();
    if (name.equals("boolean"))
      return boolean.class;
    else if (name.equals("byte"))
      return byte.class;
    else if (name.equals("char"))
      return char.class;
    else if (name.equals("short"))
      return short.class;
    else if (name.equals("int"))
      return int.class;
    else if (name.equals("long"))
      return long.class;
    else if (name.equals("float"))
      return float.class;
    else if (name.equals("double"))
      return double.class;
    else if (name.equals("java.lang.String"))
      return String.class;
    else if (name.equals("java.lang.Object"))
      return Object.class;
    else if (name.startsWith("[")) {
      int dimension = 0;
      while (name.charAt(dimension) == '[') {
        ++dimension;
      }
      char type = name.charAt(dimension);
      Class cls = null;
      switch (type) {
      case 'Z':
        cls = boolean.class;
        break;
      case 'B':
        cls = byte.class;
        break;
      case 'C':
        cls = char.class;
        break;
      case 'S':
        cls = short.class;
        break;
      case 'I':
        cls = int.class;
        break;
      case 'J':
        cls = long.class;
        break;
      case 'F':
        cls = float.class;
        break;
      case 'D':
        cls = double.class;
        break;
      case 'L':
        // Strip the semicolon at the end
        String n = name.substring(dimension + 1, name.length() - 1);
        cls = loadClass(loader, n);
        break;
      }

      if (cls == null) {
        throw new ClassNotFoundException(name);
      }
      int[] dim = new int[dimension];
      return Array.newInstance(cls, dim).getClass();
    } else {
      if (loader != null)
        return loader.loadClass(name);
      return Class.forName(name, false, null);
    }
  }

  /**
   * Returns the classes whose names are specified by the <code>names</code>
   * argument, loaded with the specified classloader.
   */
  public static Class[] loadClasses(ClassLoader loader, String[] names) throws ClassNotFoundException {
    int n = names.length;
    Class[] cls = new Class[n];
    for (int i = 0; i < n; ++i) {
      String name = names[i];
      cls[i] = loadClass(loader, name);
    }
    return cls;
  }

  public static boolean arrayEquals(Object[] arr1, Object[] arr2) {
    if (arr1 == null && arr2 == null)
      return true;
    if (arr1 == null ^ arr2 == null)
      return false;
    if (!arr1.getClass().equals(arr2.getClass()))
      return false;
    if (arr1.length != arr2.length)
      return false;

    for (int i = 0; i < arr1.length; ++i) {
      Object obj1 = arr1[i];
      Object obj2 = arr2[i];
      if (obj1 == null ^ obj2 == null)
        return false;
      if (obj1 != null && !obj1.equals(obj2))
        return false;
    }
    return true;
  }
}
