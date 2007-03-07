/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.client;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Feb 7, 2005
 * @version $Id: MockClientInfo.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class MockClientInfo implements ClientInfo {
  public MockClientInfo() {} 
  
  public String getClientType() {  return "N/A"; }

 
  public String getRemoteUser() {  return "exo"; }

  public String getIpAddress() {  return "127.0.0.1"; }

  public String getClientName() {  return "Mock client"; }
  
}
