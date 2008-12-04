/*****************************************************************************
 * Copyright (C) NanoContainer Organization. All rights reserved.            *
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD      *
 * style license a copy of which has been included with this distribution in *
 * the LICENSE.txt file.                                                     *
 *                                                                           *
 * Original code by James Strachan                                           *
 *****************************************************************************/
package org.exoplatform.container.jmx;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;

import javax.management.MBeanAttributeInfo;
import javax.management.MBeanOperationInfo;

/**
 * @author James Strachan
 * @version $Revision: 1.7 $
 */
public class ExoContainerMBean extends AbstractDynamicMBean {

  public ExoContainerMBean(Object componentInstance) {
    setResource(componentInstance);
  }

  /* Method of the second group that is overridden */
  protected String getMBeanDescription() {
    return "Dynamic mbean wrapper for instance : " + getResource().toString();
  }

  protected MBeanAttributeInfo[] createMBeanAttributeInfo() {
    return super.createMBeanAttributeInfo();
  }

  protected MBeanOperationInfo[] createMBeanOperationInfo() {
    Method[] methodArray = getResource().getClass().getDeclaredMethods();
    Collection<MBeanOperationInfo> cToReturn = new ArrayList<MBeanOperationInfo>();
    for (int i = 0; i < methodArray.length; i++) {
      Method method = methodArray[i];
      if (Modifier.isPublic(method.getModifiers())) {
        MBeanOperationInfo operationInfo = new MBeanOperationInfo(method.getName(), method);
        cToReturn.add(operationInfo);
      }
    }
    return cToReturn.toArray(new MBeanOperationInfo[cToReturn.size()]);
  }
  
  /**
   * Indicates either this MBean can be monitored or not. At least one attribute or one operation is available
   */
  public boolean canBeMonitored() {
    int total = 0;
    final MBeanAttributeInfo[] attributes = createMBeanAttributeInfo();
    if (attributes != null) {
      total += attributes.length;
    }
    final MBeanOperationInfo[] operations = createMBeanOperationInfo();
    if (operations != null) {
      total += operations.length;
    }
    return total > 0;
  }
}
