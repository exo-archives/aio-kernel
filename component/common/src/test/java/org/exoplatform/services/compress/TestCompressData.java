/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.compress;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.exoplatform.test.BasicTestCase;

/**
 * Created by The eXo Platform SARL
 * Author : Chung Nguyen
 *          nguyenchung136@yahoo.com
 * Feb 13, 2006
 */
public class TestCompressData extends BasicTestCase {
  
  public TestCompressData(String name){
    super(name);
  }
  
  public void testCompressData() throws Exception {
    CompressData compress  = new CompressData() ;
    CompressData compressIS = new CompressData();
    InputStream in = new FileInputStream("/home/exo/Desktop/ZipService.java");
    InputStream in2 = new FileInputStream("/home/exo/Desktop/help.txt");
    //---------- TEST InputStream --------------//
    System.out.println("Test InputStream");
    compressIS.addInputStream("ZipService.java",in);
    compressIS.addInputStream("help.txt",in2);
    compressIS.createJarFile("/home/exo/Desktop/ZipService");
    //compress.createZipFile("/home/exo/Desktop/ZipService");
    in.close();
    
    //----------- Test with Add File ------------------//
    System.out.println("Test File");
    
    File file = new File("/home/exo/Desktop/ZipService.java");
    File file2 = new File("/home/exo/Desktop/help.txt");
    compress.addFile("ZipService.java",file);
    compress.addFile("help.txt",file2);
    compress.createZipFile("/home/exo/Desktop/testFile");
    //compress.cleanDataInstance();
    //--------------- Test thu muc --------------------------------//
    File folder = new File("/home/exo/setup/tailieu/chung/hcm/images");
    File folder1 = new File("/home/exo/setup/tailieu/chung/hcm/xuly");
    CompressData compressF = new CompressData() ;
    compressF.addDir(folder);
    compressF.addDir(folder1);
    compressF.createZipFile("/home/exo/setup/tailieu/chung/hcm/TestZip");
    //compress.createJarFile("/home/exo/setup/tailieu/chung/hcm/TestJar");
  }
}
