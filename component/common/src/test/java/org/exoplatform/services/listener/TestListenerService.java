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
package org.exoplatform.services.listener;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.test.BasicTestCase;

/**
 * Created by The eXo Platform SAS
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
