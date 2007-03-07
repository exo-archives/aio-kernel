/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.commons.exception;

import java.util.ResourceBundle;

/**
 * @author: Tuan Nguyen
 * @version: $Id: ExoException.java,v 1.5 2004/11/03 01:24:55 tuan08 Exp $
 * @since: 0.0
 * @email: tuan08@yahoo.com
 */
abstract public class ExoException extends Exception {
  static final public int FATAL  = 0 ;
  static final public int ERROR  = 1 ;
  static final public int WARN   = 2 ;
  static final public int INFO   = 3 ;
  
  private int severity_ = INFO ;
  
  public int getSeverity() { return severity_  ;} 
  public void setSeverity(int severity) { severity_ = severity ; } 
  
  abstract public String getMessage(ResourceBundle res) ;
  abstract public String getExceptionDescription() ;
  abstract public String getErrorCode() ;
}