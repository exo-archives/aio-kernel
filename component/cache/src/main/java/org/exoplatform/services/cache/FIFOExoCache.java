/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.cache;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by The eXo Platform SARL
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
