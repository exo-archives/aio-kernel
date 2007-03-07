package org.exoplatform.services.log;

import java.util.List ;
import java.util.Collection ;
import org.apache.commons.logging.Log;

/**
 * Copyright 2001-2003 The eXo platform SARL All rights reserved.
 * Please look at license.txt in info directory for more license detail.
 **/

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
