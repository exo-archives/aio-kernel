/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.scheduler.test;

import java.util.Date;

import org.exoplatform.services.scheduler.BaseJob;
import org.exoplatform.services.scheduler.JobContext;

/**
 * Created by The eXo Platform SARL
 * Author : Hoa  Pham
 *          hoapham@exoplatform.com,phamvuxuanhoa@yahoo.com
 * Oct 7, 2005
 */
public class TestJob extends BaseJob {
  static  int counter_ = 0 ;
  static Date  expectExecuteTime_ ;
  static int   errorCounter_ = 0 ;
  static int repeatCounter_=0 ;
  
  static void reset() {
    counter_ = 0 ;
    expectExecuteTime_  = null ;
    errorCounter_ = 0 ;
    repeatCounter_ = 0 ;
  }
  
  public void  execute(JobContext  context) throws Exception {
    counter_++ ;
    repeatCounter_++;
    if(expectExecuteTime_ != null) {
      if(expectExecuteTime_.getTime() + 500 < System.currentTimeMillis() ) {
        errorCounter_++ ;
      }
    }
    System.out.println("calll  TestTask.......................") ;
  }
}