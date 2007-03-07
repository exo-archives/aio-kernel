/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.scheduler;

import org.exoplatform.commons.utils.ExoProperties;
import org.exoplatform.container.component.BaseComponentPlugin;
import org.exoplatform.container.xml.InitParams;

/**
 * Created by The eXo Platform SARL
 * Author : Hoa  Pham
 *          hoapham@exoplatform.com,phamvuxuanhoa@yahoo.com
 * Oct 10, 2005
 */
public class CronJob extends BaseComponentPlugin {
  private String expression_ ;
  private JobInfo jinfo_ ;
  
  public CronJob(InitParams params)  throws Exception {
    ExoProperties props =  params.getPropertiesParam("cronjob.info").getProperties() ;
    
    String jobName = props.getProperty("jobName") ;
    String jobGroup= props.getProperty("groupName") ;
    String jobClass = props.getProperty("job") ;
    Class clazz = Class.forName(jobClass) ;
    jinfo_ = new JobInfo(jobName,jobGroup,clazz);
    
    expression_=props.getProperty("expression") ;
  }
  
  public JobInfo getJobInfo() { return jinfo_ ; }
  public String getExpression() { return expression_ ; }
}
