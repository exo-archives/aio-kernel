/**
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 */

package org.exoplatform.services.command;

import java.io.ByteArrayInputStream;
import java.util.Iterator;

import junit.framework.TestCase;

import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.exoplatform.container.StandaloneContainer;
import org.exoplatform.services.command.impl.CommandService;



/**
 * Created by The eXo Platform SARL .
 * 
 * @author <a href="mailto:geaz@users.sourceforge.net">Gennady Azarenkov </a>
 * @version $Id: CommandServiceTest.java 9296 2006-10-04 13:13:29Z geaz $
 */
public class CommandServiceTest extends TestCase {
  
  private static final String IS = "<catalog>"+
                       "<command name='StrCommand'"+
                       " className='org.exoplatform.services.command.TestCommand1'/>"+
                       "</catalog>";

  private StandaloneContainer container;
  
  
  public void setUp() throws Exception {
    
    StandaloneContainer.setConfigurationPath("src/java/conf/standalone/test-configuration.xml");
  	
    container = StandaloneContainer.getInstance();
  }
  
  public void testPluginConf() throws Exception {
    
    CommandService cservice = (CommandService)container.getComponentInstanceOfType(CommandService.class);
    assertNotNull(cservice);
    
    // preconfigured commands
    assertTrue(cservice.getCatalog().getNames().hasNext());
    assertNotNull(cservice.getCatalog().getNames().next());
    
  }
  
  public void testStringConf() throws Exception {
    CommandService cservice = (CommandService)container.getComponentInstanceOfType(CommandService.class);
    Catalog c = cservice.getCatalog();

    assertNull(c.getCommand("StrCommand"));
    cservice.putCatalog(new ByteArrayInputStream(IS.getBytes()));
    Catalog c1=cservice.getCatalog();
    assertNotNull(c1.getCommand("StrCommand"));
    
    
  }
  
  public void testInitWithFile() throws Exception {
    CommandService cservice = (CommandService)container.getComponentInstanceOfType(CommandService.class);
    cservice.putCatalog(getClass().getResourceAsStream("/conf/test-commands3.xml"));
    assertTrue(cservice.getCatalogNames().hasNext());
    Catalog c1=cservice.getCatalog("catalog1");
    assertNotNull(c1.getCommand("Command2"));

  }  
  
  public void testExcecute() throws Exception {
    
    CommandService cservice = (CommandService)container.getComponentInstanceOfType(CommandService.class);
    Iterator cs = cservice.getCatalog().getNames();
    String c1 = (String)cs.next();
    String c2 = (String)cs.next();
    
    Catalog c = cservice.getCatalog();
    
    Context ctx = new ContextBase();
    ctx.put("test", Integer.valueOf(0));
    c.getCommand(c1).execute(ctx);
    c.getCommand(c2).execute(ctx);
    assertEquals(3, ((Integer)ctx.get("test")).intValue());
    
  }

}
