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

import org.apache.commons.chain.Catalog;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;

import org.exoplatform.container.StandaloneContainer;
import org.exoplatform.services.command.impl.CommandService;

/**
 * Created by The eXo Platform SAS .
 * 
 * @author <a href="mailto:geaz@users.sourceforge.net">Gennady Azarenkov </a>
 * @version $Id: CommandServiceTest.java 9296 2006-10-04 13:13:29Z geaz $
 */
public class CommandServiceTest extends TestCase {

  private static final String IS = "<catalog>"
                                     + "<command name='StrCommand'"
                                     + " className='org.exoplatform.services.command.TestCommand1'/>"
                                     + "</catalog>";

  private StandaloneContainer container;

  public void setUp() throws Exception {

    StandaloneContainer.setConfigurationPath("src/java/conf/standalone/test-configuration.xml");

    container = StandaloneContainer.getInstance();
  }

  public void testPluginConf() throws Exception {

    CommandService cservice = (CommandService) container.getComponentInstanceOfType(CommandService.class);
    assertNotNull(cservice);

    // preconfigured commands
    assertTrue(cservice.getCatalog().getNames().hasNext());
    assertNotNull(cservice.getCatalog().getNames().next());

  }

  public void testStringConf() throws Exception {
    CommandService cservice = (CommandService) container.getComponentInstanceOfType(CommandService.class);
    Catalog c = cservice.getCatalog();

    assertNull(c.getCommand("StrCommand"));
    cservice.putCatalog(new ByteArrayInputStream(IS.getBytes()));
    Catalog c1 = cservice.getCatalog();
    assertNotNull(c1.getCommand("StrCommand"));

  }

  public void testInitWithFile() throws Exception {
    CommandService cservice = (CommandService) container.getComponentInstanceOfType(CommandService.class);
    cservice.putCatalog(getClass().getResourceAsStream("/conf/test-commands3.xml"));
    assertTrue(cservice.getCatalogNames().hasNext());
    Catalog c1 = cservice.getCatalog("catalog1");
    assertNotNull(c1.getCommand("Command2"));

  }

  public void testExcecute() throws Exception {

    CommandService cservice = (CommandService) container.getComponentInstanceOfType(CommandService.class);
    Iterator cs = cservice.getCatalog().getNames();
    String c1 = (String) cs.next();
    String c2 = (String) cs.next();

    Catalog c = cservice.getCatalog();

    Context ctx = new ContextBase();
    ctx.put("test", Integer.valueOf(0));
    c.getCommand(c1).execute(ctx);
    c.getCommand(c2).execute(ctx);
    assertEquals(3, ((Integer) ctx.get("test")).intValue());

  }

}
