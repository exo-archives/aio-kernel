/**
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 */

package org.exoplatform.services.log.impl;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.exoplatform.services.log.AbstractLogConfigurator;
/**
 * Created by The eXo Platform SARL        . 
 * 
 * <br/> Log4J configurator. The properties are the same as log4j.properties file's name value pairs.
 *  
 * @author <a href="mailto:gennady.azarenkov@exoplatform.com">Gennady Azarenkov</a>
 * @version $Id: Log4JConfigurator.java 5332 2006-04-29 18:32:44Z geaz $
 */

public class Log4JConfigurator extends AbstractLogConfigurator {

  public void configure(Properties properties) {
    PropertyConfigurator.configure(properties);
    this.properties = properties;
  }

}
