/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.scheduler;

import org.quartz.JobExecutionContext;

/**
 * Created by The eXo Platform SARL
 * Author : Hoa  Pham
 *          hoapham@exoplatform.com
 * Oct 5, 2005
 */
public class JobContext {
  private JobExecutionContext jobContext_ ;
  
  public JobContext(JobExecutionContext context) {
    jobContext_ =  context ;
  }
  
  public  void put(Object key, Object value) {
    jobContext_.put(key, value) ;
  }
}
