package org.exoplatform.services.log.impl;


import org.apache.commons.logging.Log;
import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.log.LogService;
import org.exoplatform.services.log.LogUtil;
import org.exoplatform.test.BasicTestCase;

/**
 * Copyright 2001-2003 The eXo platform SARL All rights reserved.
 * Please look at license.txt in info directory for more license detail.
 **/

/**
 * Created by the Exo Development team.
 * Author : Mestrallet Benjamin
 *          benjmestrallet@users.sourceforge.net
 * Date: 14 nov. 2003
 * Time: 22:31:20
 */
public class TestLogService extends BasicTestCase {

  LogService service_ ;

  public TestLogService(String name) {
    super(name);
  }

	protected String getDescription() {
		return "Log service test";
	}

	public void setUp() throws Exception {
    setTestNumber(1) ;
    PortalContainer manager  = PortalContainer.getInstance();
    service_ = (LogService) manager.getComponentInstanceOfType(LogService.class) ;    
  }

  public void tearDown() throws Exception {
  }

	public void testLogService() throws Exception {
    Log log = service_.getLog("org.exoplatform.services.log");
    Log sublog = service_.getLog("org.exoplatform.services.log.sublog");
		assertEquals(LogService.DEBUG, service_.getLogLevel("org.exoplatform.services.log"));
		assertEquals(LogService.DEBUG, service_.getLogLevel("org.exoplatform.services.log.sublog"));
		
    log.debug("debug: test  category 'org.exoplatform.services.log'");
    log.info("info:   test category 'org.exoplatform.services.log'");
    log.warn("warn:   test category 'org.exoplatform.services.log'");
    log.error("error:   test category 'org.exoplatform.services.log'");
    
    service_.setLogLevel("org.exoplatform.services.log", LogService.ERROR, true) ;
    assertEquals(LogService.ERROR, service_.getLogLevel("org.exoplatform.services.log"));
		assertEquals(LogService.ERROR, service_.getLogLevel("org.exoplatform.services.log.sublog"));
		
		LogUtil.debug("This is a test debug") ;
		LogUtil.info("This is a test inof") ;
		LogUtil.warn("This is a test warn") ;
		LogUtil.error("This is a test error") ;

	}
}