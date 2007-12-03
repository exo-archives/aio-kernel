/*
 * Copyright (C) 2003-2007 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
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
