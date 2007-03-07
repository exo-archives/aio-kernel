/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/

package org.exoplatform.commons.map;

import javax.servlet.http.HttpServletRequest ;
import javax.portlet.PortletRequest;
import java.util.Enumeration;

/**
 * @author Ove Ranheim (oranheim@users.sourceforge.net)
 * @since Nov 6, 2003 6:32:49 PM
 *
 */
public class RequestMap extends AbstractMap {
  private HttpServletRequest request_;

  public RequestMap( HttpServletRequest request ) {
    request_ = request;
  }

  public RequestMap( PortletRequest request ) {
    request_ = (HttpServletRequest) request;
  }

  final protected Object getAttribute( String name ) {
    return request_.getAttribute( name );
  }

  final protected void setAttribute( String name, Object value ) {
    request_.setAttribute( name, value );
  }

  final protected void removeAttribute( String name ) {
    request_.removeAttribute( name );
  }

  final protected Enumeration getAttributeNames() {
    return request_.getAttributeNames();
  }
}
