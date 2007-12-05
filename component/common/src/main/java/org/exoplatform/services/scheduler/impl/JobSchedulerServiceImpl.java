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
package org.exoplatform.services.scheduler.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

import org.exoplatform.container.StandaloneContainer;
import org.exoplatform.container.component.ComponentPlugin;
import org.exoplatform.container.xml.PortalContainerInfo;
import org.exoplatform.services.scheduler.CronJob;
import org.exoplatform.services.scheduler.JobInfo;
import org.exoplatform.services.scheduler.JobSchedulerService;
import org.exoplatform.services.scheduler.PeriodInfo;
import org.exoplatform.services.scheduler.PeriodJob;
import org.exoplatform.services.scheduler.QueueTasks;
import org.exoplatform.services.scheduler.Task;
import org.picocontainer.Startable;
import org.quartz.CronTrigger;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobListener;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerListener;

/**
 * Created by The eXo Platform SAS
 * Author : Hoa  Pham
 *          hoapham@exoplatform.com
 * Oct 5, 2005
 * 
 * @version $Id$
 */
public class JobSchedulerServiceImpl implements JobSchedulerService, Startable {
	private final Scheduler scheduler_ ;
  private final String containerName_ ;
  private final QueueTasks qtasks_ ;
	
	public JobSchedulerServiceImpl(PortalContainerInfo pinfo, 
                                 QuartzSheduler quartzSchduler,
                                 QueueTasks qtasks)  {
		scheduler_ =  quartzSchduler.getQuartzSheduler() ;
    containerName_ = pinfo.getContainerName() ;
    qtasks_ =  qtasks ;
	}
	
	/**
	 * For run in Standalone container
	 * 
	 * @param quartzSchduler
	 * @param qtasks
	 */
	public JobSchedulerServiceImpl(QuartzSheduler quartzSchduler, QueueTasks qtasks) {
    scheduler_ = quartzSchduler.getQuartzSheduler();
    containerName_ = "Standalone";
    qtasks_ = qtasks;
  }
	
	public void queueTask(Task task) {
	  qtasks_.add(task) ;
	}
	
	public void addJob(JobDetail job, Trigger trigger) throws Exception {
		scheduler_.scheduleJob(job,trigger);
	}
	
	public void addJob(JobInfo jinfo, Trigger trigger) throws Exception {        
    JobInfo jobinfo = getJobInfo(jinfo) ;            
		JobDetail job=new JobDetail(jobinfo.getJobName(), jobinfo.getGroupName(),jobinfo.getJob());    
		scheduler_.scheduleJob(job,trigger);    
	}
	
	public void addJob(JobInfo jinfo, Date date) throws Exception {        
    JobInfo jobinfo = getJobInfo(jinfo) ;            
		SimpleTrigger trigger = new SimpleTrigger(jobinfo.getJobName(), jobinfo.getGroupName(),date);    
		JobDetail job=new JobDetail(jobinfo.getJobName(), jobinfo.getGroupName(),jobinfo.getJob());    
		job.setDescription(jinfo.getDescription());
		scheduler_.scheduleJob(job,trigger);
	}
	
	public void addPeriodJob(JobInfo jinfo, int repeatCount, long period) throws Exception {    
		int repeat;
		if(repeatCount<0) repeat=SimpleTrigger.REPEAT_INDEFINITELY ;
		else if (repeatCount==0) repeat=SimpleTrigger.REPEAT_INDEFINITELY ;
		else repeat=repeatCount-1;    
    JobInfo jobinfo = getJobInfo(jinfo) ;    
		SimpleTrigger trigger = new SimpleTrigger(jobinfo.getJobName(), 
				jobinfo.getGroupName(),repeat,period);
		JobDetail job=new JobDetail(jobinfo.getJobName(), jobinfo.getGroupName(),jobinfo.getJob());    
		job.setDescription(jobinfo.getDescription());    
		scheduler_.scheduleJob(job,trigger);        
	}
	
	public void addPeriodJob(JobInfo jinfo,PeriodInfo pinfo) throws Exception {    
		int repeat = pinfo.getRepeatCount();    
		Date start = pinfo.getStartTime();
    JobInfo jobinfo = getJobInfo(jinfo) ;
		if(start == null) start = new Date();
		if(repeat<=0) repeat=SimpleTrigger.REPEAT_INDEFINITELY ;
		else repeat=repeat-1;
		SimpleTrigger trigger = new SimpleTrigger(jobinfo.getJobName(), 
				jobinfo.getGroupName(),
				start,pinfo.getEndTime(),
				repeat,pinfo.getRepeatInterval());
		JobDetail job=new JobDetail(jobinfo.getJobName(), jobinfo.getGroupName(),jobinfo.getJob());
		job.setDescription(jobinfo.getDescription());    
		scheduler_.scheduleJob(job,trigger);
	}
	
