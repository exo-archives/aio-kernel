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
package org.exoplatform.services.remote.group.impl;

import org.exoplatform.services.remote.group.Message ;
import org.exoplatform.services.remote.group.MessageHandler;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Feb 21, 2005
 * @version $Id: MessageImpl.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class MessageImpl  implements Message {
  public String target_ ;
  public Object message_ ;
  
  public String getTargetHandler() { return target_ ; }
  public void   setTargetHandler(String targetHandler) { target_ = targetHandler ; }
  
  public Object getMessage() { return message_ ; }
  public void setMessage(Object obj) { message_ = obj ; }
}