/*
 * Copyright (C) 2003-2009 eXo Platform SAS.
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
package org.exoplatform.services.cache.impl.jboss;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.exoplatform.services.cache.CacheListener;
import org.exoplatform.services.cache.CachedObjectSelector;
import org.exoplatform.services.cache.ExoCache;
import org.exoplatform.services.cache.ExoCacheConfig;
import org.exoplatform.services.cache.ExoCacheInitException;
import org.jboss.cache.TreeCache;

/**
 * Created by The eXo Platform SAS
 * Author : eXoPlatform
 *          exo@exoplatform.com
 * 21 juil. 2009  
 */
public class TestExoCacheCreator implements ExoCacheCreator {

  public ExoCache create(ExoCacheConfig config, TreeCache cache) throws ExoCacheInitException {
    return new TestExoCache();
  }

  public Class<? extends ExoCacheConfig> getExpectedConfigType() {
    return TestExoCacheConfig.class;
  }

  public String getExpectedImplementation() {
    return "TEST";
  }

  public static class TestExoCache implements ExoCache {

    public void addCacheListener(CacheListener listener) {
      // TODO Auto-generated method stub
      
    }

    public void clearCache() throws Exception {
      // TODO Auto-generated method stub
      
    }

    public Object get(Serializable name) throws Exception {
      // TODO Auto-generated method stub
      return null;
    }

    public int getCacheHit() {
      // TODO Auto-generated method stub
      return 0;
    }

    public int getCacheMiss() {
      // TODO Auto-generated method stub
      return 0;
    }

    public int getCacheSize() {
      // TODO Auto-generated method stub
      return 0;
    }

    public List getCachedObjects() {
      // TODO Auto-generated method stub
      return null;
    }

    public String getLabel() {
      // TODO Auto-generated method stub
      return null;
    }

    public long getLiveTime() {
      // TODO Auto-generated method stub
      return 0;
    }

    public int getMaxSize() {
      // TODO Auto-generated method stub
      return 0;
    }

    public String getName() {
      return "name";
    }

    public boolean isDistributed() {
      // TODO Auto-generated method stub
      return false;
    }

    public boolean isLogEnabled() {
      // TODO Auto-generated method stub
      return false;
    }

    public boolean isReplicated() {
      // TODO Auto-generated method stub
      return false;
    }

    public void put(Serializable name, Object obj) throws Exception {
      // TODO Auto-generated method stub
      
    }

    public void putMap(Map<Serializable, Object> objs) throws Exception {
      // TODO Auto-generated method stub
      
    }

    public Object remove(Serializable name) throws Exception {
      // TODO Auto-generated method stub
      return null;
    }

    public List removeCachedObjects() throws Exception {
      // TODO Auto-generated method stub
      return null;
    }

    public void select(CachedObjectSelector selector) throws Exception {
      // TODO Auto-generated method stub
      
    }

    public void setDistributed(boolean b) {
      // TODO Auto-generated method stub
      
    }

    public void setLabel(String s) {
      // TODO Auto-generated method stub
      
    }

    public void setLiveTime(long period) {
      // TODO Auto-generated method stub
      
    }

    public void setLogEnabled(boolean b) {
      // TODO Auto-generated method stub
      
    }

    public void setMaxSize(int max) {
      // TODO Auto-generated method stub
      
    }

    public void setName(String name) {
      // TODO Auto-generated method stub
      
    }

    public void setReplicated(boolean b) {
      // TODO Auto-generated method stub
      
    }
    
  }
}
