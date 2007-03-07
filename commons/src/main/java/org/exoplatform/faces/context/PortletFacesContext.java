/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.faces.context;

import javax.faces.context.FacesContext ;

/**
 * Apr 26, 2004
 * @author: Tuan Nguyen
 * @email:   tuan08@users.sourceforge.net
 * @version: $Id: PortletFacesContext.java,v 1.1 2004/07/31 14:48:51 tuan08 Exp $
 **/
public interface PortletFacesContext {
	public FacesContext getPortalFacesContext() ;
  public void destroy() ;
}
