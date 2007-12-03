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

import java.util.Date;

/**
 * Created by The eXo Platform SAS
 * Author : Hoa  Pham
 *          hoapham@exoplatform.com,phamvuxuanhoa@yahoo.com
 * Oct 6, 2005
 */
public class PeriodInfo {
  private Date startTime_ ;
  private Date endTime_ ;
  private int repeatCount_ ;
  private long repeatInterval_ ;
  
  public PeriodInfo(Date startTime,Date endTime,int repeatCount,long repeatInterval) {
    startTime_ = startTime ;
    endTime_= endTime ;
    repeatCount_ = repeatCount ;
    repeatInterval_ = repeatInterval ;
  }
  
  public PeriodInfo(int repeatCount, long repeatInterval){
    repeatCount_ = repeatCount ;
    repeatInterval_ = repeatInterval;
  }
  
  public Date getStartTime() { return startTime_ ; }
  
  public Date getEndTime() { return endTime_; }
  
  public int getRepeatCount() {return repeatCount_; }
  
  public long getRepeatInterval() {return repeatInterval_; }
}
