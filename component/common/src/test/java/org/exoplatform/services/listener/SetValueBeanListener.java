/***************************************************************************
 * Copyright 2001-2007 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.listener;


/**
 * Created by The eXo Platform SARL
 * Author : Nhu Dinh Thuan
 *          nhudinhthuan@exoplatform.com
 * Apr 6, 2007  
 */
public class SetValueBeanListener extends Listener<TestListenerService.BeanHandler> {
  
  public SetValueBeanListener( ){
    name = "set.value.bean";
  }

  public void onEvent(Event<TestListenerService.BeanHandler, ?> event) {
    TestListenerService.Bean bean  = (TestListenerService.Bean)event.getData(); 
    System.out.println("\n set value "+bean.getValue()+"\n\n");
  }

}
