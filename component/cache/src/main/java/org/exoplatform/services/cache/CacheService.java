/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.       *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.cache;

import java.io.Serializable;
import java.util.Collection;
/**
 * Created by The eXo Platform SARL        .
 * Author : Tuan Nguyen
 *          tuan08@users.sourceforge.net
 * Date: Jun 14, 2003
 * Time: 1:12:22 PM
 */
public interface CacheService  {
  
  public void addExoCacheConfig(ExoCacheConfigPlugin plugin) ;
  
  public ExoCache getCacheInstance(String region) throws Exception ;
  
  public Collection getAllCacheInstances() throws Exception ;
  
  public void synchronize(String region, Serializable key, Object value) throws Exception ;
}