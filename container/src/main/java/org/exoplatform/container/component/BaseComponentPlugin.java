/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.component;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Apr 18, 2005
 * @version $Id: BaseComponentPlugin.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class BaseComponentPlugin implements ComponentPlugin {
  
  protected String name ;
  protected String desc ;
  
  public String getName() { return name ; }
  public void   setName(String s) { name = s ; } 
  
  public String getDescription() { return desc ; }
  public void   setDescription(String s)  { desc  =  s ; }
}
