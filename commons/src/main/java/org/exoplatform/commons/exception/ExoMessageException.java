/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.commons.exception;

import java.util.ResourceBundle ;
import org.exoplatform.commons.utils.Formater;
/*
 * @author: Tuan Nguyen
 * @version: $Id: ExoMessageException.java,v 1.2 2004/11/03 01:24:55 tuan08 Exp $
 * @since: 0.0
 * @email: tuan08@yahoo.com
 */
public class ExoMessageException extends ExoException {
  private static Formater ft_ = Formater.getDefaultFormater() ;
  
  private String messageKey_ ;
  private Object[] args_ ;
  
  public ExoMessageException(String messageKey) {
    messageKey_ = messageKey ;
  }
  
  public ExoMessageException(String messageKey, Object[] args) {
    messageKey_ = messageKey ;
    args_ = args ;
  }
  
  public String getMessageKey() { return messageKey_ ; }
  
  public Object[] getArguments() { return args_ ; }
  
  public String getMessage(ResourceBundle res) {
    if(args_ == null) {
      return res.getString(messageKey_) ;
    } 
    return ft_.format(res.getString(messageKey_) , args_) ; 
  }
  
  public String getExceptionDescription() {
    return "Usually, this is not a critical exception. The exception is raised " +
           "when unexpected condition such wrong input, object not found...." +
           "The application should not crashed and it should continue working";
  }
  
  public String getErrorCode() {  return "EXO ERROR: " ; }
}