/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.remote.group;

import org.jgroups.util.Rsp;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Mar 4, 2005
 * @version $Id: Response.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class Response {
  private Rsp rsp_ ;
  
  public void populate(Rsp rsp) {
    rsp_ = rsp ;
  }
  
  public Object getReturnValue() { return rsp_.getValue(); }
  
}
