/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/

package org.exoplatform.commons.map;

import javax.servlet.http.HttpServletRequest ;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Set;

/**
 * @author Ove Ranheim (oranheim@users.sourceforge.net)
 * @since Nov 6, 2003 6:32:49 PM
 *
 */
public class RequestParameterMap extends AbstractMap {
  private HttpServletRequest request_;

  public RequestParameterMap(HttpServletRequest request ) {
    request_ = request;
  }

  protected Object getAttribute( String name ) {
    return request_.getParameter( name );
  }

  protected void setAttribute( String name, Object value ) {
    throw new UnsupportedOperationException();
  }

  protected void removeAttribute( String name ) {
    throw new UnsupportedOperationException();
  }

  protected Enumeration getAttributeNames() {
    return request_.getParameterNames();
  }
  
  public Set keySet() {
    return request_.getParameterMap().keySet();
  }

  public Collection values() {
    return request_.getParameterMap().keySet();
  }

  public Set entrySet() {
    return request_.getParameterMap().entrySet();
  }  
  
}
