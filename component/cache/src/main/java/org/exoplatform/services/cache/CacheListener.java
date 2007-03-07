/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.cache;

import java.io.Serializable;

/**
 * Created by The eXo Platform SARL
 * Author : Tuan Nguyen
 *          tuan08@users.sourceforge.net
 * Sep 19, 2005
 */
public interface CacheListener {
  public void onExpire(ExoCache  cache, Serializable key, Object obj) throws Exception ;
  public void onRemove(ExoCache  cache, Serializable key, Object obj) throws Exception ;
  public void onPut(ExoCache  cache, Serializable key, Object obj) throws Exception ;
  public void onGet(ExoCache  cache, Serializable key, Object obj) throws Exception ;
  public void onClearCache(ExoCache  cache) throws Exception ;
}