/**
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 */

package org.exoplatform.services.log.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.LogManager;

import org.exoplatform.services.log.AbstractLogConfigurator;
/**
 * Created by The eXo Platform SARL        .
 * 
 *<br/> JDK's log system configurator. See $JAVA_HOME/jre/lib/logging.properties for details.
 * 
 * @author <a href="mailto:gennady.azarenkov@exoplatform.com">Gennady Azarenkov</a>
 * @version $Id: Jdk14Configurator.java 5332 2006-04-29 18:32:44Z geaz $
 */

public class Jdk14Configurator extends AbstractLogConfigurator {

  public void configure(Properties properties) {
    try {
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      properties.store(baos, null);
      LogManager.getLogManager().readConfiguration(new ByteArrayInputStream(baos.toByteArray()));
    } catch (IOException e) {
      e.printStackTrace();
    }
    this.properties = properties;
  }

}
