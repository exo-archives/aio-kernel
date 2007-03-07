/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.monitor.jvm;

import java.util.List;
import java.util.Map;
/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Nov 8, 2004
 * @version $Id: JVMRuntimeInfo.java 5799 2006-05-28 17:55:42Z geaz $
 */
public interface  JVMRuntimeInfo  {
  final static public String MEMORY_MANAGER_MXBEANS = "MemoryManagerMXBean" ;
  final static public String MEMORY_POOL_MXBEANS = "MemoryPoolMXBeans" ;
  final static public String GARBAGE_COLLECTOR_MXBEANS = "GarbageCollectorMXBeans"; 
  
  String  getName() ;
  String  getSpecName() ;
  String  getSpecVendor() ;
  String  getSpecVersion() ;
  String  getManagementSpecVersion() ;
  
  String  getVmName() ;
  String  getVmVendor() ;
  String  getVmVersion() ;
  
  List    getInputArguments() ;
  Map     getSystemProperties() ;
  
  boolean getBootClassPathSupported() ;
  String  getBootClassPath() ;
  String  getClassPath() ; 
  String  getLibraryPath() ;
  
  long    getStartTime() ;
  long    getUptime() ;
  
  public boolean isManagementSupported() ;
  
  public String getSystemPropertiesAsText() ;
}