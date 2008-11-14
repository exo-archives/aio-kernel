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
package org.exoplatform.commons.utils;

import junit.framework.TestCase;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class TestPropertyManager extends TestCase {

  public void testDevelopping() {
    _testDeveloppingIsNull();
    _testDeveloppingIsTrue();
    _testDeveloppingIsFalse();
    _testDeveloppingIsMaybe();
  }

  private void _testDeveloppingIsNull() {
    assertNull(System.getProperty(PropertyManager.DEVELOPING));
    PropertyManager.refresh();
    assertTrue(PropertyManager.getUseCache());
    assertFalse(PropertyManager.isDevelopping());

    //
    assertNull(PropertyManager.getProperty("foo"));
    System.setProperty("foo", "bar");
    assertEquals("bar", PropertyManager.getProperty("foo"));
    System.setProperty("foo", "juu");
    PropertyManager.refresh();
    assertEquals("juu", PropertyManager.getProperty("foo"));
    System.setProperty("foo", "daa");
    assertEquals("juu", PropertyManager.getProperty("foo"));
    PropertyManager.setProperty("foo", "daa");
    assertEquals("daa", PropertyManager.getProperty("foo"));
    assertEquals("daa", System.getProperty("foo"));
  }

  private void _testDeveloppingIsTrue() {
    System.setProperty(PropertyManager.DEVELOPING, "true");
    PropertyManager.refresh();
    assertFalse(PropertyManager.getUseCache());
    assertTrue(PropertyManager.isDevelopping());

    //
    System.setProperty("foo", "bar");
    assertEquals("bar", PropertyManager.getProperty("foo"));
    System.setProperty("foo", "juu");
    PropertyManager.refresh();
    assertEquals("juu", PropertyManager.getProperty("foo"));
    System.setProperty("foo", "daa");
    assertEquals("daa", PropertyManager.getProperty("foo"));
    PropertyManager.setProperty("foo", "daa");
    assertEquals("daa", PropertyManager.getProperty("foo"));
    assertEquals("daa", System.getProperty("foo"));
  }

  private void _testDeveloppingIsFalse() {
    System.setProperty(PropertyManager.DEVELOPING, "false");
    PropertyManager.refresh();
    assertTrue(PropertyManager.getUseCache());
    assertFalse(PropertyManager.isDevelopping());

    //
    System.setProperty("foo", "bar");
    assertEquals("bar", PropertyManager.getProperty("foo"));
    System.setProperty("foo", "juu");
    PropertyManager.refresh();
    assertEquals("juu", PropertyManager.getProperty("foo"));
    System.setProperty("foo", "daa");
    assertEquals("juu", PropertyManager.getProperty("foo"));
    PropertyManager.setProperty("foo", "daa");
    assertEquals("daa", PropertyManager.getProperty("foo"));
    assertEquals("daa", System.getProperty("foo"));
  }

  private void _testDeveloppingIsMaybe() {
    System.setProperty(PropertyManager.DEVELOPING, "maybe");
    PropertyManager.refresh();
    assertTrue(PropertyManager.getUseCache());
    assertFalse(PropertyManager.isDevelopping());

    //
    System.setProperty("foo", "bar");
    assertEquals("bar", PropertyManager.getProperty("foo"));
    System.setProperty("foo", "juu");
    PropertyManager.refresh();
    assertEquals("juu", PropertyManager.getProperty("foo"));
    System.setProperty("foo", "daa");
    assertEquals("juu", PropertyManager.getProperty("foo"));
    PropertyManager.setProperty("foo", "daa");
    assertEquals("daa", PropertyManager.getProperty("foo"));
    assertEquals("daa", System.getProperty("foo"));
  }
}
