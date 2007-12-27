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

import java.io.Serializable;

/**
 * Created by The eXo Platform SAS
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