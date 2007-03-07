/**
 * Copyright 2001-2006 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 */

package org.exoplatform.services.transaction.impl.jotm;

import java.rmi.RemoteException;

import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.Transaction;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import org.apache.commons.logging.Log;
import org.exoplatform.container.xml.InitParams;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.naming.InitialContextInitializer;
import org.exoplatform.services.transaction.TransactionService;
import org.objectweb.jotm.Current;
import org.objectweb.jotm.TransactionFactory;
import org.objectweb.jotm.TransactionFactoryImpl;
import org.objectweb.jotm.XidImpl;
import org.objectweb.transaction.jta.ResourceManagerEvent;


/**
 * Created by The eXo Platform SARL        .<br/>
 * JOTM based implementation of TransactionService
 * @author <a href="mailto:gennady.azarenkov@exoplatform.com">Gennady Azarenkov</a>
 * @version $Id: $
 */
 
public class TransactionServiceJotmImpl implements TransactionService {
  
  protected static Log log = ExoLogger.getLogger("transaction.TransactionServiceJotmImpl");
  
  private Current current;
  
  public TransactionServiceJotmImpl(InitialContextInitializer initializer, 
      InitParams params) throws RemoteException {
    current = Current.getCurrent();
    if(current == null) {
      TransactionFactory tm = new TransactionFactoryImpl();
      current = new Current(tm);
      
      // Change the timeout only if JOTM is not initialized yet
      if(params != null && params.getValueParam("timeout") != null) {
        int t = Integer.parseInt(params.getValueParam("timeout").getValue()); 
        current.setDefaultTimeout(t);
      }
    } else {
      log.info("Use externally initialized JOTM: "+current);
    }
  }

  /* (non-Javadoc)
   * @see org.exoplatform.services.transaction.TransactionService#getTransactionManager()
   */
  public TransactionManager getTransactionManager() {
    return current;
  }

  /* (non-Javadoc)
   * @see org.exoplatform.services.transaction.TransactionService#getUserTransaction()
   */
  public UserTransaction getUserTransaction() {
    return current;
  }
  
  /* (non-Javadoc)
   * @see org.exoplatform.services.transaction.TransactionService#enlistResource(javax.transaction.xa.XAResource)
   */
  public void enlistResource(XAResource xares) throws RollbackException, SystemException {
    Transaction tx = getTransactionManager().getTransaction();
    if(tx != null)
      current.getTransaction().enlistResource(xares);
    else
      current.connectionOpened((ResourceManagerEvent)xares);
  }

  /* (non-Javadoc)
   * @see org.exoplatform.services.transaction.TransactionService#delistResource(javax.transaction.xa.XAResource)
   */
  public void delistResource(XAResource xares) throws RollbackException, SystemException {
    Transaction tx = getTransactionManager().getTransaction();
    if(tx != null)
      current.getTransaction().delistResource(xares, XAResource.TMNOFLAGS);
    else
      current.connectionClosed((ResourceManagerEvent)xares);
  }

  /* (non-Javadoc)
   * @see org.exoplatform.services.transaction.TransactionService#createXid()
   */
  public Xid createXid() {
    return new XidImpl();
  }
  
  /* (non-Javadoc)
   * @see org.exoplatform.services.transaction.TransactionService#getDefaultTimeout()
   */
  public int getDefaultTimeout() {
    return current.getDefaultTimeout();
  }

  /* (non-Javadoc)
   * @see org.exoplatform.services.transaction.TransactionService#setTransactionTimeout(int)
   */
  public void setTransactionTimeout(int seconds) throws SystemException {
    current.setTransactionTimeout(seconds);
  }

}
