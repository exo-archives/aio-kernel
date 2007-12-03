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
package org.exoplatform.container.monitor.jvm;

import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

import org.exoplatform.container.RootContainer;
import org.exoplatform.test.BasicTestCase;
/**
 * Thu, May 15, 2003 @   
 * @author: Tuan Nguyen
 * @version: $Id: TestJVMManagement.java 5799 2006-05-28 17:55:42Z geaz $
 * @since: 0.0
 * @email: tuan08@yahoo.com
 */
public class TestJVMManagement extends BasicTestCase {

  public TestJVMManagement(String name) {
    super(name);
  }
  
  public void testThreadManagement() {
    ThreadMXBean threadBean = 
      (ThreadMXBean)RootContainer.getComponent(ThreadMXBean.class) ;
    if(threadBean == null) return ;
    ThreadInfo[] infos = threadBean.getThreadInfo(threadBean.getAllThreadIds());
    for(int i = 0; i < infos.length; i++) {
      System.out.println("Thread Name" + infos[i].getThreadName()) ;
    }
  }

}
