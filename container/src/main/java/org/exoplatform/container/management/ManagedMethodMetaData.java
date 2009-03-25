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
package org.exoplatform.container.management;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.HashMap;
import java.util.Collection;
import java.util.Collections;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class ManagedMethodMetaData extends ManagedMetaData {

  /** . */
  private final Method method;

  /** . */
  private final Map<Integer, ManagedMethodParameterMetaData> parameters;

  /**
   * Build a new instance.
   *
   * @param method the method
   * @throws NullPointerException if the method is null
   */
  public ManagedMethodMetaData(Method method) throws NullPointerException {
    if (method == null) {
      throw new NullPointerException();
    }

    //
    this.method = method;
    this.parameters = new HashMap<Integer, ManagedMethodParameterMetaData>();
  }

  public Method getMethod() {
    return method;
  }

  public void addParameter(ManagedMethodParameterMetaData parameter) {
    if (parameter == null) {
      throw new NullPointerException("No null parameter accepted");
    }
    parameters.put(parameter.getIndex(), parameter);
  }

  public Collection<ManagedMethodParameterMetaData> getParameters() {
    return Collections.unmodifiableCollection(parameters.values());
  }
}
