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
package org.exoplatform.container.configuration.test;

import org.exoplatform.test.BasicTestCase;
import org.exoplatform.container.configuration.ConfigurationUnmarshaller;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * @author <a href="mailto:julien.viet@exoplatform.com">Julien Viet</a>
 * @version $Revision$
 */
public class TestXSD_1_0 extends BasicTestCase {

  public void testValidation() throws Exception {
    ConfigurationUnmarshaller unmarshaller = new ConfigurationUnmarshaller();
    String baseDirPath = System.getProperty("basedir");
    File baseDir = new File(baseDirPath + "/src/main/resources/xsd_1_0");
    int count = 0;
    for (File f : baseDir.listFiles(new FileFilter() {
      public boolean accept(File pathname) {
        return pathname.getName().endsWith(".xml");
      }
    })) {
      count++;
      try {
        URL url = f.toURI().toURL();
        assertTrue("XML configuration file " + url + " is not valid", unmarshaller.isValid(url));
      }
      catch (MalformedURLException e) {
        fail("Was not expecting such exception " + e.getMessage());
      }
    }
    assertEquals(16, count);
  }
}
