/*
 * Copyright (C) 2003-2007 eXo Platform SAS.
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
package org.exoplatform.services.command;

import java.io.ByteArrayInputStream;
import java.util.Iterator;

import junit.framework.TestCase;

import org.exoplatform.container.StandaloneContainer;
import org.exoplatform.services.command.impl.CommandService;



/**
 * Created by The eXo Platform SAS .
 * 
 * @author <a href="mailto:geaz@users.sourceforge.net">Gennady Azarenkov </a>
 * @version $Id:$
 */
public class MultiConfigServiceTest extends TestCase {

  private StandaloneContainer container;
  
  private static final String IS = "<catalog name='fromput'>"+
  "<command name='put'"+
  " className='org.exoplatform.services.command.TestCommand1'/>"+
  "</catalog>";

  
  // amount of commands configured in multiple plugins
  // CHECK it if change test-multi-configuration.xml !!!
  private final int NUMBER_OF_COMMANDS_IN_DEF = 4;
  
  private final int NUMBER_OF_COMMANDS_IN_CATALOG1 = 2; 

  
  public void setUp() throws Exception {
    
    StandaloneContainer.setConfigurationPath("src/java/conf/standalone/test-multi-configuration.xml");
  	
    container = StandaloneContainer.getInstance();
  }
  
  /**
   * Tests if multiple configuration is allowed 
   * @throws Exception
   */
  public void testMultiConfig() throws Exception {
    
    CommandService cservice = (CommandService)container.getComponentInstanceOfType(CommandService.class);
    assertNotNull(cservice);

    assertTrue(cservice.getCatalogNames().hasNext());
    
    Iterator commands = cservice.getCatalog().getNames();
    int cnt=0;
    while(commands.hasNext()) {
      System.out.println(" command >>> "+commands.next());
      cnt++;
    }
    
    assertEquals(NUMBER_OF_COMMANDS_IN_DEF, cnt);

    
    commands = cservice.getCatalog("catalog1").getNames();
    cnt=0;
    while(commands.hasNext()) {
      System.out.println(" command(1) >>> "+commands.next());
      cnt++;
    }
    
    assertEquals(NUMBER_OF_COMMANDS_IN_CATALOG1, cnt);

  }

  public void testIfPutCatalogDoesNotRemoveCommands() throws Exception {
    
    CommandService cservice = (CommandService)container.getComponentInstanceOfType(CommandService.class);
    assertNotNull(cservice);

    assertTrue(cservice.getCatalogNames().hasNext());
    Iterator commands = cservice.getCatalog().getNames();
    int cnt=0;
    while(commands.hasNext()) {
      System.out.println(" command before >>> "+commands.next());
      cnt++;
    }
  
    ByteArrayInputStream is = new ByteArrayInputStream(IS.getBytes());
    
    cservice.putCatalog(is);

    assertTrue(cservice.getCatalogNames().hasNext());
    commands = cservice.getCatalog().getNames();
    cnt=0;
    while(commands.hasNext()) {
      System.out.println(" command after >>> "+commands.next());
      cnt++;
    }

    commands = cservice.getCatalog("fromput").getNames();
    cnt=0;
    while(commands.hasNext()) {
      System.out.println(" command fromput >>> "+commands.next());
      cnt++;
    }

  }


}
