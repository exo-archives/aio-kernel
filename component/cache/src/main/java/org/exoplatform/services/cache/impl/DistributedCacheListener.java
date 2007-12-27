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
package org.exoplatform.services.cache.impl;

import java.io.Serializable;

import org.exoplatform.container.component.BaseComponentPlugin;
import org.exoplatform.services.cache.CacheListener;
import org.exoplatform.services.cache.ExoCache;
import org.exoplatform.services.remote.group.CommunicationService;
import org.exoplatform.services.remote.group.Message;

/**
 * Created by The eXo Platform SAS
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