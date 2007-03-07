/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.xml;

import java.util.ArrayList;

/**
 * Jul 19, 2004 
 * @author: Tuan Nguyen
 * @email:   tuan08@users.sourceforge.net
 * @version: $Id: ManageableComponents.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class ManageableComponents  {
  
	private ArrayList		componentsType = new ArrayList(3);

	public ArrayList getComponentsType() {	return componentsType; }
	public void setComponentsType(ArrayList values) { this.componentsType = values; }
}