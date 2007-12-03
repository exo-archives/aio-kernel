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
package org.exoplatform.services.mail.impl;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Sep 12, 2004
 * @version $Id: ExoAuthenticator.java 5332 2006-04-29 18:32:44Z geaz $
 */
public class ExoAuthenticator extends Authenticator{
  private PasswordAuthentication authentication_ ;
  
  public ExoAuthenticator(String userName , String password) {
    authentication_ = new PasswordAuthentication(userName, password) ;
  }
  
  protected PasswordAuthentication getPasswordAuthentication() {
    return authentication_ ;
  }
}