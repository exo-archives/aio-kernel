package org.exoplatform.services.naming;

import java.util.Enumeration;
import java.util.List;

import javax.naming.CompositeName;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.Name;

import junit.framework.TestCase;

import org.exoplatform.container.StandaloneContainer;

/**
 * Created by The eXo Platform SAS .<br/> Prerequisites: default-context-factory
 * = org.exoplatform.services.naming.impl.SimpleContextFactory
 * 
 * @author <a href="mailto:geaz@users.sourceforge.net">Gennady Azarenkov </a>
 * @version $Id: InitialContextTest.java 5655 2006-05-22 14:19:41Z geaz $
 */
public class InitialContextTest extends TestCase {

  private static String       TEST_CONTEXT_FACTORY = "org.exoplatform.services.naming.SimpleContextFactory";

  private StandaloneContainer container;

  public void setUp() throws Exception {

    StandaloneContainer.setConfigurationPath("src/test/java/conf/standalone/test-configuration.xml");

    container = StandaloneContainer.getInstance();
  }

  public void testConfig() throws Exception {

    InitialContextInitializer initializer = (InitialContextInitializer) container.getComponentInstanceOfType(InitialContextInitializer.class);

    assertNotNull(initializer);

    assertNotNull(initializer.getDefaultContextFactory());

    assertEquals(TEST_CONTEXT_FACTORY, initializer.getDefaultContextFactory());

    List plugins = (List) initializer.getPlugins();

    assertFalse("No plugins configured", plugins.isEmpty());

    assertTrue("Plugin is not BindReferencePlugin type",
               plugins.get(0) instanceof BindReferencePlugin);

    BindReferencePlugin plugin = (BindReferencePlugin) plugins.get(0);

    assertNotNull(plugin.getBindName());
    assertNotNull(plugin.getReference());

  }

  public void testGetContext() throws Exception {
    assertNotNull(System.getProperty(Context.INITIAL_CONTEXT_FACTORY));
    InitialContext ctx = new InitialContext();
    assertNotNull(ctx);
    ctx.bind("test", "test");
    assertEquals("test", ctx.lookup("test"));
  }

  public void testCompositeNameUsing() throws Exception {
    Name name = new CompositeName("java:comp/env/jdbc/jcr");
    System.out.println("NAME ---- " + name.get(0) + " " + name.getPrefix(1) + " "
        + name.getSuffix(1) + " " + name.getPrefix(0) + " " + name.getSuffix(0));
    Enumeration en = name.getAll();
    while (en.hasMoreElements()) {
      System.out.println("---- " + en.nextElement());
    }
  }

}
