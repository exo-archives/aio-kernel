/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.scheduler.impl;

import org.picocontainer.Startable;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by The eXo Platform SARL
 * Author : Tuan Nguyen
 *          tuan08@users.sourceforge.net
 * Dec 13, 2005
 * 
 * @version $Id$
 */
public class QuartzSheduler implements Startable {
  private Scheduler scheduler_ ;
  
  public QuartzSheduler() throws Exception {
    SchedulerFactory sf = new StdSchedulerFactory();
    scheduler_ = sf.getScheduler();
    scheduler_.start();
  }
  
  public Scheduler getQuartzSheduler () {  return scheduler_ ; }

  public void start() {  }

  public void stop() {
    try {
      scheduler_.shutdown();
    } catch (Exception ex) {
      ex.printStackTrace() ;
    }
  }
}
