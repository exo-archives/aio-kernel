/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform;

import java.util.List;
import java.util.Map;

/**
 * Fri, May 30, 2003 @
 * @author : Mestrallet Benjamin
 * @email:   benjmestrallet@users.sourceforge.net
 * @author: Tuan Nguyen
 * @version: $Id: Constants.java,v 1.16 2004/07/13 02:46:20 tuan08 Exp $
 * @email: tuan08@yahoo.com
 */
public class Constants {
  final static public List EMPTY_LIST = new java.util.ArrayList() ;
  final static public Map EMPTY_MAP = new java.util.HashMap() ;
  
  final static  public String AMPERSAND = "&amp;" ;
  final static public String DEFAUL_PORTAL_OWNER = "exo";
  
  final static public String PARAMETER_ENCODER = "portal:";
  final static public String PORTLET_META_DATA_ENCODER = "/";
  final static public String PORTLET_HANDLE_ENCODER = "/";

  public static final String PORTAL_CONTEXT = PARAMETER_ENCODER + "ctx";
  final static public String EXO_PORTAL_INFO = "javax.portlet.exo-portal-info"  ;
  public static final String PORTAL_ACTION = PARAMETER_ENCODER + "action";

  public static final String COMPONENT_PARAMETER = PARAMETER_ENCODER + "componentId";
  public static final String TYPE_PARAMETER = PARAMETER_ENCODER + "type";
  public static final String WINDOW_STATE_PARAMETER = PARAMETER_ENCODER + "windowState";
  public static final String PORTLET_MODE_PARAMETER = PARAMETER_ENCODER + "portletMode";
  public static final String RESOURCE_ID_PARAMETER = PARAMETER_ENCODER + "resourceID";
  public static final String RESOURCE_CACHELEVEL_PARAMETER = PARAMETER_ENCODER + "cacheLevel";
  public static final String SECURE_PARAMETER = PARAMETER_ENCODER + "isSecure";
  public static final String LANGUAGE_PARAMETER = PARAMETER_ENCODER + "lang" ;

  public static final String PORTLET_CONTAINER = "org.exoplatform.portletcontainer";
  public static final String PORTLET_META_DATA = PORTLET_CONTAINER + ".portlet";
  public static final String SERVLET_META_DATA = PORTLET_CONTAINER + ".servlet";

  public static final String PORTLET_ENCODER = "_portlet_";
  public static final String VALIDATOR_ENCODER = "_validator_";
  public static final String FILTER_ENCODER = "_filter_";
  public static final String MESSAGE_LISTENER_ENCODER = "_message_listener_";
  
  public static final String APPLICATION_RESOURCE = "javax.portlet.application-resource";


  final static public String ADMIN_USER = "admin" ;
  final static public String ANON_USER =  "demo" ;

  //portlets
  public static final String FORWARD_PAGE = "org.exoplatform.portal.portlet.ForwardPage" ;
  //roles
  public static final String USER_ROLE = "user" ;
  public static final String ADMIN_ROLE = "admin" ;
  public static final String GUEST_ROLE = "guest" ;

  //security
  public static final String PUBLIC = "public" ;
  public static final String PRIVATE = "private" ;    
}
