/**
 * Copyright 2001-2006 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 */

package org.exoplatform.services.command.action;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by The eXo Platform SARL        .
 * @author Gennady Azarenkov
 * @version $Id: $
 */

public class ActionCatalog {
  
  private Map <ActionMatcher, Action> commands;
  
  public ActionCatalog() {
    this.commands = new HashMap<ActionMatcher, Action>();
  }
  
  public Set <Action> getAllActions() {
    return new HashSet<Action>(commands.values());
  }
  
  public Map <ActionMatcher, Action> getAllEntries() {
    return commands;
  }

  public Set <Action> getActions(Condition conditions) {
    HashSet <Action> actions = new HashSet<Action>();
    for(Map.Entry <ActionMatcher, Action> entry:commands.entrySet()) {
      if(entry.getKey().match(conditions))
        actions.add(entry.getValue());
    }
    return actions;
  }

  public Action getAction(Condition conditions, int index) {
    Iterator <Action> actions = getActions(conditions).iterator();
    for(int i=0; actions.hasNext(); i++) {
      Action c = actions.next();
      if(i == index)
        return c;
    }
    return null;
  }

  public void addAction(ActionMatcher matcher, Action action) {
    commands.put(matcher, action);
  }

  public void clear() {
    commands.clear();
  }
}
