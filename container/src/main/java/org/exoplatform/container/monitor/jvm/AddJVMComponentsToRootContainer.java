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

import org.exoplatform.container.BaseContainerLifecyclePlugin;
import org.exoplatform.container.ExoContainer;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Jan 11, 2005
 * @version $Id: AddJVMComponentsToRootContainer.java 5799 2006-05-28 17:55:42Z
 *          geaz $
 */
public class AddJVMComponentsToRootContainer extends BaseContainerLifecyclePlugin {

  public void initContainer(ExoContainer container) {
    container.registerComponentInstance(ManagementFactory.getOperatingSystemMXBean());
    container.registerComponentInstance(ManagementFactory.getRuntimeMXBean());
    container.registerComponentInstance(ManagementFactory.getThreadMXBean());
    container.registerComponentInstance(ManagementFactory.getClassLoadingMXBean());
    container.registerComponentInstance(ManagementFactory.getCompilationMXBean());

    container.registerComponentInstance(new MemoryInfo());
    container.registerComponentInstance(JVMRuntimeInfo.MEMORY_MANAGER_MXBEANS,
                                        ManagementFactory.getMemoryManagerMXBeans());
    container.registerComponentInstance(JVMRuntimeInfo.MEMORY_POOL_MXBEANS,
                                        ManagementFactory.getMemoryPoolMXBeans());
    container.registerComponentInstance(JVMRuntimeInfo.GARBAGE_COLLECTOR_MXBEANS,
                                        ManagementFactory.getGarbageCollectorMXBeans());
  }
}
