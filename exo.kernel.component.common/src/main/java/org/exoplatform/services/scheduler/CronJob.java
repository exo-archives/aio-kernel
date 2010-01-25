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
package org.exoplatform.services.scheduler;

import org.exoplatform.commons.utils.ExoProperties;
import org.exoplatform.container.component.BaseComponentPlugin;
import org.exoplatform.container.xml.InitParams;
import org.quartz.JobDataMap;

/**
 * Created by The eXo Platform SAS Author : Hoa Pham
 * hoapham@exoplatform.com,phamvuxuanhoa@yahoo.com Oct 10, 2005
 */
public class CronJob extends BaseComponentPlugin {
  private String  expression_;

  private JobInfo jinfo_;
  
  private JobDataMap jdatamap_;

  public CronJob(InitParams params) throws Exception {
    ExoProperties props = params.getPropertiesParam("cronjob.info").getProperties();

    String jobName = props.getProperty("jobName");
    String jobGroup = props.getProperty("groupName");
    String jobClass = props.getProperty("job");
    Class clazz = Class.forName(jobClass);
    jinfo_ = new JobInfo(jobName, jobGroup, clazz);

    expression_ = props.getProperty("expression");
  }

  public JobInfo getJobInfo() {
    return jinfo_;
  }

  public String getExpression() {
    return expression_;
  }
  
  public JobDataMap getJobDataMap() {
    return jdatamap_;
  }

}
