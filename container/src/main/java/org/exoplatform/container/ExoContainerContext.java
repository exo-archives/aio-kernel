/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container;

import java.util.HashMap;
import java.util.Set;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Jan 11, 2005
 * @version $Id: ExoContainerContext.java 6677 2006-07-03 10:49:59Z geaz $
 */
@SuppressWarnings("serial")
public final class ExoContainerContext implements java.io.Serializable {

  private static ThreadLocal<ExoContainer> currentContainer = new ThreadLocal<ExoContainer>();
  private static ExoContainer topContainer;
  private HashMap <String, Object> attributes = new HashMap <String, Object>();

  private ExoContainer container;
  private String name;

  public ExoContainerContext(ExoContainer container) {
     this.container = container;
  }

  public ExoContainer getContainer() { return container; }

  public String getName() { return name; }

  public void setName(String name) {
     this.name = name;
  }

  public static ExoContainer getTopContainer() {
    if (topContainer == null)
      topContainer = RootContainer.getInstance();
    return topContainer;
  }

  static void setTopContainer(ExoContainer cont) {
    if (topContainer != null && cont != null)
      throw new RuntimeException("Two top level containers created, but must be only one.");
    topContainer = cont;
  }

  public static ExoContainer getCurrentContainer() {
    ExoContainer container = currentContainer.get();
    if (container == null)
      container = getTopContainer();
    return container;
  }
  
  public static ExoContainer getCurrentContainerIfPresent() {
    ExoContainer container = currentContainer.get();
    if (container == null) return topContainer;
    return container;
  }


  public static void setCurrentContainer(ExoContainer instance) {
    currentContainer.set(instance);
  }

  public static ExoContainer getContainerByName(String name) {
    if (topContainer.getContext().getName().equals(name))
      return topContainer;
    return (ExoContainer) topContainer.getComponentInstance(name);
  }
  
  public Set <String> getAttributeNames() { return attributes.keySet(); }
  
  public Object getAttribute(String name) { return attributes.get(name); }
  
  public void setAttribute(String name, Object value) { attributes.put(name, value); }
}
