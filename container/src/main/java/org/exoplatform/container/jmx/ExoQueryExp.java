/*
 * Copyright (C) 2003-2007 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
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