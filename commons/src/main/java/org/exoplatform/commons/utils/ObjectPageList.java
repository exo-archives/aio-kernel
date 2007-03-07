/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.commons.utils;

import java.util.List;
/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Oct 21, 2004
 * @version $Id: ObjectPageList.java,v 1.1 2004/10/22 14:18:46 tuan08 Exp $
 */
public class ObjectPageList extends PageList {
  
  private List objects_  ;
  
  public ObjectPageList(List list, int pageSize) {
    super(pageSize) ;
    objects_ = list ;
    setAvailablePage(list.size()) ;
  }
  
  protected void populateCurrentPage(int page) throws Exception  {
    currentListPage_ = objects_.subList(getFrom(), getTo()) ;
  }
  
  public List getAll() throws Exception  { return objects_ ; }
}
