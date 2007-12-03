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

import java.util.List ;
import java.util.Collection ;
import org.apache.commons.logging.Log;

/**
 * Created by the Exo Development team.
 * Author : Mestrallet Benjamin
 *          benjmestrallet@users.sourceforge.net
 * Date: 14 nov. 2003
 * Time: 20:19:45
 */
public interface LogService {
	final static public int FATAL = 0 ;
  final static public int ERROR = 1 ;
  final static public int WARN = 2 ;
  final static public int INFO = 3 ;
  final static public int DEBUG = 4 ;
  final static public int TRACE = 5 ;

  public Log getLog(Class clazz);
  public Log getLog(String name);
  public Collection getLogs() ;
    
  public int  getLogLevel(String name) throws Exception ;
  public void setLogLevel(String name, int level, boolean recursive) throws Exception ;
  
  public List getLogBuffer() ; 
  public List getErrorBuffer() ; 
}
