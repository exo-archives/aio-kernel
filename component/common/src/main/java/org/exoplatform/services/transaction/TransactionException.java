/**
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 */

package org.exoplatform.services.transaction;

/**
 * Created by The eXo Platform SARL        .
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
