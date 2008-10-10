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

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Mar 7, 2005
 * @version $Id: SynchronizeCacheMessage.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class SynchronizeCacheMessage implements Serializable {
  private String       cacheName_;

  private Serializable cacheKey_;

  private Serializable cacheValue_;

  public SynchronizeCacheMessage(String name, Serializable cacheKey, Serializable cacheValue) {
    cacheName_ = name;
    cacheKey_ = cacheKey;
    cacheValue_ = cacheValue;
  }

  public String getCacheName() {
    return cacheName_;
  }

  public Serializable getCacheKey() {
    return cacheKey_;
  }

  public Serializable getCacheValue() {
    return cacheValue_;
  }
}
