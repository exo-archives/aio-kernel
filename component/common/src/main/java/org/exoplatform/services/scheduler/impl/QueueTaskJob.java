/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.scheduler.impl;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.scheduler.BaseJob;
import org.exoplatform.services.scheduler.JobContext;
import org.exoplatform.services.scheduler.QueueTasks;
import org.exoplatform.services.scheduler.Task;


/**
 * Created by The eXo Platform SARL
 * Author : Hoa  Pham
 *          hoapham@exoplatform.com,phamvuxuanhoa@yahoo.com
 * Oct 7, 2005
 * 
 * @version $Id$
 */
public class QueueTaskJob extends BaseJob {  
  public void  execute(JobContext  context) throws Exception {      
    PortalContainer manager = PortalContainer.getInstance() ;
    QueueTasks qtasks = 
      (QueueTasks) manager.getComponentInstanceOfType(QueueTasks.class) ;    
    Task task =  qtasks.poll() ;
    while(task != null) {
      try {
        task.execute() ;
      } catch(Exception ex) {
        ex.printStackTrace() ;
      }
      task = qtasks.poll() ;
    }
  }
}