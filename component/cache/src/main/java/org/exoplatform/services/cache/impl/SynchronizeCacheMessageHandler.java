/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.cache.impl;

import org.exoplatform.services.cache.CacheService;
import org.exoplatform.services.cache.ExoCache;
import org.exoplatform.services.remote.group.Message;
import org.exoplatform.services.remote.group.MessageHandler;
/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Mar 5, 2005
 * @version $Id: SynchronizeCacheMessageHandler.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class SynchronizeCacheMessageHandler extends MessageHandler  {
  final static public String IDENTIFIER =  "SynchronizeCacheMessageHandler" ;
  
  private CacheService cacheService_ ;
  
  public SynchronizeCacheMessageHandler(CacheService cacheService) { 
    super(IDENTIFIER) ;
    cacheService_ = cacheService ;
  }
  
  public Object handle(Message message) throws Exception {
    SynchronizeCacheMessage syncMessage = (SynchronizeCacheMessage) message.getMessage() ;
    //ExoCache cache = cacheService_.getCacheInstance(syncMessage.getCacheName()) ;
    cacheService_.synchronize(syncMessage.getCacheName(), syncMessage.getCacheKey(), syncMessage.getCacheValue()) ;
    return null ;
  }
}
