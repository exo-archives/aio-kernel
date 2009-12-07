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
package org.exoplatform.services.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.exoplatform.test.BasicTestCase;

/**
 * Created by The eXo Platform SAS Author : Chung Nguyen
 * nguyenchung136@yahoo.com Feb 13, 2006
 */
public class TestCompressData extends BasicTestCase {

  public TestCompressData(String name) {
    super(name);
  }

  public void testCompressData() throws Exception {
    CompressData compress = new CompressData();
    CompressData compressIS = new CompressData();
    InputStream in = new FileInputStream("src/test/resources/ZipService.java");
    InputStream in2 = new FileInputStream("src/test/resources/helper.txt");
    // ---------- TEST InputStream --------------//
    System.out.println("Test InputStream");
    compressIS.addInputStream("ZipService.java", in);
    compressIS.addInputStream("helper.txt", in2);
    compressIS.createJarFile("target/ZipService");
    // compress.createZipFile("/home/exo/Desktop/ZipService");
    in.close();

    // ----------- Test with Add File ------------------//
    System.out.println("Test File");

    File file = new File("src/test/resources/ZipService.java");
    File file2 = new File("src/test/resources/helper.txt");
    compress.addFile("ZipService.java", file);
    compress.addFile("helper.txt", file2);
    compress.createZipFile("target/testFile");
    // compress.cleanDataInstance();
    // --------------- Test thu muc --------------------------------//
    // TODO what is t?
//    File folder = new File("/home/exo/setup/tailieu/chung/hcm/images");
//    File folder1 = new File("/home/exo/setup/tailieu/chung/hcm/xuly");
//    CompressData compressF = new CompressData();
//    compressF.addDir(folder);
//    compressF.addDir(folder1);
//    compressF.createZipFile("/home/exo/setup/tailieu/chung/hcm/TestZip");
    // compress.createJarFile("/home/exo/setup/tailieu/chung/hcm/TestJar");
  }
}
