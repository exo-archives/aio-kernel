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
package org.exoplatform.container.xml;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Jul 19, 2004 
 * @author: Tuan Nguyen
 * @email:   tuan08@users.sourceforge.net
 * @version: $Id: ValuesParam.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class ValuesParam extends  Parameter {
  
	private ArrayList		values = new ArrayList(2);

	public ArrayList getValues() {	return values; }
	public void setValues(ArrayList values) { this.values = values; }
  
  public String getValue() {
   if(values.size() == 0) return null ;
   return (String) values.get(0) ;
  }
  
  public String toString() {
    Iterator it = values.iterator();
    StringBuilder builder = new StringBuilder();
    while (it.hasNext()) {
      Object object = (Object) it.next();
      builder.append(object);
      if (it.hasNext()) {
        builder.append(",");
      }
    }
    return builder.toString();
  }
}