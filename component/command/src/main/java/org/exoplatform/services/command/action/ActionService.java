/**
 * Copyright 2001-2006 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 */

package org.exoplatform.services.command.action;

import java.util.HashMap;

/**
 * Created by The eXo Platform SARL        .
 * @author Gennady Azarenkov
 * @version $Id: $
 */

public class ActionService extends HashMap <String, ActionCatalog> {
  
//  public void addPlugin(ComponentPlugin plugin) {
//    if (plugin instanceof AddActionCatalogPlugin) {
//      AddActionCatalogPlugin cplugin = (AddActionCatalogPlugin) plugin;
//      CatalogConfiguration conf = cplugin.getCatalogConfiguration();
//      ActionCatalog catalog = new ActionCatalog();
//      for(ActionConfiguration ac:(List <ActionConfiguration>)conf.getActions()) {
//        try {
//          ActionMatcher matcher = (ActionMatcher)Class.forName(ac.getMatcherFQCN()).newInstance();
//          Action action = (Action)Class.forName(ac.getActionFQCN()).newInstance();
//          catalog.addAction(matcher, action);
//        } catch (Exception e) {
//          e.printStackTrace();
//        } 
//      }
//      put(conf.getName(), catalog);
//    }
//  }

}
