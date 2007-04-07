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
public class Event<E, V> {
  
  protected String eventName ;
  
  protected E source; 
  
  protected V data ;
   
  public Event(String name, E source, V data){
    this.eventName = name;
    this.source = source;
    this.data = data;
  }

  public String getEventName(){ return eventName; }
  
  public V getData() { return data; }
  
  public E getSource() { return source; }

  
}
