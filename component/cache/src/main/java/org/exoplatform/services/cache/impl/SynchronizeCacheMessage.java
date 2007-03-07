/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.cache.impl;

import java.io.Serializable;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Mar 7, 2005
 * @version $Id: SynchronizeCacheMessage.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class SynchronizeCacheMessage implements Serializable {
  private String cacheName_ ;
  private Serializable cacheKey_ ;
  private Serializable cacheValue_ ;
  
  public SynchronizeCacheMessage(String name, Serializable cacheKey, Serializable cacheValue ) {
    cacheName_ = name ;
    cacheKey_ = cacheKey ;
    cacheValue_ = cacheValue ;
  }
  
  public String getCacheName() { return cacheName_ ; }
  
  public Serializable getCacheKey() { return cacheKey_ ; }
  
  public Serializable getCacheValue() { return cacheValue_ ; }
}
