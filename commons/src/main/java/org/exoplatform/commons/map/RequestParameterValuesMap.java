/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/

package org.exoplatform.commons.map;

import javax.portlet.PortletRequest;
import java.util.Enumeration;

/**
 * @author Ove Ranheim (oranheim@users.sourceforge.net)
 * @since Nov 6, 2003 6:32:49 PM
 *
 */
public class RequestParameterValuesMap extends AbstractMap
{
    private PortletRequest p_request;

    public RequestParameterValuesMap( PortletRequest request )
    {
        p_request = request;
    }

    protected Object getAttribute( String name )
    {
        return p_request.getParameterValues( name );
    }

    protected void setAttribute( String name, Object value )
    {
        throw new UnsupportedOperationException();
    }

    protected void removeAttribute( String name )
    {
        throw new UnsupportedOperationException();
    }

    protected Enumeration getAttributeNames()
    {
        return p_request.getParameterNames();
    }
}
