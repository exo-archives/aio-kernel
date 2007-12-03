/**
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.
 * Please look at license.txt in info directory for more license detail.
 **/
package org.exoplatform.container;

import java.util.HashMap;

import org.exoplatform.container.client.ClientInfo;
/**
 * Created by The eXo Platform SAS
 * Author : Tuan Nguyen
 *          tuan08@users.sourceforge.net
 * Date: Jul 18, 2004
 * Time: 12:15:28 AM
 */
public class SessionContainer extends HashMap <Object, Object>{
  private static ThreadLocal threadLocal_ = new ThreadLocal() ;
  final public  static  int INIT_STATUS = 0 ; 
  final public  static  int START_STATUS = 1 ; 
  final public  static  int STOP_STATUS = 2 ;
  final public  static  int DESTROY_STATUS = 3 ; 
  
  private String owner_ ;
  private String portalName_ ;
  private ClientInfo clientInfo_ ;
  private long startTime_ = -1;
  private String sessionId_ ;
  private String uniqueId_ ;
  private int status_ ;
  private long lastAccessTime_ ;
  
  public SessionContainer(String id, String owner) {
    sessionId_ = id ;
    owner_ = owner ;
  }
  
  public String getSessionId() { return sessionId_ ; }
  
  public String getUniqueId() { return uniqueId_ ; }
  public void   setUniqueId(String s) { uniqueId_ = s ; }
  
  public String getOwner() { return  owner_ ; }

  public String getRemoteUser() { return  clientInfo_.getRemoteUser() ; }
  
  public String getPortalName() { return portalName_ ; }
  public void   setPortalName(String name) { portalName_ = name ; }
  
  public ClientInfo getClientInfo() { return clientInfo_ ; }
  public void       setClientInfo(ClientInfo ci) { clientInfo_ = ci ; }
  
  public int getStatus()  { return status_ ; }
  public void setStatus(int status) { status_ =  status ; }
  
  public long getCreationTime() { return startTime_ ; }
  public long getLastAccessTime() {  return lastAccessTime_ ; }
  public void setLastAccessTime(long time) {
    if(startTime_  < 0)  startTime_ = time ;
    lastAccessTime_ =  time ;
  }
  
  public long getLiveTime() { return System.currentTimeMillis() - startTime_ ;}
  public long getLiveTimeInMinute() { return (System.currentTimeMillis() - startTime_)/60000 ;}
  public long getLiveTimeInSecond() { return (System.currentTimeMillis() - startTime_)/1000 ;}
  
  public static Object getComponent(Class key) {
    SessionContainer scontainer = (SessionContainer)threadLocal_.get() ;
    return scontainer.get(key) ;
  }
  
  final public  void registerComponentInstance(Object key,  Object obj) {  put(key, obj) ; }
  final public Object getComponentInstance(Object key) {  return get(key) ; }
  final public Object getComponentInstanceOfType(Class key) {  return get(key) ; }
  
  static public SessionContainer getInstance() { return (SessionContainer)threadLocal_.get() ;}
  static public void setInstance(SessionContainer scontainer) {threadLocal_.set(scontainer) ; }
  
}
