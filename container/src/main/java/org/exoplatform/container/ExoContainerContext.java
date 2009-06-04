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
package org.exoplatform.container;

import org.apache.commons.logging.Log;
import org.exoplatform.services.log.ExoLogger;

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

  private static volatile ExoContainer     topContainer;

  private HashMap<String, Object>          attributes       = new HashMap<String, Object>();

  private ExoContainer                     container;

  private String                           name;

  private static final Log log = ExoLogger.getLogger(ExoContainerContext.class);

  public ExoContainerContext(ExoContainer container) {
    this.container = container;
  }

  public ExoContainer getContainer() {
    return container;
  }

  public String getName() {
    return name;
  }

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
      throw new IllegalStateException("Two top level containers created, but must be only one.");
    log.info("Set the top container in its context");
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
    if (container == null)
      return topContainer;
    return container;
  }

  public static void setCurrentContainer(ExoContainer instance) {
    currentContainer.set(instance);
  }

  public static ExoContainer getContainerByName(String name) {
    ExoContainerContext containerContext = topContainer.getContext();
    String name1 = containerContext.getName();
    if (name1.equals(name))
      return topContainer;
    return (ExoContainer) topContainer.getComponentInstance(name);
  }

  public Set<String> getAttributeNames() {
    return attributes.keySet();
  }

  public Object getAttribute(String name) {
    return attributes.get(name);
  }

  public void setAttribute(String name, Object value) {
    attributes.put(name, value);
  }
}
