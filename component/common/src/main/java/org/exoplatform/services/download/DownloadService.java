/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.download;

/**
 * Created by The eXo Platform SARL
 * Author : Tuan Nguyen
 *          tuan08@users.sourceforge.net
 * Dec 26, 2005
 * 
 * This service is used manage the download resource. Each 
 */
public interface DownloadService {
  public void addDefaultDownloadResource(DownloadResource resource) ;
  public String addDownloadResource(DownloadResource resource) ;
  public DownloadResource getDownloadResource(String id) throws Exception ;
  public String getDownloadLink(String id) ;
}
