/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.cache;

import java.util.ArrayList;
import java.util.List;

import org.exoplatform.container.component.BaseComponentPlugin;
import org.exoplatform.container.xml.InitParams;
/**
 * Created by The eXo Platform SARL
 * Author : Tuan Nguyen
 *          tuan08@users.sourceforge.net
 * Jan 6, 2006
 */
public class ExoCacheConfigPlugin extends  BaseComponentPlugin {
  private List<ExoCacheConfig> configs_ ;
  
  public ExoCacheConfigPlugin(InitParams params) {
    configs_ = new ArrayList<ExoCacheConfig>() ;
    List configs = params.getObjectParamValues(ExoCacheConfig.class) ;
    for(int i = 0 ; i  < configs.size(); i++) {
      ExoCacheConfig config = (ExoCacheConfig)configs.get(i) ;                     
      configs_.add(config) ;
    }
  }
  
  
  public List<ExoCacheConfig>  getConfigs() {  return  configs_ ; }
}
