/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.faces.context;

import java.util.ResourceBundle;
import javax.portlet.PortletConfig ;
/**
 * Created by The eXo Platform SARL        .
 * Author : Tuan Nguyen
 *          tuan08@users.sourceforge.net
 * Created on Apr 7, 2004
 */
public interface PortletExternalContext {
	public PortletConfig getConfig() ;
	public ResourceBundle getApplicationResourceBundle() ;
}