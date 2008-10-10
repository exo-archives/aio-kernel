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

import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.scheduler.BaseJob;
import org.exoplatform.services.scheduler.JobContext;
import org.exoplatform.services.scheduler.QueueTasks;
import org.exoplatform.services.scheduler.Task;

/**
 * Created by The eXo Platform SAS Author : Hoa Pham
 * hoapham@exoplatform.com,phamvuxuanhoa@yahoo.com Oct 7, 2005
 * 
 * @version $Id: QueueTaskJob.java 20167 2008-09-19 15:00:53Z tolusha $
 */
public class QueueTaskJob extends BaseJob {
  public void execute(JobContext context) throws Exception {
    PortalContainer manager = PortalContainer.getInstance();
    QueueTasks qtasks = (QueueTasks) manager.getComponentInstanceOfType(QueueTasks.class);
    Task task = qtasks.poll();
    while (task != null) {
      try {
        task.execute();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
      task = qtasks.poll();
    }
  }
}
