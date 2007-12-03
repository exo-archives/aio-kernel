/***************************************************************************
 * Copyright 2001-2007 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.listener;

import org.exoplatform.container.component.BaseComponentPlugin;

/**
 * Created by The eXo Platform SAS
 * Author : Nhu Dinh Thuan
 *          nhudinhthuan@exoplatform.com
 * Apr 6, 2007
 * 
 * This class is registered with the Listener service and  is invoked  when an  event with the same name
 * is broadcasted. You can have many listeners with the same name to listen to an event.
 */
public abstract class Listener<S, D> extends BaseComponentPlugin {
  //TODO: Should have the event name here to avoid  the conflict  with  the plugin name
  
  /**
   * This method should be invoked when an event with the same name is broadcasted
   */
  public abstract void onEvent(Event<S, D> event) throws Exception ;
  
}
