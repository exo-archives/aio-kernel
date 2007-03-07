/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.scheduler;


/**
 * Created by The eXo Platform SARL
 * Author : Hoa  Pham
 *          hoapham@exoplatform.com,phamvuxuanhoa@yahoo.com
 * Oct 6, 2005
 */
public class JobInfo  {  
  private String jobName_ ;
  private String groupName_ ;
  private String description_ ;
  private Class job_ ;
  
  public JobInfo(Class job) {
    this(null, null, job) ;
  }
  
  public JobInfo(String jobName, String groupName,Class job) {
    job_ =  job ;
    if(jobName == null) jobName_ =  Integer.toString(this.hashCode()) ;
    else jobName_ =  jobName ;    
    groupName_ = groupName ;
  }
  
  
  public String getJobName() {  return jobName_ ; }
  public void setJobName(String name) { jobName_= name ; }
  
  public String getGroupName() {  return groupName_ ; }
  public void setGroupName(String name) { groupName_ = name ; }
  
  
  public String getDescription() { return description_ ; }
  public void setDescription(String s) { description_ =  s ; } 
  
  public Class getJob()  { return job_ ; }
  
}