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


/**
 * Created by The eXo Platform SAS
 * Author : Tuan Nguyen
 *          tuan08@users.sourceforge.net
 * Sep 8, 2005
 */
public class ComponentLifecyclePlugin {
  private String type ;
  private ManageableComponents manageableComponents  ;
  private InitParams initParams ;
 
  public String getType() {   return type; }
  public void setType(String type) {   this.type = type; }
  
  public ManageableComponents  getManageableComponents() { 
    return manageableComponents ;
  }
  
  public  void setManageableComponents(ManageableComponents mc) {
    manageableComponents = mc  ;
  }
  
  public InitParams getInitParams() {  return initParams; }
  public void setInitParams(InitParams initParams) {  this.initParams = initParams; }
 
}
