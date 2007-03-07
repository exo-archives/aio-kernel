/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.commons.exception;

import java.util.ResourceBundle;
import org.exoplatform.commons.utils.Formater;

/*
 * @author: Tuan Nguyen
 * @version: $Id: ExoPermissionException.java,v 1.1 2004/09/21 00:04:44 tuan08 Exp $
 * @since: 0.0
 * @email: tuan08@yahoo.com
 */
public class ExoPermissionException extends ExoException {
  private String action_ ;
  private String requireRole_ ;
  
  public ExoPermissionException(String action , String requireRole) {
    action_ = action;
    requireRole_ = requireRole ;
  }
  
  public String getAction() { return action_ ; }
  
  public String getRequireRole() { return requireRole_ ; }
  
  public String getExceptionDescription() {
    return "Usually, this is not a critical exception. The exception is raised " +
           "when the remote user is not authenticate or he do not have the right " +
           "to access certain resource";
  }
  
  public String getMessage(ResourceBundle res) {
    Object[] args = {requireRole_ , action_ } ; 
    String s =  res.getString("ExoPermissionException.msg.message") ;
    return  Formater.getDefaultFormater().format(s , args) ; 
  }
  
  public String getErrorCode() {  return "EXO ERROR" ; }
}