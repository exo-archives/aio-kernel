/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.jmx;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * Created by The eXo Platform SARL
 * Author : Tuan Nguyen
 *          tuan08@users.sourceforge.net
 * Oct 28, 2005
 */
public class JMXProxyComponent {
  public static Object getComponent(Class intf, MBeanServer mbeanServer, ObjectName objectName) {
    ClassLoader cl = Thread.currentThread().getContextClassLoader() ;
    return Proxy.newProxyInstance(cl,
                                  new Class[]{intf},
                                  new Handler(mbeanServer, objectName));
  }
  
  static public class Handler implements InvocationHandler {
    MBeanServer mserver_ ;
    ObjectName objectName_ ;
    
    public Handler(MBeanServer mbeanServer, ObjectName objectName) {
      mserver_ = mbeanServer ;
      objectName_ = objectName ;
    }
    
    public Object invoke(Object proxy, Method m, Object[] params) throws Throwable {
      Object result = null;
      try {
        String[] signatures = null ;
        if(params != null) {
          signatures = new String[params.length] ;
          Class[] types = m.getParameterTypes() ;
          for(int i = 0 ; i < params.length ; i++) {
            signatures[i] = types[i].getName() ;
          }
        }
        result = mserver_.invoke(objectName_, m.getName(), params, signatures);
      } catch (Exception e) {
        e.printStackTrace() ;
      }
      return result;
    }
  }
}
