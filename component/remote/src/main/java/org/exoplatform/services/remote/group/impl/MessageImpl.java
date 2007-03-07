/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.remote.group.impl;

import org.exoplatform.services.remote.group.Message ;
import org.exoplatform.services.remote.group.MessageHandler;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Feb 21, 2005
 * @version $Id: MessageImpl.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class MessageImpl  implements Message {
  public String target_ ;
  public Object message_ ;
  
  public String getTargetHandler() { return target_ ; }
  public void   setTargetHandler(String targetHandler) { target_ = targetHandler ; }
  
  public Object getMessage() { return message_ ; }
  public void setMessage(Object obj) { message_ = obj ; }
}