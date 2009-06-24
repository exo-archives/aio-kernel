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

import junit.framework.TestCase;

import org.exoplatform.container.StandaloneContainer;
import org.exoplatform.services.log.LogConfigurationInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by The eXo Platform SAS
 * 
 * @author <a href="work.visor.ck@gmail.com">Dmytro Katayev</a> Jun 24, 2009
 */
public class TestLoggers extends TestCase {

  private final String     logger    = "org.slf4j.Logger";

  private final String     confClass = "org.exoplatform.services.log.impl.Log4JConfigurator";

  private final Properties props     = new Properties();

//  public void testLog4j() throws Exception {
//
//    Logger log = LoggerFactory.getLogger(TestLoggers.class);
//
//    props.put("log4j.rootLogger", "INFO, stdout, file");
//
//    props.put("log4j.appender.stdout", "org.apache.log4j.ConsoleAppender");
//    props.put("log4j.appender.stdout.threshold", "DEBUG");
//
//    props.put("log4j.appender.stdout.layout", "org.apache.log4j.PatternLayout");
//    props.put("log4j.appender.stdout.layout.ConversionPattern",
//              "%d{dd.MM.yyyy HH:mm:ss} *%-5p* [%t] %c{1}: %m (%F, line %L) %n");
//
//    props.put("log4j.appender.file", "org.apache.log4j.FileAppender");
//    props.put("log4j.appender.file.File", "target/l4j_info.log");
//
//    props.put("log4j.appender.file.layout", "org.apache.log4j.PatternLayout");
//    props.put("log4j.appender.file.layout.ConversionPattern",
//              "%d{dd.MM.yyyy HH:mm:ss} *%-5p* [%t] %c{1}: %m (%F, line %L) %n");
//
//    props.put("log4j.category.jcr.FileCleaner", "DEBUG");
//
//    LogConfigurationInitializer initializer = new LogConfigurationInitializer(logger,
//                                                                              confClass,
//                                                                              props);
//    log.info("LOG4J Tests");
//    logOut(log);
//
//    initializer.setProperty("log4j.rootLogger", "DEBUG, stdout, file");
//    initializer.setProperty("log4j.appender.file.File", "target/l4j_debg.log");
//
//    logOut(log);
//
//  }

  public void testLog4jContainer() throws Exception {

    Logger log = LoggerFactory.getLogger(TestLoggers.class);

    StandaloneContainer.addConfigurationPath("src/test/java/conf/standalone/test-configuration.xml");
    
    log.info("Container Tests");
    logOut(log);

  }

  private void logOut(Logger log) {
    log.debug(log.getClass().getName() + ": \tDEBUG");
    log.error(log.getClass().getName() + ": \tERROR");
    log.info(log.getClass().getName() + ": \tINFO");
    log.trace(log.getClass().getName() + ": \tTRACE");
    log.warn(log.getClass().getName() + ": \tWARNING\n");
  }

}
