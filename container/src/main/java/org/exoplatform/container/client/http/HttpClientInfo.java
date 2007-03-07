/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.client.http;

import javax.servlet.http.HttpServletRequest;

import org.exoplatform.container.client.ClientInfo;
/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Jan 12, 2005
 * @version $Id: HttpClientInfo.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class HttpClientInfo implements ClientInfo {
  final static public String  STANDARD_BROWSER_TYPE = "standard-browser" ;
  final static public String  MOBILE_BROWSER_TYPE = "mobile-browser" ;
  final static public String  PDA_BROWSER_TYPE = "pda-browser" ;
   
  private HttpClientType clientType_ ;
  private String     ipAddress_ ;
  private String     remoteUser_ ;
  
  public HttpClientInfo(HttpServletRequest request) {
    clientType_ = 
      ClientTypeMap.getInstance().findClient(request.getHeader("User-Agent")) ;
    remoteUser_ = request.getRemoteUser() ;
    ipAddress_ = request.getRemoteAddr() ;
  }
  
  public String getClientType() { return clientType_.getType() ; }
  
  public String getRemoteUser() { return remoteUser_ ; }
  
  public String getIpAddress() { return ipAddress_ ; }

  public String getClientName() {   return clientType_.getName() ; }

  public String getPreferredMimeType() {  return clientType_.getPreferredMimeType(); }

  public String getUserAgentPattern() {  return clientType_.getUserAgentPattern() ; }
}
