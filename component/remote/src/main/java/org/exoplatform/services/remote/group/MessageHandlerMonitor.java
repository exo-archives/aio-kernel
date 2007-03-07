/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.remote.group;

import java.io.Serializable;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Mar 4, 2005
 * @version $Id: MessageHandlerMonitor.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class MessageHandlerMonitor implements Serializable {
  private String handlerId_ ;
  int counter_ = 0 ;
  int errorCounter_ = 0 ;
  private Throwable lastError_ ;
  
  public MessageHandlerMonitor(String handlerId) {
    handlerId_ = handlerId ;
  }
  
  public String getMessageHandlerId() { return handlerId_ ; }
  
  public void addMessageCounter(int i) { counter_ += i ; }
  public int getReceiceMessageCounter() { return counter_ ; }
  
  public String getLastErrorMessage() {
    if(lastError_ == null) return "" ;
    return lastError_.getMessage() ; 
  }
  public Throwable getLastError() { return lastError_ ; }
  public void setLastError(Throwable t) {
    errorCounter_++ ;
    lastError_ = t ;
  }
  
  public int getHandleErrorCounter() { return errorCounter_ ; }
}
