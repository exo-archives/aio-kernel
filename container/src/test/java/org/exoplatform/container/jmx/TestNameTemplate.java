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
package org.exoplatform.container.jmx;

import junit.framework.TestCase;
import org.exoplatform.management.jmx.annotations.NameTemplate;

import javax.management.ObjectName;
import javax.management.MalformedObjectNameException;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class TestNameTemplate extends TestCase {

  public void assertCannotBuild(Object o) {
    try {
      ObjectNameBuilder builder = new ObjectNameBuilder(o);
      builder.build();
      fail();
    }
    catch (Exception ignore) {
    }
  }

  public void assertSame(Object o, String value) {
    ObjectName expectedValue;
    try {
      expectedValue = new ObjectName(value);
    }
    catch (MalformedObjectNameException e) {
      throw new AssertionError(e);
    }
    ObjectNameBuilder builder = new ObjectNameBuilder(o);
    assertEquals(expectedValue, builder.build());
  }

  public void testSame() {
    assertSame(new MBean1(), "foo:a=b");
    assertSame(new MBean2(), "foo:a=\"something\"");
    assertSame(new MBean3(), "foo:foo=\"3\"");
  }

  @NameTemplate("foo:a=b")
  public static class MBean1 {
  }

  @NameTemplate("foo:a={B}")
  public static class MBean2 {
    public String getB() {
      return "something";
    }
  }

  @NameTemplate("foo:foo={Foo}")
  public static class MBean3 {
    public Integer getFoo() {
      return 3;
    }
  }

  public void testCannotBuilder() {
    assertCannotBuild(new MBean4());
    assertCannotBuild(new MBean5());
    assertCannotBuild(new MBean6());
    assertCannotBuild(new MBean7());
    assertCannotBuild(new MBean8());
    assertCannotBuild(new MBean9());
    assertCannotBuild(new MBean10());
    assertCannotBuild(new MBean11());
    assertCannotBuild(new MBean12());
  }

  @NameTemplate("foo:a={b}")
  public static class MBean4 {
  }

  @NameTemplate("foo:a={b}")
  public static class MBean5 {
    public void getB() {
    }
  }

  @NameTemplate("foo:a={b}")
  public static class MBean6 {
    public String getB(String s) {
      return "Foo";
    }
  }

  @NameTemplate("foo:a={b}")
  public static class MBean7 {
    public String getB() {
      throw new RuntimeException();
    }
  }

  @NameTemplate("foo:a={b}")
  public static class MBean8 {
    public String getB() {
      return null;
    }
  }

  @NameTemplate("foo:a={b}")
  public static class MBean9 {
    public static String getB() {
      return "Foo";
    }
  }

  @NameTemplate("foo:a=={b}")
  public static class MBean10 {
    public String getB() {
      return "Foo";
    }
  }

  @NameTemplate("foo:a={b}")
  public static class MBean11 {
    protected String getB() {
      return "Foo";
    }
  }

  @NameTemplate("foo:a={b}")
  public static class MBean12 {
    public String getB() {
      return "=";
    }
  }

  public void testNoAnnotation() {
    ObjectNameBuilder builder = new ObjectNameBuilder(new MBean13());
    assertEquals(null, builder.build());
  }

  public static class MBean13 {
  }

  public void testInheritence() {
    assertSame(new MBean14(), "foo:a=b");
    assertSame(new MBean15(), "foo:c=d");
    assertSame(new MBean16(), "foo:e=f");
    assertSame(new MBean17(), "foo:g=h");
    assertSame(new MBean18(), "foo:g=h");
    assertSame(new MBean19(), "foo:i=j");
  }

  @NameTemplate("foo:a=b")
  public static interface Interface1 { }
  public static class MBean14 implements Interface1 { }
  @NameTemplate("foo:c=d")
  public static class MBean15 implements Interface1 { }
  @NameTemplate("foo:e=f")
  public static interface Interface2 extends Interface1 { }
  public static class MBean16 implements Interface2 { }
  @NameTemplate("foo:g=h")
  public static class MBean17 { }
  public static class MBean18 extends MBean17 { }
  @NameTemplate("foo:i=j")
  public static class MBean19 extends MBean17 { }
}
