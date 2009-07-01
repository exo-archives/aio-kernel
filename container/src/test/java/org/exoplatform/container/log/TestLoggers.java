/*
 * Copyright (C) 2003-2009 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package org.exoplatform.container.log;

import java.util.Properties;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.log.LogConfigurationInitializer;
import org.exoplatform.test.BasicTestCase;

/**
 * Created by The eXo Platform SAS
 * 
 * @author <a href="work.visor.ck@gmail.com">Dmytro Katayev</a> Jun 24, 2009
 */
public class TestLoggers extends BasicTestCase {
    
  private final String logger = "org.slf4j.Logger";
  
  
  public void _testExoLog() throws Exception {
    long started = System.currentTimeMillis();
    Log log = ExoLogger.getLogger(TestLoggers.class);

    String confClass = "org.exoplatform.services.log.impl.Log4JConfigurator";

    Properties props = new Properties();

    props.put("log4j.rootLogger", "INFO, stdout, file");

    props.put("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
    props.put("log4j.appender.stdout.threshold", "DEBUG");

    props.put("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
    props.put("log4j.appender.stdout.layout.ConversionPattern",
              "%d{dd.MM.yyyy HH:mm:ss} *%-5p* [%t] %c{1}: %m (%F, line %L) %n");

    props.put("log4j.appender.file", "org.apache.log4j.FileAppender");
    props.put("log4j.appender.file.File", "target/l4j_info.log");

    props.put("log4j.appender.file.layout", "org.apache.log4j.PatternLayout");
    props.put("log4j.appender.file.layout.ConversionPattern",
              "%d{dd.MM.yyyy HH:mm:ss} *%-5p* [%t] %c{1}: %m (%F, line %L) %n");

    props.put("log4j.category.jcr.FileCleaner", "DEBUG");

    LogConfigurationInitializer initializer = new LogConfigurationInitializer(logger,
                                                                              confClass,
                                                                              props);
    
    for (int i = 0; i< 10000; i++) {
      log.info("Info " + i);
    }
    
    long finished = System.currentTimeMillis();
    
    System.out.println(started);
    System.out.println(finished);
    
  }
  

  public void _testLog4j() throws Exception {

    Log log = ExoLogger.getLogger(TestLoggers.class);

    String confClass = "org.exoplatform.services.log.impl.Log4JConfigurator";

    Properties props = new Properties();

    props.put("log4j.rootLogger", "INFO, stdout, file");

    props.put("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
    props.put("log4j.appender.stdout.threshold", "DEBUG");

    props.put("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
    props.put("log4j.appender.stdout.layout.ConversionPattern",
              "%d{dd.MM.yyyy HH:mm:ss} *%-5p* [%t] %c{1}: %m (%F, line %L) %n");

    props.put("log4j.appender.file", "org.apache.log4j.FileAppender");
    props.put("log4j.appender.file.File", "target/l4j_info.log");

    props.put("log4j.appender.file.layout", "org.apache.log4j.PatternLayout");
    props.put("log4j.appender.file.layout.ConversionPattern",
              "%d{dd.MM.yyyy HH:mm:ss} *%-5p* [%t] %c{1}: %m (%F, line %L) %n");

    props.put("log4j.category.jcr.FileCleaner", "DEBUG");

    LogConfigurationInitializer initializer = new LogConfigurationInitializer(logger,
                                                                              confClass,
                                                                              props);
    log.info("LOG4J Tests");
    logOut(log);

    initializer.setProperty("log4j.rootLogger", "DEBUG, stdout, file");
    initializer.setProperty("log4j.appender.file.File", "target/l4j_debg.log");

    logOut(log);

  }

  public void _testLog4jContainer() throws Exception {

    PortalContainer container = PortalContainer.getInstance();
    Log log = ExoLogger.getLogger(TestLoggers.class);

    log.info("Log4j Container Tests");
    logOut(log);

  }

  /**
   * To launch this test: 1. remove Log4jConfogurator from
   * org.exoplatform.services.log. 2. remove log4j dependency from
   * exo.kernel.commons. 3. replace slf4j-log4j12 with slf4j-jcl in
   * exo.kernel.commons dependencies.
   */
  public void _testJCLLog() throws Exception {

    String confClass = "org.exoplatform.services.log.impl.Jdk14Configurator";

    Properties props = new Properties();

    props.put("handlers", "java.util.logging.ConsoleHandler,java.util.logging.FileHandler");
    props.put(".level", "INFO");
    props.put("java.util.logging.ConsoleHandler.level", "ALL");
    props.put("java.util.logging.FileHandler.pattern", "./target/java%u.log");
    props.put("java.util.logging.FileHandler.formatter", "java.util.logging.SimpleFormatter");

    LogConfigurationInitializer initializer = new LogConfigurationInitializer(logger,
                                                                              confClass,
                                                                              props);
    Log log = ExoLogger.getLogger(TestLoggers.class);

    log.info("JCL Tests");
    logOut(log);

  }

  /**
   * To launch this test: 
   * 1. remove Log4jConfogurator from org.exoplatform.services.log. 
   * 2. remove log4j dependency from exo.kernel.commons. 
   * 3. replace slf4j-log4j12 with slf4j-jcl in exo.kernel.commons dependencies. 
   * 4. Comment Log4J logger configuration and uncomment JDK14 logger configuration in conf.portal/test-configuration.xml.
   */
  public void _testJCLContainer() throws Exception {

    PortalContainer container = PortalContainer.getInstance();
    Log log = ExoLogger.getLogger(TestLoggers.class);

    log.info("JCL Container Tests");
    logOut(log);

  }

  private void logOut(Log log) {
    log.debug(log.getClass().getName() + ": \tDEBUG");
    log.error(log.getClass().getName() + ": \tERROR");
    log.info(log.getClass().getName() + ": \tINFO");
    log.trace(log.getClass().getName() + ": \tTRACE");
    log.warn(log.getClass().getName() + ": \tWARNING\n");
  }

}
