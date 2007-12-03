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
package org.exoplatform.container.client;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Feb 7, 2005
 * @version $Id: MockClientInfo.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class MockClientInfo implements ClientInfo {
  public MockClientInfo() {} 
  
  public String getClientType() {  return "N/A"; }

 
  public String getRemoteUser() {  return "exo"; }

  public String getIpAddress() {  return "127.0.0.1"; }

  public String getClientName() {  return "Mock client"; }
  
}
