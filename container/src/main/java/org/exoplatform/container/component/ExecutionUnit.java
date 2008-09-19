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
package org.exoplatform.container.component;

/**
 * Created by The eXo Platform SAS Author : Tuan Nguyen
 * tuan08@users.sourceforge.net Sep 17, 2005
 */
abstract public class ExecutionUnit extends BaseComponentPlugin {
  private ExecutionUnit next_;

  public void addExecutionUnit(ExecutionUnit next) {
    if (next_ == null)
      next_ = next;
    else
      next_.addExecutionUnit(next);
  }

  public ExecutionUnit getNextUnit() {
    return next_;
  }

  abstract public Object execute(ExecutionContext context) throws Throwable;
}
