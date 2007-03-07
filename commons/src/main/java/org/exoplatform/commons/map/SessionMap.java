/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/

package org.exoplatform.commons.map;

import javax.portlet.PortletSession;
import java.util.Enumeration;

/**
 * @author Ove Ranheim (oranheim@users.sourceforge.net)
 * @since Nov 6, 2003 6:32:49 PM
 *
 */
public class SessionMap extends AbstractMap
{
    private PortletSession p_session;

    public SessionMap( PortletSession session )
    {
        p_session = session;
    }

    protected Object getAttribute( String name )
    {
        if (p_session != null) {
            return p_session.getAttribute( name );
        } else {
            return null;
        }
    }

    protected void setAttribute( String name, Object value )
    {
        if (p_session != null)
            p_session.setAttribute( name, value );
    }

    protected void removeAttribute( String name )
    {
        if (p_session != null)
            p_session.removeAttribute( name );
    }

    protected Enumeration getAttributeNames()
    {
        if (p_session != null)
            return p_session.getAttributeNames();
        else
            return null;
    }
}
