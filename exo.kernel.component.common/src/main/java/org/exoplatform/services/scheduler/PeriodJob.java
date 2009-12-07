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

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobDataMap;

import org.exoplatform.commons.utils.ExoProperties;
import org.exoplatform.container.component.BaseComponentPlugin;
import org.exoplatform.container.xml.InitParams;

/**
 * Created by The eXo Platform SAS Author : Tuan Nguyen
 * tuan08@users.sourceforge.net Oct 6, 2005
 */
public class PeriodJob extends BaseComponentPlugin {
  private static SimpleDateFormat ft_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

  private PeriodInfo              pjinfo_;

  private JobInfo                 jinfo_;

  private JobDataMap              jdatamap_;

  public PeriodJob(InitParams params) throws Exception {
    ExoProperties props = params.getPropertiesParam("job.info").getProperties();

    String jobName = props.getProperty("jobName");
    String jobGroup = props.getProperty("groupName");
    String jobClass = props.getProperty("job");
    Class clazz = Class.forName(jobClass);
    jinfo_ = new JobInfo(jobName, jobGroup, clazz);

    Date startTime = getDate(props.getProperty("startTime"));
    Date endTime = getDate(props.getProperty("endTime"));
    int repeatCount = Integer.parseInt(props.getProperty("repeatCount"));
    long repeatInterval = Integer.parseInt(props.getProperty("period"));
    pjinfo_ = new PeriodInfo(startTime, endTime, repeatCount, repeatInterval);
  }

  private Date getDate(String stime) throws Exception {
    Date date = null;
    if (stime == null || stime.equals("")) {
      return date;
    } else if (stime.startsWith("+")) {
      long val = Long.parseLong(stime.substring(1));
      date = new Date(System.currentTimeMillis() + val);
    } else {
      date = ft_.parse(stime);
    }
    return date;
  }

  public JobInfo getJobInfo() {
    return jinfo_;
  }

  public PeriodInfo getPeriodInfo() {
    return pjinfo_;
  }

  public JobDataMap getJobDataMap() {
    return jdatamap_;
  }
}
