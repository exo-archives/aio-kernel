/**
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 */

package org.exoplatform.services.naming;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.spi.InitialContextFactory;

/**
 * Created by The eXo Platform SARL .
 * 
 * @author <a href="mailto:gennady.azarenkov@exoplatform.com">Gennady Azarenkov</a>
 * @version $Id: SimpleContextFactory.java 5340 2006-04-30 12:51:48Z geaz $
 */

public class SimpleContextFactory implements InitialContextFactory {

  private Context context;
  
  public SimpleContextFactory() {
    context = new SimpleContext(); 
  }

  public Context getInitialContext(Hashtable<?, ?> env) throws NamingException {
    return context;
  }

}
