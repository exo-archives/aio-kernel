/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.cache.impl;

import java.io.Serializable;

import org.exoplatform.container.component.BaseComponentPlugin;
import org.exoplatform.services.cache.CacheListener;
import org.exoplatform.services.cache.ExoCache;
import org.exoplatform.services.remote.group.CommunicationService;
import org.exoplatform.services.remote.group.Message;

/**
 * Created by The eXo Platform SARL
 * Author : Tuan Nguyen
 *          tuan08@users.sourceforge.net
 * Sep 19, 2005
 */
public class DistributedCacheListener extends BaseComponentPlugin implements CacheListener {
  private CommunicationService communicationService_ ;
  
  public DistributedCacheListener(CommunicationService communicationService) {
    communicationService_ = communicationService ;
  }

  public void onExpire(ExoCache cache, Serializable key, Object obj) throws Exception {
    
  }

  public void onRemove(ExoCache cache, Serializable key, Object obj) throws Exception {
    broadcast(cache, key, null) ;
  }

  public void onPut(ExoCache cache, Serializable key, Object obj) throws Exception {
    broadcast(cache, key, obj) ;
  }

  public void onGet(ExoCache cache, Serializable key, Object obj) throws Exception {

  }
  
  public void onClearCache(ExoCache  cache) throws Exception  {
    broadcast(cache, null, null) ;
  }
  
  private void broadcast(ExoCache cache, Serializable key, Object value) throws Exception {
    Message message = 
      communicationService_.createMessage(SynchronizeCacheMessageHandler.IDENTIFIER) ;
    SynchronizeCacheMessage synMessage = null ;
    if(cache.isReplicated()) {
      synMessage = new SynchronizeCacheMessage(cache.getName(), key, (Serializable)value) ;
    } else {
      synMessage = new SynchronizeCacheMessage(cache.getName(), key, null) ;
    }
    message.setMessage(synMessage) ;
    communicationService_.broadcast(message, false) ;
  }
}