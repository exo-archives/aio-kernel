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
package org.exoplatform.services.cache;

import java.lang.ref.SoftReference;

/**
 * Created by The eXo Platform SAS Author : Tuan Nguyen
 * tuan08@users.sourceforge.net Sat, Sep 13, 2003 @ Time: 1:12:22 PM
 */
public class SimpleExoCache extends BaseExoCache {
  public SimpleExoCache(int maxSize) {
    super(maxSize);
  }

  public SimpleExoCache() {
  }

  public SimpleExoCache(String name, int maxSize) {
    super(name, maxSize);
  }

  protected ObjectCacheInfo createObjectCacheInfo(long expTime, Object objToCache) {
    return new CacheSoftReference(expTime, objToCache);
  }

  static public class CacheSoftReference extends SoftReference implements ObjectCacheInfo {
    private long expireTime_;

    public CacheSoftReference(long expireTime, Object o) {
      super(o);
      expireTime_ = expireTime;
    }

    public long getExpireTime() {
      return expireTime_;
    }
  }
}
