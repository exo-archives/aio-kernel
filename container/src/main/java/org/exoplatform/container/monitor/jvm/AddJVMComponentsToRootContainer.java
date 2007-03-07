/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.monitor.jvm;

import java.lang.management.ManagementFactory;

import org.exoplatform.container.BaseContainerLifecyclePlugin;
import org.exoplatform.container.ExoContainer;
/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Jan 11, 2005
 * @version $Id: AddJVMComponentsToRootContainer.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class AddJVMComponentsToRootContainer extends BaseContainerLifecyclePlugin {
  
  public void initContainer(ExoContainer container) {
    container.registerComponentInstance(ManagementFactory.getOperatingSystemMXBean()) ;
    container.registerComponentInstance(ManagementFactory.getRuntimeMXBean()) ;
    container.registerComponentInstance(ManagementFactory.getThreadMXBean()) ;
    container.registerComponentInstance(ManagementFactory.getClassLoadingMXBean()) ;
    container.registerComponentInstance(ManagementFactory.getCompilationMXBean()) ;
    
    container.registerComponentInstance(new MemoryInfo()) ;
    container.registerComponentInstance(JVMRuntimeInfo.MEMORY_MANAGER_MXBEANS, ManagementFactory.getMemoryManagerMXBeans()) ;
    container.registerComponentInstance(JVMRuntimeInfo.MEMORY_POOL_MXBEANS, ManagementFactory.getMemoryPoolMXBeans()) ;
    container.registerComponentInstance(JVMRuntimeInfo.GARBAGE_COLLECTOR_MXBEANS, ManagementFactory.getGarbageCollectorMXBeans()) ;
  }
}