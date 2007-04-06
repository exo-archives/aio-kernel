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
public abstract class Event<T> {
  
  private T source;
  
  protected T value ;
  
  public T getBean() { return value; }
  
  public void setBean(T t) { value = t; }

  public T getSource() { return source; }

  public void setSource(T source) { this.source = source; }
  
}
