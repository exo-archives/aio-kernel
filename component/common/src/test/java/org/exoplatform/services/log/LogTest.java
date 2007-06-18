
package org.exoplatform.services.log;

import junit.framework.TestCase;

import org.exoplatform.container.StandaloneContainer;
import org.exoplatform.services.log.impl.ExoLog;



/**
 * Created by The eXo Platform SARL .
 * 
 * @author <a href="mailto:geaz@users.sourceforge.net">Gennady Azarenkov </a>
 * @version $Id: LogTest.java 5332 2006-04-29 18:32:44Z geaz $
 */
public class LogTest extends TestCase {

  private StandaloneContainer container;
  
  public void setUp() throws Exception {
    
    StandaloneContainer.setConfigurationPath("src/test/java/conf/standalone/test-configuration.xml");
  	
    container = StandaloneContainer.getInstance();
  }
  
  public void testLog() throws Exception {

    ExoLogger.getLogger(this.getClass()).debug("debug message");

    ExoLogger.getLogger(this.getClass().getName()).warn("warn message");
    
    ExoLogger.getLogger(this.getClass()).error("error message");

    ExoLogger.getLogger(this.getClass()).fatal("fatal message");

    assertEquals(4, ExoLog.getLogBuffer().size());

    assertEquals(2, ExoLog.getErrorBuffer().size());

    LogMessage m = ((LogMessage)ExoLog.getLogBuffer().get(2));
    
    assertEquals("error message", m.getMessage());
    
  }



}
