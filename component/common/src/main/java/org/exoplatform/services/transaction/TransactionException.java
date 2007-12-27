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
package org.exoplatform.services.transaction;

/**
 * Created by The eXo Platform SAS.
 * @author <a href="mailto:gennady.azarenkov@exoplatform.com">Gennady Azarenkov</a>
 * @version $Id: TransactionException.java 5987 2006-06-05 10:14:23Z geaz $
 */

public class TransactionException extends Exception {

  public TransactionException() {
    super();
  }

  public TransactionException(String arg0) {
    super(arg0);
  }

  public TransactionException(String arg0, Throwable arg1) {
    super(arg0, arg1);
  }

  public TransactionException(Throwable arg0) {
    super(arg0);
  }

}
