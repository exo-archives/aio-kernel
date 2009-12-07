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

import org.exoplatform.management.annotations.Managed;
import org.exoplatform.management.annotations.ManagedDescription;

import javax.management.MBeanOperationInfo;
import javax.management.MBeanAttributeInfo;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class TestExoMBean extends AbstractTestExoMBean {

  public void test1() throws Exception {
    assertNotBuildable(MBean1.class);
  }

  public static class MBean1 {
  }

  public void testMBean2() throws Exception {
    Bean bean = register("domain:name=mbean", MBean2.class);
    MBeanOperationInfo[] operationInfos = bean.info.getOperations();
    assertNotNull(operationInfos);
    assertEquals(0, operationInfos.length);
    MBeanAttributeInfo[] mbeanAttributeInfos = bean.info.getAttributes();
    assertNotNull(mbeanAttributeInfos);
    assertEquals(0, mbeanAttributeInfos.length);
  }

  @Managed
  public static class MBean2 {
  }

  public void testMBean3() throws Exception {
    Bean bean = register("domain:name=mbean", MBean3.class);
    assertEquals("Bean_desc", bean.info.getDescription());
  }

  @Managed
  @ManagedDescription("Bean_desc")
  public static class MBean3 {
  }
}
