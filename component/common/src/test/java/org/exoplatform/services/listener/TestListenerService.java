/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.listener;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.test.BasicTestCase;

/**
 * Created by The eXo Platform SARL
 * Author : Chung Nguyen
 *          nguyenchung136@yahoo.com
 * Feb 13, 2006
 */
public class TestListenerService extends BasicTestCase {
  
  private ListenerService service_;
  
  public TestListenerService(String name){
    super(name);
  }
  
  public void setUp() throws Exception {
    setTestNumber(1) ;
    PortalContainer manager  = PortalContainer.getInstance();
    service_ = (ListenerService) manager.getComponentInstanceOfType(ListenerService.class) ;    
  }
  
  public void testListener() throws Exception {
    assertTrue(service_ != null);
    
    BeanHandler handler = new BeanHandler();
    handler.setValue("thuan");    
  }
  
  public class BeanHandler {
    
    private Bean bean;
    
    public BeanHandler() throws Exception {
      bean = new Bean("test", "listener1");
      service_.broadcast(new Event<BeanHandler, Bean>("new.bean", this, bean));
    }
    
    public void setValue(String value) throws Exception {
      bean.value = value;
      service_.broadcast(new Event<BeanHandler, Bean>("set.value.bean", this, bean));
    }
    
  }
  
  static class Bean {
    
    @SuppressWarnings("unused")
    private String name;
    
    private String value;
    
    Bean(String n, String v) {
      name = n;
      value = v;
    }
    
    String getValue() { return value; }
    
    String getName() { return name; }
  }
  
}
