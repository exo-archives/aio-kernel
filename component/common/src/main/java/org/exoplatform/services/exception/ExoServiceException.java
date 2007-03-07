/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.exception;

/**
 * @author: Tuan Nguyen
 * @version: $Id: ExoServiceException.java 5332 2006-04-29 18:32:44Z geaz $
 * @since: 0.0
 * @email: tuan08@yahoo.com
 */
public class ExoServiceException extends Exception {
  protected Object[] params_ ; 
  protected String key_ = "SystemException" ;
  protected String keyDesc_ = "SystemExceptionDesc" ;

  public ExoServiceException() {
  }

  public ExoServiceException(Throwable ex) {
    super(ex.getMessage()) ;
    ex.printStackTrace() ;
  }

  public ExoServiceException(String s) {
    super(s) ;
  }

  public ExoServiceException(String key, Object[] params) {
    key_ = key ;
    keyDesc_ = key + "Desc";
    params_ = params ;
  }
}
