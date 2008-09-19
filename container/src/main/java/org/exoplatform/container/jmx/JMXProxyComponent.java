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

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * Created by The eXo Platform SAS Author : Tuan Nguyen
 * tuan08@users.sourceforge.net Oct 28, 2005
 */
public class JMXProxyComponent {
  public static Object getComponent(Class intf, MBeanServer mbeanServer, ObjectName objectName) {
    ClassLoader cl = Thread.currentThread().getContextClassLoader();
    return Proxy.newProxyInstance(cl, new Class[] { intf }, new Handler(mbeanServer, objectName));
  }

  static public class Handler implements InvocationHandler {
    MBeanServer mserver_;

    ObjectName  objectName_;

    public Handler(MBeanServer mbeanServer, ObjectName objectName) {
      mserver_ = mbeanServer;
      objectName_ = objectName;
    }

    public Object invoke(Object proxy, Method m, Object[] params) throws Throwable {
      Object result = null;
      try {
        String[] signatures = null;
        if (params != null) {
          signatures = new String[params.length];
          Class[] types = m.getParameterTypes();
          for (int i = 0; i < params.length; i++) {
            signatures[i] = types[i].getName();
          }
        }
        result = mserver_.invoke(objectName_, m.getName(), params, signatures);
      } catch (Exception e) {
        e.printStackTrace();
      }
      return result;
    }
  }
}
