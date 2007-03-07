/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.component;

import java.util.Collection;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Apr 18, 2005
 * @version $Id: RulePlugin.java 5799 2006-05-28 17:55:42Z geaz $
 */
public interface RulePlugin {
 
  public String getName() ;
  public void   setName(String s) ;
  
  public String getDescription() ;
  public void   setDescription(String s) ;
  
  public void execute(Object fact) throws Exception ;
  public void execute(Collection fact) throws Exception ;
  public void execute(Collection fact, boolean dynamic) throws Exception ;
}
