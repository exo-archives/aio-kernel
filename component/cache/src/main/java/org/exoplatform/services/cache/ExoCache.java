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
package org.exoplatform.services.cache;

import java.io.Serializable;
import java.util.List;
/**
 * Created by The eXo Platform SAS.
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
