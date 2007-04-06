/***************************************************************************
 * Copyright 2001-2007 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.listener;

import org.exoplatform.container.component.BaseComponentPlugin;

/**
 * Created by The eXo Platform SARL
 * Author : Nhu Dinh Thuan
 *          nhudinhthuan@exoplatform.com
 * Apr 6, 2007  
 */
public class SetValueBeanListener extends BaseComponentPlugin implements Listener<TestListenerService.Bean> {
  
  public SetValueBeanListener( ){
    name = "set.value.bean";
  }

  public void handle(Event<TestListenerService.Bean> event) {
    TestListenerService.Bean bean  = event.getData(); 
    System.out.println("\n set value "+bean.getValue()+"\n\n");
  }

}
