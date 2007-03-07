/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/

package org.exoplatform.commons.map;

import javax.portlet.PortletContext;
import java.util.Enumeration;

/**
 * @author Ove Ranheim (oranheim@users.sourceforge.net)
 * @since Nov 6, 2003 6:32:49 PM
 *
 */
public class ApplicationMap extends AbstractMap
{
    private PortletContext p_context;

    public ApplicationMap( PortletContext context )
    {
        p_context = context;
    }

    protected Object getAttribute( String name )
    {
        return p_context.getAttribute( name );
    }

    protected void setAttribute( String name, Object value )
    {
        p_context.setAttribute( name, value );
    }

    protected void removeAttribute( String name )
    {
        p_context.removeAttribute( name );
    }

    protected Enumeration getAttributeNames()
    {
        return p_context.getAttributeNames();
    }
}
