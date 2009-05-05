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

import javax.management.ObjectName;
import javax.management.MalformedObjectNameException;
import java.util.Map;
import java.util.Hashtable;

/**
 * Various JMX utilities.
 *
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class JMX {

  private JMX() {
  }

  /**
   * This method create an object name from a generic map argument. The main reason is that
   * the method {@link javax.management.ObjectName#getInstance(String, java.util.Hashtable)} has
   * uses a non generic Hashtable with Java 5 and use a Hashtable<String, String> constructor in Java 6.
   *
   * The suitable solution is therefore to use a non generic Hashtable but that creates compilation warning therefore
   * we encapsulate there this code in order to use the warning supression in that single place.
   *
   * @see ObjectName#getInstance(String, java.util.Hashtable)
   *
   * @param domain  The domain part of the object name.
   * @param table A hash table containing one or more key
   * properties.  The key of each entry in the table is the key of a
   * key property in the object name.  The associated value in the
   * table is the associated value in the object name.
   *
   * @return an ObjectName corresponding to the given domain and
   * key mappings.
   * @exception MalformedObjectNameException The <code>domain</code>
   * contains an illegal character, or one of the keys or values in
   * <code>table</code> contains an illegal character, or one of the
   * values in <code>table</code> does not follow the rules for
   * quoting.
   * @exception NullPointerException One of the parameters is null.
   */
  @SuppressWarnings("unchecked")
  public static ObjectName createObjectName(String domain, Map<String, String> table) throws MalformedObjectNameException, NullPointerException {
    Hashtable tmp = new Hashtable(table);
    return ObjectName.getInstance(domain, tmp);
  }
}
