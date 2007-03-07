/**
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 */

package org.exoplatform.services.transaction;


/**
 * Created by The eXo Platform SARL        .<br/>
 * Internal resource manager for support transaction
 * @author <a href="mailto:gennady.azarenkov@exoplatform.com">Gennady Azarenkov</a>
 * @version $Id: TransactionResource.java 6853 2006-07-07 11:41:24Z geaz $
 */

public interface TransactionResource {
  
  /**
   * called when transaction is starting (by javax.transaction.xa.XAResource#start(javax.transaction.xa.Xid, int))
   * @throws TransactionException
   */
  void start() throws TransactionException;
  
  /**
   * called when transaction is committing (by javax.transaction.xa.XAResource#commit(javax.transaction.xa.Xid, boolean))
   * @throws TransactionException
   */
  void commit() throws TransactionException;
 
  /**
   * called when transaction is rolling back (by javax.transaction.xa.XAResource#rollback(javax.transaction.xa.Xid))
   * @throws TransactionException
   */
  void rollback() throws TransactionException;

}
