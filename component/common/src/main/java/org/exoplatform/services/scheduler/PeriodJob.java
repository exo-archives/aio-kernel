/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.scheduler;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.exoplatform.commons.utils.ExoProperties;
import org.exoplatform.container.component.BaseComponentPlugin;
import org.exoplatform.container.xml.InitParams;

/**
 * Created by The eXo Platform SARL
 * Author : Tuan Nguyen
 *          tuan08@users.sourceforge.net
 * Oct 6, 2005
 */
public class PeriodJob extends  BaseComponentPlugin {
  private static SimpleDateFormat ft_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS") ;
  
  private PeriodInfo pjinfo_ ;
  private JobInfo jinfo_ ;
  
  public PeriodJob(InitParams params) throws Exception {
    ExoProperties props =  params.getPropertiesParam("job.info").getProperties() ;
    
    String jobName = props.getProperty("jobName") ;
    String jobGroup= props.getProperty("groupName") ;
    String jobClass = props.getProperty("job") ;
    Class clazz = Class.forName(jobClass) ;
    jinfo_ = new JobInfo(jobName,jobGroup,clazz);
    
    Date startTime = getDate(props.getProperty("startTime")) ;
    Date endTime = getDate(props.getProperty("endTime"));
    int repeatCount = Integer.parseInt(props.getProperty("repeatCount")) ;
    long repeatInterval = Integer.parseInt(props.getProperty("period")) ;
    pjinfo_ = new PeriodInfo(startTime,endTime,repeatCount,repeatInterval) ;
 }
  
  private Date  getDate(String stime) throws Exception {
    Date date = null ;
    if (stime  == null  || stime.equals("")) {
      return date ;
    } else if(stime.startsWith("+")) {
      long val = Long.parseLong(stime.substring(1)) ; 
      date = new Date(System.currentTimeMillis() + val) ;
    } else { 
      date = ft_.parse(stime) ;
    }
    return date ;
  }
  
  public JobInfo  getJobInfo() {  return jinfo_ ;  }
  
  public PeriodInfo  getPeriodInfo() { return pjinfo_ ;}
}