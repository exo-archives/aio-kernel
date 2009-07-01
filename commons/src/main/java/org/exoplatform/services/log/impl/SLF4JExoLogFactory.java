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
package org.exoplatform.services.log.impl;

import org.exoplatform.services.log.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.spi.LocationAwareLogger;

/**
 * A factory for {@link org.exoplatform.services.log.impl.LocationAwareSLF4JExoLog} and
 * {@link org.exoplatform.services.log.impl.SLF4JExoLog} based on the type of the logger
 * returned by {@link org.slf4j.LoggerFactory} which can be {@link Logger} or {@link org.slf4j.spi.LocationAwareLogger}.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Id$
 */
public class SLF4JExoLogFactory extends AbstractExoLogFactory {

  /**
   * {@inheritDoc}
   */
  protected Log getLogger(String name) {
    Logger slf4jlogger = LoggerFactory.getLogger(name);
    if (slf4jlogger instanceof LocationAwareLogger) {
      return new LocationAwareSLF4JExoLog((LocationAwareLogger)slf4jlogger);
    } else {
      return new SLF4JExoLog(slf4jlogger);
    }
  }
}
