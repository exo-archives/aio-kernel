/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.remote.group;

import java.io.Serializable;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Feb 21, 2005
 * @version $Id: Message.java 5799 2006-05-28 17:55:42Z geaz $
 */
public interface Message  extends Serializable {
  public String getTargetHandler() ;
  public void   setTargetHandler(String handlerId) ;
  
  public Object getMessage() ;
  public void   setMessage(Object obj) ;
}