/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.scheduler;

import java.util.Date;

/**
 * Created by The eXo Platform SARL
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
