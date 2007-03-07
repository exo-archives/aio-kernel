/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/

package org.exoplatform.services.scheduler.test;

import java.util.Comparator;

import org.exoplatform.container.component.BaseComponentPlugin;
import org.exoplatform.services.scheduler.BaseJob;
import org.exoplatform.services.scheduler.JobContext;
import org.exoplatform.services.scheduler.JobSchedulerService;
import org.exoplatform.test.BasicTestCase;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.quartz.Trigger;
import org.quartz.TriggerListener;


/**
 * Created by The eXo Platform SARL
 * Author : Hoa  Pham
 *          hoapham@exoplatform.com
 * Oct 5, 2005
 */
public class SchedulerServiceTestBase extends BasicTestCase {
  protected JobSchedulerService service_ ;      
  
  static  protected  void resetTestEnvironment() {
    AJob.reset() ;
    GlobalTriggerListener.countTriggerComplete_ = 0 ; 
  }
  
  static public class RunningJob extends BaseJob { 
    static long SLEEP_TIME = 1000 ;
    public void  execute(JobContext  context) throws Exception {
      Thread.sleep(SLEEP_TIME) ;
    }
  }
  
  static public class  GlobalTriggerListener 
  extends BaseComponentPlugin implements  TriggerListener {
    
    static int countTriggerFired_ = 0 ;
    static int countTriggerComplete_ = 0 ;
    
    public String getName() { return "GlobalTriggerListener";  }
    
    public void triggerFired(Trigger arg0, JobExecutionContext arg1) {
      countTriggerFired_++ ;
      System.out.println("trigger fired.......................") ;
    }
    
    public boolean vetoJobExecution(Trigger arg0, JobExecutionContext arg1) {
      return false;
    }
    
    public void triggerMisfired(Trigger arg0) {
      System.out.println("calll  TestJob.......................") ;
    }
    
    public void triggerComplete(Trigger arg0, JobExecutionContext arg1, int arg2) {
      countTriggerComplete_++ ;
      System.out.println("trigger complete.......................") ;      
    }  
  }
  
  static public class  FirstTriggerListener 
  extends BaseComponentPlugin implements  TriggerListener {
    
    static int countTriggerFired_ = 0 ;
    static int countTriggerComplete_ = 0 ;
    
    public String getName() { return "FirstTriggerListener";  }
    
    public void triggerFired(Trigger arg0, JobExecutionContext arg1) {
      countTriggerFired_++ ;
      System.out.println("trigger fired.......................") ;
    }
    
    public boolean vetoJobExecution(Trigger arg0, JobExecutionContext arg1) {
      return false;
    }
    
    public void triggerMisfired(Trigger arg0) {
      System.out.println("calll  TestJob.......................") ;
    }
    
    public void triggerComplete(Trigger arg0, JobExecutionContext arg1, int arg2) {
      countTriggerComplete_++ ;
      System.out.println("trigger complete.......................") ;      
    }  
  }

  static public class  SecondTriggerListener 
  extends BaseComponentPlugin implements  TriggerListener {
    
    static int countTriggerFired_ = 0 ;
    static int countTriggerComplete_ = 0 ;
    
    public String getName() { return "SecondTriggerListener";  }
    
    public void triggerFired(Trigger arg0, JobExecutionContext arg1) {
      countTriggerFired_++ ;
      System.out.println("trigger fired.......................") ;
    }
    
    public boolean vetoJobExecution(Trigger arg0, JobExecutionContext arg1) {
      return false;
    }
    
    public void triggerMisfired(Trigger arg0) {
      System.out.println("calll  TestJob.......................") ;
    }
    
    public void triggerComplete(Trigger arg0, JobExecutionContext arg1, int arg2) {
      countTriggerComplete_++ ;
      System.out.println("trigger complete.......................") ;      
    }  
  }

  static public class FirstJobListener extends BaseComponentPlugin implements JobListener {
    
    public String getName() { return "FirstJobListener";  }
    
    public void jobToBeExecuted(JobExecutionContext arg0) {            
    }
    
    public void jobExecutionVetoed(JobExecutionContext arg0) {            
    }
    
    public void jobWasExecuted(JobExecutionContext arg0, JobExecutionException arg1) {      
    }
    
  }
  static public class SecondJobListener extends BaseComponentPlugin implements JobListener {
    
    public String getName() { return "SecondJobListener";  }
    
    public void jobToBeExecuted(JobExecutionContext arg0) {            
    }
    
    public void jobExecutionVetoed(JobExecutionContext arg0) {            
    }
    
    public void jobWasExecuted(JobExecutionContext arg0, JobExecutionException arg1) {      
    }
    
  }
  
  static public class JobListenerComparator implements Comparator {
    
    public int compare(Object o1, Object o2) {
      JobListener j1 = (JobListener) o1 ;
      JobListener j2 = (JobListener) o2 ;
      if(j1.getName().equals(j2.getName()))  return 0; 
      return -1 ;
    }    
  }
  static public class TriggerListenerComparator implements Comparator {
    
    public int compare(Object o1, Object o2) {
      TriggerListener t1 = (TriggerListener) o1 ;
      TriggerListener t2 = (TriggerListener) o2 ;
      if(t1.getName().equals(t2.getName()))  return 0; 
      return -1 ;
    }    
  }
  
}




