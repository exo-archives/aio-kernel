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
public class Event<S, D> {
  
  protected String eventName ;
  protected S source; 
  protected D data ;
   
  /**
   * Construct an Event object  that  contain the event name ,  the object  that  broadcast the event
   * and the data  object that  the  source object is  working on
   * 
   * @param name    The name of the event
   * @param source  The object on which the Event initially occurred.
   * @param data    the object that the  source object is  working on
   */
  public Event(String name, S source, D data){
    this.eventName = name;
    this.source = source;
    this.data = data;
  }

  /**
   * @return The name of the event.  Any Listener want to be invoked on the event has to  have 
   *         the same name
   */
  public String getEventName(){ return eventName; }
  /**
   * 
   */
  public S getSource() { return source; }
  /**
   * @return  The data  object that the source object is working on
   */
  public D getData() { return data; }
}