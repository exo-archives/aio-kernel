/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.configuration.test;

import java.util.ArrayList;
import java.util.List;
/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Oct 27, 2004
 * @version $Id: ConfigParam.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class ConfigParam {
  private List role ;
  private List group ;
  private List ignoredUser ;
  
  public ConfigParam() {
    role = new ArrayList(3) ;
    group = new ArrayList(3) ;
    ignoredUser = new ArrayList(5) ;
  }
  
  public  List getRole()  { return role ; }
  public List getGroup() { return group ; }
  public List getIgnoredUser() { return ignoredUser ; }
  
  static public class Group {
    public String name ; 
    public String membership ;
    
    public Group() { 
    }
    
    public String getName() { return name ; }
    public void   setName(String s) { name = s  ; }
    
    public String getMembership() { return membership ; }
    public void   setMembership(String s) { membership = s ; }
  }
} 