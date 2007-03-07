/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.commons.utils;

import java.util.ArrayList ;
/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Nov 10, 2004
 * @version $Id$
 */
public class ListenerStack extends ArrayList {
  public ListenerStack() {
    super() ;
  }
  
  public ListenerStack(int size) {
    super(size) ;
  }
  
  public void add(int index,  Object element) {
    throw new UnsupportedOperationException("use add(java.lang.Object)") ;
  }
  
  public boolean add(java.lang.Object obj) {
    String name = obj.getClass().getName(); 
    for(int i = 0; i < size(); i++) {
      Object found = get(i) ;
      if(name.equals(found.getClass().getName())) {
        remove(i) ;
        break ;
      }
    }
    return super.add(obj) ;
  }
}
