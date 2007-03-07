/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.component;
/**
 * Created by The eXo Platform SARL
 * Author : Tuan Nguyen
 *          tuan08@users.sourceforge.net
 * Sep 17, 2005
 */
public class ExecutionContext {
  private ExecutionUnit currentUnit_ ;
  
  public void setCurrentExecutionUnit(ExecutionUnit unit) {
    currentUnit_ = unit ;
  }
  
  public Object execute() throws Throwable {
    if(currentUnit_ != null)  return currentUnit_.execute(this) ;
    return null ;
  } 
  
  public Object executeNextUnit() throws Throwable {
    currentUnit_ =  currentUnit_.getNextUnit() ;
    if(currentUnit_ != null)  return currentUnit_.execute(this) ;
    return null ;
  } 
}
