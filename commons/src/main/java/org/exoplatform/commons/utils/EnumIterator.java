/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/

package org.exoplatform.commons.utils;

import java.util.Iterator;
import java.util.Enumeration;

/**
 * @author Ove Ranheim (oranheim@users.sourceforge.net)
 * @since Nov 9, 2003 4:01:29 PM
 */
public class EnumIterator implements Iterator
{
	private Enumeration p_enum;
	
	public EnumIterator( Enumeration e )
	{
		p_enum = e;
	}
	
	public boolean hasNext()
	{
		return p_enum.hasMoreElements();
	}
	
	public Object next()
	{
		return p_enum.nextElement();
	}
	
	public void remove()
	{
		throw new UnsupportedOperationException();
	}
}
