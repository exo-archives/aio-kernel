/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.remote.group;

import java.io.Serializable;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Feb 21, 2005
 * @version $Id: MemberInfo.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class MemberInfo implements Serializable {
  private String hostName_ ;
  private String ipAddress_ ;
  private int    port_ ;
  
  public String getHostName() { return hostName_ ; }
  public void   setHostName(String s) { hostName_ = s ; }
  
  public String getIpAddress() { return ipAddress_ ; }
  public void   setIpAddress(String s) { ipAddress_ = s ; }
  
  public int getPort() { return port_ ; } 
  public void setPort(int port) { port_ = port ; }
  
  public String toString() { return hostName_ + ":" + port_ ; }
}
