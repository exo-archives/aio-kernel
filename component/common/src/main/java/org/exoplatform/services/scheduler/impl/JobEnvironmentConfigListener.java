/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.scheduler.impl;

import java.util.List;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.container.RootContainer;
import org.exoplatform.container.component.ComponentPlugin;
import org.exoplatform.container.component.ComponentRequestLifecycle;
import org.exoplatform.container.xml.PortalContainerInfo;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;

/**
 * Created by The eXo Platform SARL
 * Author : Hoa  Pham
 *          hoapham@exoplatform.com
 * Oct 5, 2005
 */
public class JobEnvironmentConfigListener implements JobListener, ComponentPlugin  {
  private String containerName_ ;
  
  public JobEnvironmentConfigListener(PortalContainerInfo pinfo) {
    containerName_ =  pinfo.getContainerName() ;
  }

  public void jobToBeExecuted(JobExecutionContext context) {    
    RootContainer rootContainer = RootContainer.getInstance() ;
    PortalContainer pcontainer = 
      (PortalContainer) rootContainer.getComponentInstance(containerName_) ;
    PortalContainer.setInstance(pcontainer) ;
    List<ComponentRequestLifecycle> components = 
      pcontainer.getComponentInstancesOfType(ComponentRequestLifecycle.class) ; 
    for(ComponentRequestLifecycle component : components) {
      component.startRequest(pcontainer) ;
    }
  } 

  public void jobExecutionVetoed(JobExecutionContext context) {
    
  }

  public void jobWasExecuted(JobExecutionContext context, 
                             JobExecutionException exception) {
    RootContainer rootContainer = RootContainer.getInstance() ;
    PortalContainer pcontainer = (PortalContainer) rootContainer.getComponentInstance(containerName_);
    List<ComponentRequestLifecycle> components = 
      pcontainer.getComponentInstancesOfType(ComponentRequestLifecycle.class) ; 
    for(ComponentRequestLifecycle component : components) {
      component.endRequest(pcontainer) ;
    }
    PortalContainer.setInstance(null) ;
  }

  public String getName() {  return "JobContextConfigListener";  }
  public void setName(String s) { }

  public String getDescription() {  return null; }
  public void setDescription(String s) {  }

}
