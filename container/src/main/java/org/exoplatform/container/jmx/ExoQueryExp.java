/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.jmx;

import javax.management.BadAttributeValueExpException;
import javax.management.BadBinaryOpValueExpException;
import javax.management.BadStringOperationException;
import javax.management.InvalidApplicationException;
import javax.management.MBeanServer;
import javax.management.ObjectName;
import javax.management.QueryExp;

/**
 * Jul 30, 2004 
 * @author: Tuan Nguyen
 * @email:   tuan08@users.sourceforge.net
 * @version: $Id: ExoQueryExp.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class ExoQueryExp implements QueryExp {
	
  private String domain_ ;
  
  public ExoQueryExp(String domain) {
    domain_ = domain ;
  }
  
  public void setMBeanServer(MBeanServer s) {
    
  }
  
  public boolean apply(ObjectName name) throws BadStringOperationException,
																				BadBinaryOpValueExpException,
																				BadAttributeValueExpException,
																				InvalidApplicationException {
		return domain_.equals(name.getDomain());
	}
}