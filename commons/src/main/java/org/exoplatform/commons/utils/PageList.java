/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.commons.utils;

import java.util.* ;
import org.exoplatform.commons.exception.ExoMessageException;
/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Oct 21, 2004
 * @version $Id: PageList.java,v 1.2 2004/10/25 03:36:58 tuan08 Exp $
 */
abstract public class PageList {
  final static public PageList EMPTY_LIST = new ObjectPageList(new ArrayList(), 10) ;
  
  private int pageSize_ ;
  protected int available_ = 0;
  protected int availablePage_  = 1;
  protected int currentPage_ = -1 ;
  protected List currentListPage_ ;
  
  public PageList(int pageSize) {
    pageSize_ = pageSize ;
  }
  
  public int getPageSize() { return pageSize_  ; }
  public void setPageSize(int pageSize) {
    pageSize_ = pageSize ;
    setAvailablePage(available_) ;
  }
  
  public int getCurrentPage() { return currentPage_ ; }
  public int getAvailable() { return available_ ; }
  
  public int getAvailablePage() { return availablePage_ ; }
  
  public List currentPage() throws Exception {
    if(currentListPage_ == null) {
      populateCurrentPage(currentPage_) ;
    }
    return currentListPage_  ;
  }
  
  abstract protected void populateCurrentPage(int page) throws Exception   ;
  
  public List getPage(int page) throws Exception   {
    checkAndSetPage(page) ;
    populateCurrentPage(page) ;
    return currentListPage_ ;
  }
  
  abstract public List getAll() throws Exception  ;
  
  protected void checkAndSetPage(int page) throws Exception  {
    if(page < 1 || page > availablePage_) {
      Object[] args = { Integer.toString(page), Integer.toString(availablePage_) } ;
      throw new ExoMessageException("PageList.page-out-of-range", args) ;
    }
    currentPage_ =  page ;
  }
  
  protected void setAvailablePage(int available) {
    available_ = available ;
    if (available == 0)  {
      availablePage_ = 1 ; 
      currentPage_ =  1 ;
    } else {
      int pages = available / pageSize_ ;
      if ( available % pageSize_ > 0) pages++ ;
      availablePage_ = pages ;
      currentPage_ =  1 ;
    }
  }
  
  public int getFrom() { 
    return (currentPage_ - 1) * pageSize_ ; 
  }
  
  public int getTo() { 
    int to = currentPage_  * pageSize_ ; 
    if (to > available_ ) to = available_ ;
    return to ;
  }
}