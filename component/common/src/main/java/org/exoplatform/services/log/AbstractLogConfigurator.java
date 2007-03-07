/**
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 */

package org.exoplatform.services.log;

import java.util.Properties;

/**
 * Created by The eXo Platform SARL        .
 * @author <a href="mailto:gennady.azarenkov@exoplatform.com">Gennady Azarenkov</a>
 * @version $Id: AbstractLogConfigurator.java 5332 2006-04-29 18:32:44Z geaz $
 */

public abstract class AbstractLogConfigurator implements LogConfigurator {

  protected Properties properties;
  
  public final Properties getProperties() {
    return properties;
  }

}
