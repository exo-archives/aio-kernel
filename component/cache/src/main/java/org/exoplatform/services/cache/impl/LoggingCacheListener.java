/*
 * Copyright (C) 2003-2008 eXo Platform SAS.
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

import org.apache.commons.logging.Log;
import org.exoplatform.services.cache.CacheListener;
import org.exoplatform.services.cache.ExoCache;
import org.exoplatform.services.log.ExoLogger;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * Oct 4, 2008  
 */
public class LoggingCacheListener implements CacheListener {
  
  Log log = ExoLogger.getLogger("kernel.cache.log");

  public void onClearCache(ExoCache cache) throws Exception {
      if (log.isDebugEnabled()) {
        log.debug("Cleared region " + cache.getName());
      }
  }

  public void onExpire(ExoCache cache, Serializable key, Object obj) throws Exception {
    if (log.isTraceEnabled()) {
      log.trace("Expired entry " + key + " on region " + cache.getName());
    }
  }

  public void onGet(ExoCache cache, Serializable key, Object obj) throws Exception {
    if (log.isTraceEnabled()) {
      log.trace("Get entry " + key + " on region " + cache.getName());
    }
  }

  public void onPut(ExoCache cache, Serializable key, Object obj) throws Exception {
    if (log.isTraceEnabled()) {
      log.trace("Put entry " + key + " region " + cache.getName());
    }
    if (log.isWarnEnabled()) {
      int maxSize = cache.getMaxSize();  
      int size = cache.getCacheSize();
      double treshold = maxSize*0.95;
      if (size >= treshold) {
        log.warn("region " + cache.getName() + " is 95% full, consider extending maxSize");
      }
    }
    
  }

  public void onRemove(ExoCache cache, Serializable key, Object obj) throws Exception {
    if (log.isTraceEnabled()) {
      log.trace("Removed entry " + key + " region " + cache.getName());
    }
  }

}
