/*****************************************************************************
 * Copyright (C) NanoContainer Organization. All rights reserved.            *
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD      *
 * style license a copy of which has been included with this distribution in *
 * the LICENSE.txt file.                                                     *
 *                                                                           *
 * Original code by James Strachan and Mauro Talevi                          *
 *****************************************************************************/
package org.exoplatform.container;

import org.picocontainer.ComponentAdapter;
import org.picocontainer.PicoContainer;
import org.picocontainer.PicoVisitor;

/**
 * @author James Strachan
 * @author Mauro Talevi
 * @author Jeppe Cramon
 * @author Benjamin Mestrallet
 * @version $Revision: 1.5 $
 */
public class SimpleComponentAdapter implements ComponentAdapter {

  private Object instance_;

  private Object key_;

  private Class  implementation_;

  public SimpleComponentAdapter(Object key, Class implementation) {
    key_ = key;
    implementation_ = implementation;
  }

  public Object getComponentInstance(PicoContainer container) {
    if (instance_ != null)
      return instance_;
    ExoContainer exocontainer = (ExoContainer) container;
    try {
      synchronized (container) {
        instance_ = exocontainer.createComponent(getComponentImplementation());
      }
    } catch (Exception ex) {
      throw new RuntimeException("Cannot instantiate component " + getComponentImplementation(), ex);
    }
    return instance_;
  }

  public void verify(PicoContainer container) {
  }

  public Object getComponentKey() {
    return key_;
  }

  public Class getComponentImplementation() {
    return implementation_;
  }

  public void accept(PicoVisitor visitor) {
    visitor.visitComponentAdapter(this);
  }
}
