/**
 * Copyright 2001-2006 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 */

package org.exoplatform.services.transaction;

import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

/**
 * Created by The eXo Platform SARL        .<br/>
 * The transaction service 
 * @author <a href="mailto:gennady.azarenkov@exoplatform.com">Gennady Azarenkov</a>
 * @version $Id: $
 */

public interface TransactionService {
  
  /**
   * @return TransactionManager  
   */
  TransactionManager getTransactionManager();
  
  /**
   * @return UserTransaction 
   */
  UserTransaction getUserTransaction();
  
  /**
   * @return default timeout in seconds
   */
  int getDefaultTimeout();
  
  /**
   * sets timeout in seconds
   * @param seconds 
   * @throws SystemException
   */
  void setTransactionTimeout(int seconds) throws SystemException;
  
  /**
   * enlists XA resource in transaction manager
   * @param xares XAResource
   * @throws RollbackException
   * @throws SystemException
   */
  void enlistResource(XAResource xares) throws RollbackException, SystemException;
  
  /**
   * delists XA resource from transaction manager
   * @param xares XAResource
   * @throws RollbackException
   * @throws SystemException
   */
  void delistResource(XAResource xares) throws RollbackException, SystemException;
  
  /**
   * creates unique XA transaction identifier
   * @return Xid
   */
  Xid createXid();
  
}
