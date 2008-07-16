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
package org.exoplatform.services.log;

import org.apache.commons.logging.Log;
import org.exoplatform.container.ExoContainer;
import org.exoplatform.container.ExoContainerContext;

/**
 * Jun 28, 2004 
 * @author: Tuan Nguyen
 * @email:   tuan08@users.sourceforge.net
 * @version: $Id: LogUtil.java 5332 2006-04-29 18:32:44Z geaz $
 * @deprecated use ExoLogger instead
 */
public class LogUtil {
	static private Log defaultInstance_ = null ;

  /**
   * @deprecated use ExoLogger
   * @see ExoLogger#getLogger(String)
   */
	public static Log getLog(String category) {
		ExoContainer manager  = ExoContainerContext.getTopContainer();
		LogService service = (LogService) manager.getComponentInstanceOfType(LogService.class) ;
		Log log = service.getLog(category) ;
		return log ;
	}
	
  /**
   * @deprecated use ExoLogger
   * @see ExoLogger#getLogger(Class)
   */	
  public static Log getLog(Class clazz) {
    ExoContainer manager  = ExoContainerContext.getTopContainer();
    LogService service = (LogService) manager.getComponentInstanceOfType(LogService.class) ;   
    Log log = service.getLog(clazz) ;
    return log ;
  }
  
	public static void setLevel(String category, int level, boolean recursive) throws Exception {
		ExoContainer manager  = ExoContainerContext.getTopContainer();
		LogService service = (LogService) manager.getComponentInstanceOfType(LogService.class) ;
		service.setLogLevel(category, level, recursive) ;
	}
	
	static public void debug(String msg) {	getDefault().debug(msg) ;	}
	static public void debug(String msg, Throwable t) {	getDefault().debug(msg, t) ;	}
	
	static public void info(String msg) {	getDefault().info(msg) ;	}
	static public void info(String msg, Throwable t) {	getDefault().info(msg, t) ;	}
	
	static public void warn(String msg) {	getDefault().warn(msg) ;	}
	static public void warn(String msg, Throwable t) {	getDefault().warn(msg, t) ;	}
	
	static public void error(String msg) {	getDefault().error(msg) ;	}
	static public void error(String msg, Throwable t) {	getDefault().error(msg, t) ;	}
	
	final static public Log getDefault() {
		if(defaultInstance_ == null) {
			defaultInstance_ = getLog("default") ;
		}
		return defaultInstance_ ;
	}
}