	public void addPeriodJob(ComponentPlugin plugin)   throws Exception {
		PeriodJob pjob = (PeriodJob) plugin ;    
		addPeriodJob(pjob.getJobInfo(), pjob.getPeriodInfo(), pjob.getJobDataMap()) ;
	}
	
	public void addCronJob(JobInfo jinfo, String exp) throws Exception {
    JobInfo jobinfo = getJobInfo(jinfo) ;
		CronTrigger trigger= new CronTrigger(jobinfo.getJobName(), jobinfo.getGroupName(),
				jobinfo.getJobName(), jobinfo.getGroupName(),exp) ;
		JobDetail job=new JobDetail(jobinfo.getJobName(), jobinfo.getGroupName(),jobinfo.getJob());
		job.setDescription(jobinfo.getDescription());    
    scheduler_.addJob(job,true) ;
		scheduler_.scheduleJob(trigger) ;
	}
	
	public void addCronJob(ComponentPlugin plugin) throws Exception {
		CronJob cjob = (CronJob) plugin ;
		addCronJob(cjob.getJobInfo(),cjob.getExpression()) ;
	}
  
	public void addCronJob(JobInfo jinfo, String exp, JobDataMap jdatamap) throws Exception {
    JobInfo jobinfo = getJobInfo(jinfo) ;
    CronTrigger trigger= new CronTrigger(jobinfo.getJobName(), jobinfo.getGroupName(),
        jobinfo.getJobName(), jobinfo.getGroupName(),exp) ;
    JobDetail job=new JobDetail(jobinfo.getJobName(), jobinfo.getGroupName(),jobinfo.getJob());
    job.setDescription(jobinfo.getDescription());
    scheduler_.addJob(job,true) ;
    scheduler_.scheduleJob(trigger) ;        
	}

	public void addPeriodJob(JobInfo jinfo, PeriodInfo pinfo, JobDataMap jdatamap) throws Exception {
    int repeat = pinfo.getRepeatCount();    
    Date start = pinfo.getStartTime();
    JobInfo jobinfo = getJobInfo(jinfo) ;
    if(start == null) start = new Date();
    if(repeat<=0) repeat=SimpleTrigger.REPEAT_INDEFINITELY ;
    else repeat=repeat-1;
    SimpleTrigger trigger = new SimpleTrigger(jobinfo.getJobName(), 
        jobinfo.getGroupName(),
        start,pinfo.getEndTime(),
        repeat,pinfo.getRepeatInterval());
    JobDetail job=new JobDetail(jobinfo.getJobName(), jobinfo.getGroupName(),jobinfo.getJob());
    job.setJobDataMap(jdatamap) ;
    job.setDescription(jobinfo.getDescription());    
    scheduler_.scheduleJob(job,trigger);
	}

	
	public boolean removeJob(JobInfo jinfo) throws Exception {
    JobInfo jobinfo = getJobInfo(jinfo) ;
		boolean b = scheduler_.deleteJob(jobinfo.getJobName(), jobinfo.getGroupName()) ;
		return b;
	}
	
	public List getAllExcutingJobs() throws Exception {		
		return  scheduler_.getCurrentlyExecutingJobs() ;		
	}
	
	public List getAllJobs() throws Exception {
		List jlist = new ArrayList();
		String jgroup [] = scheduler_.getJobGroupNames() ;
		for(int i=1 ; i<jgroup.length; i++) {
			String jname [] = scheduler_.getJobNames(jgroup[i]) ;
			for(int j = 0; j<jname.length; j++){
				jlist.add(scheduler_.getJobDetail(jname[j],jgroup[i]));
			}
		}
		return jlist;
	}
	
	public void addGlobalJobListener(ComponentPlugin plugin) throws Exception {
		scheduler_.addGlobalJobListener((JobListener) plugin) ;
	}
	
	public List getAllGlobalJobListener() throws Exception {
		List globalListeners ;
		globalListeners = scheduler_.getGlobalJobListeners();
		return globalListeners;
	}
	
	public JobListener getGlobalJobListener(String name) throws Exception {
		List listener ;  
		JobListener jlistener ;
		listener =  scheduler_.getGlobalJobListeners() ;
		ListIterator iterator = listener.listIterator() ;
		while(iterator.hasNext()) {
			jlistener = (JobListener)iterator.next() ;
			if(jlistener.getName().equals(name)) {return jlistener ; }
		}
		return null ;
	}
	
