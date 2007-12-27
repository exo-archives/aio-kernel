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
import java.lang.management.OperatingSystemMXBean;
import java.net.URL;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Nov 8, 2004
 * @version $Id: OperatingSystemInfoImpl.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class OperatingSystemInfoImpl implements OperatingSystemInfo {
  private OperatingSystemMXBean  mxbean_ ;
  
  public OperatingSystemInfoImpl() {
    mxbean_ = ManagementFactory.getOperatingSystemMXBean();
  }
  
  public String getArch() { return mxbean_.getArch() ; }

  public String getName() { return mxbean_.getName()  ; } 
  
  public String getVersion() { return mxbean_.getVersion() ;}
  
  public int    getAvailableProcessors()  { return mxbean_.getAvailableProcessors() ; }
  
  public URL createURL(String file)  throws Exception { 
    return new URL("file:" + file) ; 
  }
  
  public String toString() {
    StringBuilder b = new StringBuilder() ;
    b.append("Operating System: ").append(getName()).append("\n") ;
    b.append("Operating  System Version : ").append(getVersion()).append("\n") ;
    b.append("CPU Achitechure : ").append(getArch()).append("\n") ;
    b.append("Number of Processors : ").append(getAvailableProcessors()).append("\n") ;
    return b.toString() ;
  }
}