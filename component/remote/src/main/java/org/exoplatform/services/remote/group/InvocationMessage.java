/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.remote.group;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Feb 21, 2005
 * @version $Id: InvocationMessage.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class InvocationMessage {
  private String method_ ;
  private Object[] args_ ;
  
  public InvocationMessage(String method, Object[] args) {
    method_ = method ;
    args_ = args ;
  }
  
  public String getMethod() { return method_ ; }
  public Object[] getArguments() { return args_; }
}
