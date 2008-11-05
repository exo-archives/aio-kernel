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
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Jan 11, 2005
 * @version $Id: AddJVMComponentsToRootContainer.java 5799 2006-05-28 17:55:42Z
 *          geaz $
 */
public class AddJVMComponentsToRootContainer extends BaseContainerLifecyclePlugin {

  private static final Log log = LogFactory.getLog(AddJVMComponentsToRootContainer.class);

  public void initContainer(ExoContainer container) {
    attemptToRegisterMXComponent(container, ManagementFactory.getOperatingSystemMXBean());
    attemptToRegisterMXComponent(container, ManagementFactory.getRuntimeMXBean());
    attemptToRegisterMXComponent(container, ManagementFactory.getThreadMXBean());
    attemptToRegisterMXComponent(container, ManagementFactory.getClassLoadingMXBean());
    attemptToRegisterMXComponent(container, ManagementFactory.getCompilationMXBean());

    attemptToRegisterMXComponent(container, new MemoryInfo());
    attemptToRegisterMXComponent(container, JVMRuntimeInfo.MEMORY_MANAGER_MXBEANS,
                                        ManagementFactory.getMemoryManagerMXBeans());
    attemptToRegisterMXComponent(container, JVMRuntimeInfo.MEMORY_POOL_MXBEANS,
                                        ManagementFactory.getMemoryPoolMXBeans());
    attemptToRegisterMXComponent(container, JVMRuntimeInfo.GARBAGE_COLLECTOR_MXBEANS,
                                        ManagementFactory.getGarbageCollectorMXBeans());
  }

  private void attemptToRegisterMXComponent(ExoContainer container, Object mxComponent) {
    if (mxComponent != null) {
      log.debug("Attempt to register mx component " + mxComponent);
      container.registerComponentInstance(mxComponent);
      log.debug("Mx component " + mxComponent + " registered");
    }
  }

  private void attemptToRegisterMXComponent(ExoContainer container, Object mxKey, Object mxComponent) {
    if (mxComponent != null) {
      log.debug("Attempt to register mx component " + mxComponent + " with key " + mxKey);
      container.registerComponentInstance(mxKey, mxComponent);
      log.debug("Mx component " + mxComponent + " registered");
    }
  }

}
