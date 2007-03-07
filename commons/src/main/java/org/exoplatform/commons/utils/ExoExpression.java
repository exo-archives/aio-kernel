/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.commons.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by The eXo Platform SARL
 * Author : Chung Nguyen
 *          nguyenchung136@yahoo.com
 * Feb 8, 2006
 */
public class ExoExpression {
  protected static final int EOF = -1;
  protected static final int BUFFER = 2048;
  public static String entryName ;
  public static String outFileName;
  public static String pathOut ;
  
  public ExoExpression(){}
  /**
   * Method accept Zip one InputStream
   * @param is : InputStream 
   * @return : ByteArrayInputStream
   * @throws Exception
   */
  public ByteArrayInputStream ZipExcute(InputStream is) throws Exception{
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    BufferedInputStream bis = new BufferedInputStream(is);
    BufferedOutputStream out = new BufferedOutputStream(baos);
    int c;
    while( ( c = bis.read() ) != EOF ) {
         out.write( (byte)c );
    }
    bis.close();
    out.close();
    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    return getZipOutputStream(bais);
  }
  /**
   * Method accept Zip one File 
   * @param file : File input
   * @return : ByteArrayInputStream 
   * @throws Exception : FileNotFoundException
   */
  public ByteArrayInputStream ZipExcute(File file) throws Exception{
    ByteArrayInputStream bais ;
    if(file.isDirectory()){
      bais = getZipByteToFolder(file,true);
    }else {
      setEntry(file.getName());
      InputStream is = new FileInputStream(file);
      bais = ZipExcute(is);
    }
    return bais;
  }
  /**
   * Method accept Zip one ByteArrayInputStream
   * @param bytesStream
   * @return ByteArrayInputStream
   * @throws Exception
   */
  public ByteArrayInputStream ZipExcute(ByteArrayInputStream bytesStream) throws Exception{
    return getZipOutputStream(bytesStream);
  }
  /**
   * Method accept download File with URL and Zip it 
   * @param url 
   * @return : ByteArrayInputStream
   * @throws Exception
   */
  public ByteArrayInputStream ZipExcute(String url) throws Exception{
    URL urlAdd = new URL(url);
    InputStream inputStream = urlAdd.openStream();
    return ZipExcute(inputStream);
  }
  /**
   * Method create File Zip with 
   * @throws Exception
   */
  public ByteArrayInputStream getZipOutputStream(ByteArrayInputStream bais)throws Exception{
    byte[] buf = new byte[BUFFER];
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ZipOutputStream out = new ZipOutputStream(baos);
    out.putNextEntry(new ZipEntry(getEntry()));
    if(bais != null && bais.available() != EOF){    
      int count;
      while ((count = bais.read(buf)) > 0){
        out.write(buf, 0, count);
      }
    }   
    out.closeEntry();
    out.close();
    bais.close();
    return new ByteArrayInputStream(baos.toByteArray());
  }
  /**
   * Create File with ByteArrInputStrem Zip
   * @param bais
   * @return File Zip
   * @throws Exception
   */
  public File getFileToByte(ByteArrayInputStream bais) throws Exception{
    File file = new File(getDirOut());
    byte[] data = new byte[BUFFER];
    FileOutputStream out = new FileOutputStream(file);
    ByteArrayOutputStream baos = new ByteArrayOutputStream(bais.available());
    int x  ;
    while(( x = bais.read(data,0,BUFFER)) >0){
      baos.write(data,0,x);
    }
    baos.writeTo(out);
    baos.close();
    bais.close();
    out.close();
    return file;
  }
  
  public static void setEntry(String entry) { entryName = entry;}
  public static String getEntry() {  return entryName ;  }
  /**
   * @param fileName : Name of File Zip out
   */
  public static void setOutFileName(String fileName) { outFileName = fileName;  }
  public static String getOutFileName() { return outFileName; }
  /**
   * @param dir : Directory Out 
   */
  public static void setPathOut(String dir) { pathOut = dir;  }
  public static String getPathOut() {  return pathOut;  }
  /**
   * @return Dir of Zip File
   */
  public String getDirOut(){
    String dir = getPathOut()+getOutFileName()+".zip";
    return dir ;
  }
  /**
   * Mothed accept Zip one Folder and Return ByteArrayInputStream of it after Zip
   * @param input : File Folder input
   * @param containParent = true Folder is Parent
   * @return ByteArrayInputStream after Zip
   * @throws Exception
   */
  public ByteArrayInputStream getZipByteToFolder(File input,boolean containParent)throws Exception{ 
    String path = input.getAbsolutePath();
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    ZipOutputStream zipOutput = new ZipOutputStream(baos);      
    BufferedInputStream bufInput = null;  
    
    List<File> list = listFile(input);    
    if(input.isDirectory()) list.remove(input);
    if( list == null || list.size() < 1) return new ByteArrayInputStream(baos.toByteArray());
    for (File f : list) {      
      String filePath = f.getAbsolutePath();
      if (filePath.startsWith(path)){
        if(containParent && input.isDirectory())
          filePath = input.getName() + File.separator + filePath.substring(path.length()+1);          
        else if(input.isDirectory())
          filePath = filePath.substring(path.length()+1);
        else
          filePath = input.getName();
      }
      if(f.isFile()){
        FileInputStream fileInput = new FileInputStream(f);
        bufInput = new BufferedInputStream(fileInput, BUFFER);
      }else 
        filePath += "/";      
      addToArchive(zipOutput, bufInput, filePath);      
      if(bufInput != null) bufInput.close();      
    }
    zipOutput.close();
    ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
    return bais;
  }  
  private List<File> listFile(File dir) {
    final List <File> list = new ArrayList<File>();    
    if(dir.isFile()) {
      list.add(dir);
      return list;
    }
    dir.listFiles(new FileFilter() {
      public boolean accept(File f) {
        if (f.isDirectory()) list.addAll(listFile(f));
        list.add(f);
        return true;
      }
    });
    return list;
  }
  
  public ZipOutputStream addToArchive(ZipOutputStream zipOutput, InputStream input, String entryName1)throws Exception{    
    byte data[] = new byte[BUFFER];
    ZipEntry entry = new ZipEntry(entryName1);    
    zipOutput.putNextEntry(entry);    
    if(input != null){    
      int count;
      while ((count = input.read(data, 0, BUFFER)) != EOF) zipOutput.write(data, 0, count);  
    }    
    zipOutput.closeEntry();    
    return zipOutput;
  }
  
  }
