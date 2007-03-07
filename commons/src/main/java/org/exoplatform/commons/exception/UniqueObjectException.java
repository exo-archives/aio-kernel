/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.commons.exception;
/*
 * @author: Tuan Nguyen
 * @version: $Id: UniqueObjectException.java,v 1.1 2004/10/21 15:23:40 tuan08 Exp $
 * @since: 0.0
 * @email: tuan08@yahoo.com
 */
public class UniqueObjectException extends ExoMessageException {
  
  public UniqueObjectException(String messageKey) {
    super(messageKey) ;
  }
  
  public UniqueObjectException(String messageKey, Object[] args) {
    super(messageKey, args) ; 
  }
  
  public String getExceptionDescription() {
    return "Usually, this exception is raised when the system detect 2 or more " +
           "objects with the same id the database or a tree of components";
  }
  
  public String getErrorCode() {  return "UNIQUE_CONSTRAINT: " ; }
}