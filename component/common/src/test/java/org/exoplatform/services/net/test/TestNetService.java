/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.net.test;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.net.NetService;
import org.exoplatform.test.BasicTestCase;

/**
 * Created by The eXo Platform SARL
 * Author : HoaPham
 *          phamvuxuanhoa@yahoo.com
 * Jan 10, 2006
 */
public class TestNetService extends BasicTestCase {
  private NetService service_ ;
  
  public TestNetService(String name) {
    super(name) ;
  }
  
  public void setUp() throws Exception  {
    if(service_== null) {
      PortalContainer manager = PortalContainer.getInstance() ;
      service_ = (NetService)manager.getComponentInstanceOfType(NetService.class) ;
    }        
  }
  
  public void tearDown() throws Exception {
    
  }
  
  public void testNetService() throws Exception {
    ping(null,0) ;
    ping("www.google.com.vn",80) ;
    ping("www.vnexpress.net",80) ;    
    ping("www.googlevn.vn",80) ;
    //----ping a host on LAN
    ping("PC01",25) ;
  }
  
  private void ping(String host,int port) throws Exception {
    System.out.println("Ping host:" +host+ " port: "+port + " .....") ;
    long pingTime = 0 ;    
    pingTime = service_.ping(host,port) ; 
    if(pingTime>0) { 
      System.out.println("--------> Host is connected") ;
      System.out.println("--------> Ping time(ms): "+pingTime) ;
    } else {
      System.out.println("--------> Internet is not connected or host doesn't exits") ;
    }    
  }  
}
