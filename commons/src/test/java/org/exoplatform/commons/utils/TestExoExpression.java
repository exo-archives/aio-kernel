/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.commons.utils;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.zip.ZipFile;
import junit.framework.TestCase;

/**
 * Created by The eXo Platform SARL
 * Author : Chung Nguyen
 *          nguyenchung136@yahoo.com
 * Feb 8, 2006
 */
public class TestExoExpression extends TestCase {
  protected ExoExpression ZipAction_;
  static ZipFile zf ;
  public static final int EOF = -1;
  public void testExoExpression() throws Exception {
    String path = "/home/exo/Desktop/ZipService.java";
    String pathOut = "/home/exo/Desktop/" ;
    //----------- Test with one InputStream--------------------//
    InputStream is = new FileInputStream(path);
    ZipAction_= new ExoExpression();
    ExoExpression.setEntry("ZipService.java");
    ExoExpression.setPathOut("/home/exo/Desktop/");
    ByteArrayInputStream bais = ZipAction_.ZipExcute(is);
    if( bais.available() > 0 ) System.out.println("Test OK Zip with one InputStream");
    //ZipAction_.getFileToByte(bais);
    
    //----------------- Test with URL --------------------//
    String url = "http://vnexpress.net/";
    ExoExpression.setEntry("index.htm");
    ExoExpression.setOutFileName("vnexpress");
    ByteArrayInputStream urlByte = ZipAction_.ZipExcute(url);
    if( urlByte != null) System.out.println("Test OK Zip with one URL");
    //ZipAction_.getFileToByte(urlByte);
    
    //----------------- Test Zip with ByteArrayInputStream
    byte data1[] = url.getBytes();
    ExoExpression.setEntry("test.txt");
    ExoExpression.setOutFileName("TestArrByte");
    ByteArrayInputStream baisByte = ZipAction_.ZipExcute(new ByteArrayInputStream(data1));
    if( baisByte != null) System.out.println("Test OK Zip voi ByteArrayInputStream");
    //ZipAction_.getFileToByte(baisByte);
    
    //------------- Test Zip with one File-------------------//
//    /*File file = new File("/home/exo/Desktop/docview.xml");
//    ExoExpression.setEntry(file.getName());
//    ExoExpression.setOutFileName(file.getName());
//    ExoExpression.setPathOut(pathOut);
//    ByteArrayInputStream baisFile = ZipAction_.ZipExcute(file);
//    ZipAction_.getFileToByte(baisFile);
//    if(baisFile.available()>0) System.out.println("Test OK Zip with one File");*/
    
    //----------------- Test Zip with Dir ----------------------- //
    String pathDir = "/home/exo/setup/PortalHello/";
    ExoExpression.setPathOut("/home/exo/Desktop/");
    ExoExpression.setOutFileName("dir");
    ByteArrayInputStream baisDir = ZipAction_.getZipByteToFolder(new File(pathDir),true);
    if( baisDir != null) System.out.println("Test OK Zip with one Dir");
    ZipAction_.getFileToByte(baisDir);
  }
 }

