/**
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 */

package org.exoplatform.services.log.impl;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.exoplatform.services.log.AbstractLogConfigurator;

/**
 * Created by The eXo Platform SARL        .
 * 
 * <br/> Simple commons configurator. See org.apache.commons.logging.impl.SimpleLog javadoc for details.
 * 
 * @author <a href="mailto:gennady.azarenkov@exoplatform.com">Gennady Azarenkov</a>
 * @version $Id: SimpleLogConfigurator.java 5332 2006-04-29 18:32:44Z geaz $
 */

public class SimpleLogConfigurator extends AbstractLogConfigurator {

  public void configure(Properties properties) {
    
    // it is ok in AS env, but maven test throws something not understandable  
    //System.setProperties(properties);
    
    for(Iterator it = properties.entrySet().iterator(); it.hasNext();) {
      Map.Entry entry = (Map.Entry)it.next();
      System.setProperty((String)entry.getKey(), (String)entry.getValue());
    }
    
    this.properties = properties;
  }
}
