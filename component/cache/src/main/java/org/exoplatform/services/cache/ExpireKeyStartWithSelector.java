/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.cache;

import java.io.Serializable;

/**
 * Created by The eXo Platform SARL
 * Author : Thuannd
 *         nhudinhthuan@yahoo.com
 * Apr 4, 2006
 */
public class ExpireKeyStartWithSelector implements CachedObjectSelector {
  
  private String keyStartWith_ ;
  
  public ExpireKeyStartWithSelector(String keyStartWith) {
    keyStartWith_ = keyStartWith ;
  }
  
  public boolean select(Serializable key, ObjectCacheInfo ocinfo) {
    String skey = (String) key ;
    if(skey.startsWith(keyStartWith_)) return true ;
    return false;
  }

  public void onSelect(ExoCache cache, Serializable key, ObjectCacheInfo ocinfo) throws Exception {
    cache.remove(key) ;
  }

}
