/***************************************************************************
 * Copyright 2001-2007 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by The eXo Platform SARL
 * Author : Nhu Dinh Thuan
 *          nhudinhthuan@exoplatform.com
 * Apr 6, 2007  
 */
public class ListenerService {
  
  private Map<String, List<Listener>> map;
  
  public ListenerService() {
    map = new HashMap<String, List<Listener>>();
  }
  
  public void addListener(Listener listener) {
    String name = listener.getName();
    List<Listener> list = map.get(name);
    if(list == null) { 
      list = new ArrayList<Listener>();
      map.put(name, list);
    }
    list.add(listener);
  }
  
  public void addListener(String name, Listener listener) {
    listener.setName(name);
    addListener(listener);
  }
  
  final public <E, V> void broadcast(String name, E source, V data) throws Exception {
    broadcast(new Event<E, V>(name, source, data)) ;
  }
  
  @SuppressWarnings("unchecked")
  final public <T extends Event> void broadcast(T event) throws Exception {
    List<Listener> list = map.get(event.getEventName());
    if(list == null)  return;
    for(Listener listener : list) listener.onEvent(event);    
  }
 
  
  
}
