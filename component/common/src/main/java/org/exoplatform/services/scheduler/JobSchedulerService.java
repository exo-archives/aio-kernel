/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.scheduler;

import java.util.Date;
import java.util.List;

import org.exoplatform.container.component.ComponentPlugin;
import org.quartz.JobDataMap;
import org.quartz.JobListener;
import org.quartz.Trigger;
import org.quartz.TriggerListener;
/**
 * Created by The eXo Platform SARL
 * Author : Hoa  Pham
 *          hoapham@exoplatform.com
 * Oct 5, 2005
 */
public interface JobSchedulerService {  
  public void addJob(JobInfo jinfo, Date date) throws Exception ;
  public void addPeriodJob(JobInfo jinfo,PeriodInfo pinfo) throws Exception ;
  public void addCronJob(JobInfo jinfo, String exp) throws Exception ;
  public boolean removeJob(JobInfo jinfo) throws Exception ;
 
  public void addPeriodJob(JobInfo jinfo, PeriodInfo pinfo, JobDataMap jdatamap) throws Exception ;
  public void addCronJob(JobInfo jinfo, String exp, JobDataMap jdatamap) throws Exception ;
  
  public void executeJob(String jname, String jgroup, JobDataMap jdatamap)  throws Exception ;
  
  public void addGlobalJobListener(ComponentPlugin plugin) throws Exception  ;
  public List getAllGlobalJobListener() throws Exception ;
  public JobListener getGlobalJobListener(String name) throws Exception ;
  public boolean removeGlobalJobListener(String name) throws Exception ;
  
  public void addJobListener(ComponentPlugin plugin) throws Exception ;
  public List getAllJobListener() throws Exception ;
  public JobListener getJobListener(String name) throws Exception  ;
  public boolean removeJobListener(String name) throws Exception ;
  
  public void addGlobalTriggerListener(ComponentPlugin plugin) throws Exception ;
  public List getAllGlobalTriggerListener() throws Exception ;
  public TriggerListener getGlobalTriggerListener(String name) throws Exception;
  public boolean removeGlobaTriggerListener(String name) throws Exception ;
  public int getTriggerState(String triggerName,String triggerGroup) throws Exception ;
  
  public void addTriggerListener(ComponentPlugin plugin) throws Exception ;
  public List getAllTriggerListener() throws Exception ;
  public TriggerListener getTriggerListener(String name) throws Exception ;
  public boolean removeTriggerListener(String name) throws Exception ;
  
  
  public void queueTask(Task task) ;
  
  public List getAllExcutingJobs() throws Exception;
  public List getAllJobs() throws Exception ;
  
  public void pauseJob(String jobName, String groupName) throws Exception;
  public void resumeJob(String jobName, String groupName) throws Exception;
  public Trigger[] getTriggersOfJob(String jobName, String groupName) throws Exception;
  public Date rescheduleJob(String triggerName, String groupName, Trigger newTrigger) throws Exception;
}