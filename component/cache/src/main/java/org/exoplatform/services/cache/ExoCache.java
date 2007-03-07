/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.cache;

import java.io.Serializable;
import java.util.List;
/**
 * Created by The eXo Platform SARL        .
 * Author : Tuan Nguyen
 *          tuan08@users.sourceforge.net
 * Date: Jun 14, 2003
 * Time: 1:12:22 PM
 */
public interface ExoCache  {
  
  public String getName() ;
  public void   setName(String name) ;
  
  public String getLabel() ;
  public void   setLabel(String s) ;
  
  public Object get(Serializable name) throws Exception ;
  public Object remove(Serializable name) throws Exception ;
  public void   put(Serializable name, Object obj) throws Exception ;
  public void   clearCache() throws Exception ;
  public void   select(CachedObjectSelector selector) throws Exception ;
  public int  getCacheSize() ;
  
  public int  getMaxSize() ;
  public void setMaxSize(int max) ;
  
  public long  getLiveTime() ;
  public void  setLiveTime(long period) ;
  
  public int getCacheHit() ;
  public int getCacheMiss() ;
  
  public List getCachedObjects() ;
  public List removeCachedObjects() throws Exception ;
  
  public void addCacheListener(CacheListener listener) ;
  
  public boolean isDistributed()  ;
  public void    setDistributed(boolean b) ;
  
  public boolean isReplicated()  ;
  public void    setReplicated(boolean b) ;
}
