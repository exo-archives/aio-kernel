/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.cache;

import java.lang.ref.SoftReference;
/**
 * Created by The eXo Platform SARL
 * Author : Tuan Nguyen
 *          tuan08@users.sourceforge.net
 * Sat, Sep 13, 2003 @  
 * Time: 1:12:22 PM
 */
public class SimpleExoCache  extends BaseExoCache {
  public SimpleExoCache(int maxSize) {   super(maxSize) ; }
  
  public SimpleExoCache() {   
  }
  
  
  public SimpleExoCache(String name, int maxSize) {  
    super(name, maxSize) ; 
  }
  
  
  protected ObjectCacheInfo createObjectCacheInfo(long expTime, Object objToCache) {
    return new CacheSoftReference(expTime, objToCache) ;
  }
  
  static public class CacheSoftReference extends SoftReference implements ObjectCacheInfo {
    private long expireTime_ ;
    
    public CacheSoftReference(long expireTime, Object o) {
      super(o) ;
      expireTime_ = expireTime ;
    }
    
    public long getExpireTime() { return expireTime_ ;  }
  }
}