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

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Nov 8, 2004
 * @version $Id: MemoryInfo.java 12726 2007-02-12 05:01:32Z tuan08 $
 */
public class MemoryInfo {
  private MemoryMXBean mxbean_;

  public MemoryInfo() {
    mxbean_ = ManagementFactory.getMemoryMXBean();
  }

  public MemoryUsage getHeapMemoryUsage() {
    return mxbean_.getHeapMemoryUsage();
  }

  public MemoryUsage getNonHeapMemoryUsage() {
    return mxbean_.getNonHeapMemoryUsage();
  }

  public int getObjectPendingFinalizationCount() {
    return mxbean_.getObjectPendingFinalizationCount();
  }

  public boolean isVerbose() {
    return mxbean_.isVerbose();
  }

  public void printMemoryInfo() {
    System.out.println("  Memory Heap Usage: " + mxbean_.getHeapMemoryUsage());
    System.out.println("  Memory Non Heap Usage" + mxbean_.getNonHeapMemoryUsage());
  }
}
