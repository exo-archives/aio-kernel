/**
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 */

package org.exoplatform.services.log;

import java.util.Properties;

/**
 * Created by The eXo Platform SARL        . 
 * <br/> An interface for log system configuration
 * @author <a href="mailto:gennady.azarenkov@exoplatform.com">Gennady Azarenkov</a>
 * @version $Id: LogConfigurator.java 5332 2006-04-29 18:32:44Z geaz $
 */

public interface LogConfigurator {
  /**
   * Configures a concrete log system implementation using properties list
   * @param properties
   */
  void configure(Properties properties);
  
  /**
   * @return current properties
   */
  Properties getProperties();
}

