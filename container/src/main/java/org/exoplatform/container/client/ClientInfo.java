/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.client;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Jan 25, 2005
 * @version $Id: ClientInfo.java 5799 2006-05-28 17:55:42Z geaz $
 */
public interface ClientInfo {
  public static ClientInfo DEFAULT = new MockClientInfo() ;
  
  public String getClientType()  ;
  
  public String getRemoteUser()  ;
  
  public String getIpAddress()  ;

  public String getClientName() ;
}
