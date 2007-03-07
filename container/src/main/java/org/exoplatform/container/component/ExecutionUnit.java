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
abstract public class ExecutionUnit extends BaseComponentPlugin {
  private  ExecutionUnit next_ ;
  
  public  void  addExecutionUnit(ExecutionUnit next) {
    if(next_  == null) next_ = next ;
    else  next_.addExecutionUnit(next) ;
  }
  
  public ExecutionUnit getNextUnit()  { return next_ ; }
  
  abstract public Object execute(ExecutionContext context) throws Throwable ;
}
