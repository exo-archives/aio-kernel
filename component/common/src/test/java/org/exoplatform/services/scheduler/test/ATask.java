/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.scheduler.test;

import java.util.Date;

import org.exoplatform.services.scheduler.BaseJob;
import org.exoplatform.services.scheduler.JobContext;
import org.exoplatform.services.scheduler.Task;

/**
 * Created by The eXo Platform SARL
 * Author : Hoa  Pham
 *          hoapham@exoplatform.com,phamvuxuanhoa@yahoo.com
 * Oct 7, 2005
 */
public class ATask extends Task {
  static  int counter_ = 0 ;  

  
  static void reset() {
    counter_ = 0 ;       
  }
  
  public void execute() throws Exception {
    counter_++ ;    
    System.out.println("========>Executing Task in Queue Task Job") ;
  }
}