	public boolean removeGlobalJobListener(String name) throws Exception {
		JobListener jlistener = getGlobalJobListener(name);
		boolean b = scheduler_.removeGlobalJobListener(jlistener) ;
		return b;
	}
	
  public void addJobListener(ComponentPlugin plugin) throws Exception {
    scheduler_.addJobListener((JobListener)plugin) ;
  }

  public List getAllJobListener() throws Exception {
    Set jlNames = scheduler_.getJobListenerNames() ;
    List jlisteners = new ArrayList() ;
    if(jlNames.isEmpty()) return jlisteners;            
    for(Object obj:jlNames.toArray()) {
      jlisteners.add(getJobListener(obj.toString())) ;
    }
    return jlisteners ;
  }

  public JobListener getJobListener(String name) throws Exception {    
    return scheduler_.getJobListener(name);
  }

  public boolean removeJobListener(String name) throws Exception {    
    boolean b = scheduler_.removeJobListener(name) ;
    return b;
  }  
  
	public void addGlobalTriggerListener(ComponentPlugin plugin)   throws Exception {
		scheduler_.addGlobalTriggerListener((TriggerListener) plugin) ;
	}
	
	public List getAllGlobalTriggerListener() throws Exception {
		List listener ;
		listener = scheduler_.getGlobalTriggerListeners() ;
		return listener;
	}
	
	public TriggerListener getGlobalTriggerListener(String name) throws Exception {
		TriggerListener tlistener ;
		List listener ;
		listener = scheduler_.getGlobalTriggerListeners() ;
		ListIterator iterator = listener.listIterator() ;
		while(iterator.hasNext()) {
			tlistener = (TriggerListener)iterator.next() ;
			if(tlistener.getName().equals(name)) { return tlistener ; }
		}
		return null;
	}
	
	public boolean removeGlobaTriggerListener(String name) throws Exception {
		TriggerListener tlistener = getGlobalTriggerListener(name) ;
		boolean b = scheduler_.removeGlobalTriggerListener(tlistener) ;
		return b;
	}
    	
	public void addTriggerListener(ComponentPlugin plugin) throws Exception {
    scheduler_.addTriggerListener((TriggerListener)plugin) ;
	}
	
   public List getAllTriggerListener() throws Exception {
     List tlisteners = new ArrayList() ;
     Set tlistenerNames = scheduler_.getTriggerListenerNames() ;
     if (tlistenerNames.isEmpty()) return tlisteners;          
     for(Object obj: tlistenerNames.toArray()) {
       tlisteners.add(getTriggerListener(obj.toString())) ;
     }
     return tlisteners ;
    }

    public TriggerListener getTriggerListener(String name) throws Exception {
      TriggerListener tlistener = scheduler_.getTriggerListener(name) ;
      return tlistener;
    }

    public boolean removeTriggerListener(String name) throws Exception {
      boolean b = scheduler_.removeTriggerListener(name) ;
      return b;
    }
  
  private JobInfo getJobInfo(JobInfo jinfo) throws Exception {
    String gname ; 
    if(jinfo.getGroupName()==null)  gname = containerName_; 
    else gname = containerName_+ ":" + jinfo.getGroupName() ;     
    JobInfo jobInfo = new JobInfo(jinfo.getJobName(),gname,jinfo.getJob()) ;
    jobInfo.setDescription(jinfo.getDescription()) ;    
    return  jobInfo;
  }
  
  public void pauseJob(String jobName, String groupName) throws Exception {
  	scheduler_.pauseJob(jobName, groupName);    
  }
  
  public void resumeJob(String jobName, String groupName) throws Exception {
  	scheduler_.resumeJob(jobName, groupName);    
  }
  
  public void executeJob(String jname, String jgroup,JobDataMap jdatamap) throws Exception {
    scheduler_.triggerJobWithVolatileTrigger(jname,jgroup,jdatamap) ;    
  }
  
  public Trigger[] getTriggersOfJob(String jobName, String groupName) throws Exception {
  	return scheduler_.getTriggersOfJob(jobName, groupName);
  }
  
  public int getTriggerState(String triggerName, String triggerGroup) throws Exception {
    return scheduler_.getTriggerState(triggerName,triggerGroup) ;    
  }
  
  public Date rescheduleJob(String triggerName, String groupName, Trigger newTrigger) throws SchedulerException {
  	return scheduler_.rescheduleJob(triggerName, groupName, newTrigger);    
  }
  
	public void start() { }
	
	public void stop() {  
    try {
      scheduler_.shutdown(true) ;
    } catch (Exception ex) {
      ex.printStackTrace() ;
    }
  }
    
}
