/**
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 */

package org.exoplatform.services.transaction;

import javax.transaction.RollbackException;
import javax.transaction.Status;
import javax.transaction.Transaction;
import javax.transaction.xa.XAException;
import javax.transaction.xa.XAResource;
import javax.transaction.xa.Xid;

import org.apache.commons.logging.Log;
import org.exoplatform.services.log.ExoLogger;
import org.objectweb.transaction.jta.ResourceManagerEvent;
/**
 * Created by The eXo Platform SARL        .
 * @author <a href="mailto:gennady.azarenkov@exoplatform.com">Gennady Azarenkov</a>
 * @version $Id: $
 */

public class XAResourceTestImpl implements XAResource, ResourceManagerEvent {
  
  private static Log log = ExoLogger.getLogger("tx.TestXAResource");
  private int timeout = 5;
  //private transient TransactionService ts;

  private int oldFlag;

  private int flag = oldFlag = 0;
  
//  public XAResourceTestImpl(TransactionService ts)  
//  throws RollbackException, SystemException {
//    this.ts = ts;
//    ts.enlistResource(this);
//  }

  public void commit(Xid arg0, boolean arg1) throws XAException {
    oldFlag = flag;
    log.info("Commit "+arg0);
  }

  public void end(Xid arg0, int arg1) throws XAException {
    log.info("End "+arg0);
  }

  public void forget(Xid arg0) throws XAException {
    log.info("Forget "+arg0);
  }

  public int getTransactionTimeout() throws XAException {
    return timeout;
  }

  public boolean isSameRM(XAResource arg0) throws XAException {
    return false;
  }

  public int prepare(Xid arg0) throws XAException {
    log.info("Prepare "+arg0);
    return Status.STATUS_PREPARED;
  }

  public Xid[] recover(int arg0) throws XAException {
    log.info("recover "+arg0);
    return null;
  }

  public void rollback(Xid arg0) throws XAException {
    flag = oldFlag;
    log.info("Rollback "+arg0);
  }

  public boolean setTransactionTimeout(int arg0) throws XAException {
    this.timeout = arg0;
    return true;
  }

  public void start(Xid arg0, int arg1) throws XAException {
    log.info("Start "+arg0);
  }

  public int getFlag() {
    return flag;
  }

  public void setFlag(int flag) {
    this.flag = flag;
  }

  /* (non-Javadoc)
   * @see org.objectweb.transaction.jta.ResourceManagerEvent#enlistConnection(javax.transaction.Transaction)
   */
  public void enlistConnection(Transaction transaction) throws javax.transaction.SystemException  {
    try {
      transaction.enlistResource(this);
    } catch (IllegalStateException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (RollbackException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } 
  }

  public int getOldFlag() {
    return oldFlag;
  }



}
