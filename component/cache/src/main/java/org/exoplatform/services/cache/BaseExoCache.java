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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by The eXo Platform SAS
 * Author : Tuan Nguyen
 *          tuan08@users.sourceforge.net
 * Sat, Sep 13, 2003 @  
 * Time: 1:12:22 PM
 */
abstract public class BaseExoCache  extends LinkedHashMap implements ExoCache {
  private static int DEFAULT_MAX_SIZE =  50 ;
  
  private String name_ ;
  private String label_ ;
  
  private int maxSize_ ;
  private long liveTime_ = -1;
  private int cacheHit_ ;
  private int cacheMiss_ ;
  private boolean distributed_ = false ;
  private boolean replicated_ = false ;
  
  private ArrayList<CacheListener> listeners_ ;
  
  public BaseExoCache() {  maxSize_  = DEFAULT_MAX_SIZE ; }
  
  public BaseExoCache(int maxSize) {   maxSize_  = maxSize ; }
  
  public BaseExoCache(String name, int maxSize) {
    maxSize_  = maxSize ;
    name_ = name ;
  }
  
  public String getName() { return name_ ; }
  public void   setName(String s) {  name_ = s ; } 
  
  public String getLabel() { 
    if(label_ ==  null){
      if(name_.length() > 30 ){
        String shortLabel = name_.substring(name_.lastIndexOf(".") + 1) ; 
        setLabel(shortLabel) ;
        return  shortLabel;
      }
      return name_ ; 
    }
    return label_ ;    
  }
  
  public void setLabel(String name) {  label_ = name ; }
  
  public int  getCacheSize()  { return size() ; }
  
  public int  getMaxSize() { return maxSize_ ; }
  public void setMaxSize(int max) { maxSize_ = max ; }
  
  public long  getLiveTime() { return liveTime_ ; }
  public void  setLiveTime(long period)  { liveTime_ = period * 1000;} 
  
  synchronized public Object get(Serializable name)  throws Exception {
    ObjectCacheInfo info = (ObjectCacheInfo) super.get(name) ;
    if(info !=  null)  { 
      if(isExpire(info)) {
        onExpire(name, info.get()) ;
        super.remove(name) ;
        cacheMiss_++ ;
        return null ;
      }
      cacheHit_++ ;
      onGet(name, info.get()) ;
      return info.get() ;
    }
    cacheMiss_++ ;
    return null ; 
  }
  
  synchronized public Object remove(Serializable name)  throws Exception {
    ObjectCacheInfo ref = (ObjectCacheInfo) super.remove(name) ;
    if(ref == null)  return null;
    if(isExpire(ref)) {
      onExpire(name, ref.get()) ;
      return null ;
    }
    onRemove(name, ref.get()) ;
    return ref.get() ;
  }
  
  synchronized public void select(CachedObjectSelector selector) throws Exception  {
    Iterator i =  super.entrySet().iterator() ;
    List<Map.Entry> listEntry = new ArrayList<Map.Entry>();    
    while(i.hasNext()) {
      Map.Entry entry = (Map.Entry) i.next() ;
      Serializable key = (Serializable)entry.getKey() ;
      ObjectCacheInfo info = (ObjectCacheInfo) entry.getValue() ;
      if(selector.select(key, info)) listEntry.add(entry);
    }
    for(Map.Entry entry : listEntry){
      Serializable key = (Serializable)entry.getKey() ;
      ObjectCacheInfo info = (ObjectCacheInfo) entry.getValue() ;
      selector.onSelect(this, key, info) ;
    }
  }
  
  synchronized public void put(Serializable name, Object obj) throws Exception {
    if(liveTime_ == 0) return ;
    long expireTime = -1 ;
    if(liveTime_ > 0) expireTime = System.currentTimeMillis() + liveTime_ ;
    ObjectCacheInfo ref = createObjectCacheInfo(expireTime, obj) ;
    onPut(name,obj) ;
    super.put(name, ref) ;
  }
  
  synchronized public void clearCache() throws Exception {
    onClearCache() ;
    super.clear() ;
  }
  
  public int getCacheHit()  { return cacheHit_  ;}
  
  public int getCacheMiss() { return cacheMiss_ ; }
  
  public boolean isDistributed()  { return distributed_ ; }
  public void    setDistributed(boolean b)  { distributed_ =  b; }
  
  public boolean isReplicated()  { return replicated_ ; }
  public void    setReplicated(boolean b)  { replicated_ =  b; }
  
  protected boolean removeEldestEntry(Map.Entry eldest) {
    if(size() > maxSize_ ) {
      try {
        ObjectCacheInfo info = (ObjectCacheInfo)  eldest.getValue() ;
        remove(eldest.getKey()) ;
        onExpire((Serializable)eldest.getKey(), info.get()) ;
      } catch (Exception ex) {
        throw  new RuntimeException(ex) ;
      }
      return  true ;
    }
    return false ;
  }
  
  private boolean isExpire(ObjectCacheInfo info) {
    //-1 mean cache live for ever
    if(liveTime_ < 0) return false ;
    if(System.currentTimeMillis() > info.getExpireTime()) return true ;
    return false ;
  }
  
  public List getCachedObjects() {
    throw new RuntimeException("the  cached type " + getClass().getName() + " doesn't support this method" ) ;
  }
  
  public List removeCachedObjects() throws Exception {
    throw new RuntimeException("the  cached type " + getClass().getName() + " doesn't support this method" ) ;
  }
  
  public void addCacheListener(CacheListener listener) {
    if(listener == null)  return ;
    if(listeners_ == null) listeners_ = new ArrayList<CacheListener>() ;
    listeners_.add(listener) ;
  }
  
  synchronized public void localPut(Serializable key,  Object obj) throws Exception {
    long expireTime = -1 ;
    if(liveTime_ > 0) expireTime = System.currentTimeMillis() + liveTime_ ;
    ObjectCacheInfo ref = createObjectCacheInfo(expireTime, obj) ;
    super.put(key, ref) ;
  }
  
  synchronized public Object localRemove(Serializable key) throws Exception {
    ObjectCacheInfo ref = (ObjectCacheInfo) super.remove(key) ;
    if(ref != null)  {
      if(isExpire(ref)) return null ;
      return ref.get() ;
    }
    return null ;
  }
  
  synchronized public void localClear() throws Exception {
    super.clear() ;
  }
  
  private void onExpire(Serializable key, Object obj) throws Exception {
    if(listeners_ == null)  return ;
    for(CacheListener listener : listeners_ ) listener.onExpire(this, key, obj) ;
  }
  
  private void onRemove(Serializable key, Object obj) throws Exception {
    if(listeners_ == null)  return ;
    for(CacheListener listener : listeners_ ) listener.onRemove(this, key, obj) ;
  }
  
  private void onPut(Serializable key, Object obj) throws Exception {
    if(listeners_ == null)  return ;
    for(CacheListener listener : listeners_ ) listener.onPut(this, key, obj) ;
  }
  
  private void onGet(Serializable key, Object obj) throws Exception {
    if(listeners_ == null)  return ;
    for(CacheListener listener : listeners_ ) listener.onGet(this, key, obj) ;
  }
  
  private void onClearCache() throws Exception {
    if(listeners_ == null)  return ;
    for(CacheListener listener : listeners_ ) listener.onClearCache(this) ;
  }
  
  abstract protected ObjectCacheInfo createObjectCacheInfo(long expTime, Object objToCache) ;
}