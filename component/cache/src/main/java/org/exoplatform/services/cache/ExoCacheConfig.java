/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.cache;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Feb 20, 2005
 * @version $Id: ExoCacheConfig.java 5799 2006-05-28 17:55:42Z geaz $
 */
public class ExoCacheConfig {
  private String name ;
  private String label ;
  private int maxSize ;
  private long liveTime ;
  private boolean distributed = false ;
  private boolean replicated = false ;
  private String implementation ;
  
  public String getName() { return name ; }
  public void   setName(String s) { name = s ; }
  
  public String getLabel() { return label ; }
  public void   setLabel(String s) { label = s ; }
  
  public int  getMaxSize() { return maxSize ; }
  public void setMaxSize(int size) { maxSize = size ; }
  
  public long  getLiveTime() { return liveTime ; }
  public void  setLiveTime(long period)  {  liveTime = period * 1000; }
  
  public boolean isDistributed() { return distributed ; }
  public void    setDistributed(boolean b) { distributed = b ; }
  //public void    setDistributed(String b) { distributed_ = "true".equals(b) ; }
  
  public boolean isRepicated() { return replicated ; }
  public void    setReplicated(boolean b) { replicated = b ; }
  
  
  public String getImplementation() {  return implementation ; }
  public void   setImplementation(String alg) { implementation = alg ; } 
}