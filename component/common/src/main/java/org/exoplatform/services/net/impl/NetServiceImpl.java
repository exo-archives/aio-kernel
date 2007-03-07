/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.net.impl;

import java.net.Socket;

import org.exoplatform.services.net.NetService;

/**
 * Created by The eXo Platform SARL
 * Author : HoaPham
 *          phamvuxuanhoa@yahoo.com
 * Jan 10, 2006
 */
public class NetServiceImpl implements NetService{

  public long ping(String host, int port) throws Exception{
    long startTime = 0 ;
    long endTime = 0 ;    
    try{      
      startTime = System.currentTimeMillis() ;
      Socket socket = new Socket(host,port)  ;
      endTime = System.currentTimeMillis() ;
    }catch (Exception e) {
      //e.printStackTrace() ;
      return -1 ;
    }        
    return endTime - startTime;
  }  
}
