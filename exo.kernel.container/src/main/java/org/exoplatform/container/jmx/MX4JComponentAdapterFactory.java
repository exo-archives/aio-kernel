/*****************************************************************************
 * Copyright (C) NanoContainer Organization. All rights reserved.            *
 * ------------------------------------------------------------------------- *
 * The software in this package is published under the terms of the BSD      *
 * style license a copy of which has been included with this distribution in *
 * the LICENSE.txt file.                                                     *
 *                                                                           *
 * Original code by James Strachan and Mauro Talevi                          *
 *****************************************************************************/
package org.exoplatform.container.jmx;

import java.io.Serializable;

import org.picocontainer.ComponentAdapter;
import org.picocontainer.Parameter;
import org.picocontainer.PicoIntrospectionException;
import org.picocontainer.defaults.AssignabilityRegistrationException;
import org.picocontainer.defaults.ComponentAdapterFactory;
import org.picocontainer.defaults.NotConcreteRegistrationException;

public class MX4JComponentAdapterFactory implements ComponentAdapterFactory, Serializable {
  public ComponentAdapter createComponentAdapter(Object key, Class impl, Parameter[] params) throws PicoIntrospectionException,
                                                                                            AssignabilityRegistrationException,
                                                                                            NotConcreteRegistrationException {
    return new MX4JComponentAdapter(key, impl);
  }
}
