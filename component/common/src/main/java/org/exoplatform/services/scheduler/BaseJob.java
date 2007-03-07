/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.scheduler;

import org.quartz.Job ;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * Created by The eXo Platform SARL
 * Author : Hoa  Pham
 *          hoapham@exoplatform.com
 * Oct 5, 2005
 */
abstract public class BaseJob implements  Job {

  public void execute(JobExecutionContext context) throws JobExecutionException {
     JobContext tcontext  = new JobContext(context) ;
     try {
       execute(tcontext) ;
     } catch (Exception ex) {
       throw new JobExecutionException(ex) ;
     }
  }

  public void  execute(JobContext  context) throws Exception {
    throw new Exception("You  should overide this method") ;
  }
}
