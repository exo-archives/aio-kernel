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
package org.exoplatform.container.jmx.support;

import org.exoplatform.management.annotations.Managed;
import org.exoplatform.management.ManagementAware;
import org.exoplatform.management.ManagementContext;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
@Managed
public class ManagedManagementAware implements ManagementAware {

  public ManagementContext context;

  private int count = 0;

  public AssertionError failure;

  public ManagedDependent foo = new ManagedDependent("Foo");

  public void setContext(ManagementContext context) {
    this.context = context;

    //
    if (count == 0) {
      if (context == null) {
        failure = new AssertionError();
      }
      context.register(foo);
      count = 1;
    } else if (count == 1) {
      if (context != null) {
        failure = new AssertionError();
      }
      context.unregister(foo);
    } else {
      failure = new AssertionError();
    }
  }
}
