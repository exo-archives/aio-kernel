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
package org.exoplatform.services.transaction.jcr;

import junit.framework.TestCase;
import org.exoplatform.services.transaction.impl.jotm.TransactionServiceJotmImpl;
import org.exoplatform.services.transaction.TransactionService;

/**
 * Integration test between the behavior of an XASession implementing the ExoResource interface
 * and the JOTM implementation.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class TestXASessionIntegration extends TestCase {

  /** . */
  private TransactionService txservice;

  @Override
  protected void setUp() throws Exception {
    txservice = new TransactionServiceJotmImpl(null, null);
  }

  public void testLoginLogout() throws Exception {
    XASession session = new XASession(txservice);
    txservice.enlistResource(session);
    txservice.delistResource(session);
  }

  public void testLogout() throws Exception {
    XASession session = new XASession(txservice);
    txservice.delistResource(session);
  }
}
