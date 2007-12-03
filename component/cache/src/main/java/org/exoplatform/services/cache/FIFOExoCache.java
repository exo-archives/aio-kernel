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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by The eXo Platform SAS
 * Author : Tuan Nguyen
 *          tuan08@users.sourceforge.net
 * Sat, Sep 13, 2003 @  
 * Time: 1:12:22 PM
 */
public class FIFOExoCache  extends BaseExoCache {
  public FIFOExoCache() {  }
  
  public FIFOExoCache(int maxSize) {   super(maxSize) ; }
  
  public FIFOExoCache(String name, int maxSize) {  super(name, maxSize) ; }
  
  protected ObjectCacheInfo createObjectCacheInfo(long expTime, Object objToCache) {
    return new ObjectCacheInfoImpl(expTime, objToCache) ;
  }
  
  synchronized public List getCachedObjects() {
    List dataList = new ArrayList() ;
    Iterator i =  values().iterator() ;
    while(i.hasNext()){
      ObjectCacheInfo info = (ObjectCacheInfo) i.next() ;
      if (info != null)
        dataList.add(info.get()) ;
    }
    return dataList ;
  }
  
  synchronized public List removeCachedObjects() throws Exception {
    List list =  getCachedObjects() ;
    clearCache() ;
    return list ;
  }
  
  static public class ObjectCacheInfoImpl  implements ObjectCacheInfo {
    private long expireTime_ ;
    private Object obj_ ;
    
    public ObjectCacheInfoImpl(long expireTime, Object o) {
      obj_ =  o ;
      expireTime_ = expireTime ;
    }
    
    public Object get() {  return obj_ ; }
    public long getExpireTime() { return expireTime_ ;  }    
  }
}
