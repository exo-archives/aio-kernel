 /***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/

package org.exoplatform.xml.test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.exoplatform.commons.debug.ObjectDebuger;
import org.exoplatform.xml.object.XMLCollection;
import org.exoplatform.xml.object.XMLObject;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
/**
 * @author Benjamin Mestrallet
 * benjamin.mestrallet@exoplatform.com
 */
public class TestDataXML extends TestCase{

  public void testMarshallAndUnmarshall() throws Exception {
    String projectdir = System.getProperty("basedir") ;
    IBindingFactory bfact = BindingDirectory.getFactory(XMLObject.class);
    IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
    Object obj = uctx.unmarshalDocument(new FileInputStream(projectdir + "/src/resources/object.xml"), null);
    System.out.print(obj) ;
    
    IMarshallingContext mctx = bfact.createMarshallingContext();
    mctx.setIndent(2);
    mctx.marshalDocument(obj, "UTF-8", null,  new FileOutputStream(projectdir + "/target/object.xml")) ;
  }
  
  public void testConvert() throws Exception {
    String projectdir = System.getProperty("basedir") ;
    XMLObject xmlobj = new XMLObject(new TestObject());
    FileOutputStream os = new FileOutputStream(projectdir + "/target/test-object-1.xml") ;
    String xml1 = new String(xmlobj.toByteArray("UTF-8")) ;
    os.write(xml1.getBytes()) ;
    os.close() ;
    System.out.println("---------XML Object------------------------") ;
    System.out.println(ObjectDebuger.asString(xmlobj.toObject())) ;
    FileInputStream is = new FileInputStream(projectdir + "/target/test-object-1.xml") ;
    TestObject tobject = (TestObject)XMLObject.getObject(is) ;
    assertTrue(tobject.nested.intarray.length == 10) ;
    os = new FileOutputStream(projectdir +  "/target/test-object-2.xml") ;
    xmlobj = new XMLObject(tobject);
    String xml2 = new String(xmlobj.toByteArray("UTF-8")) ;
    os.write(xml2.getBytes()) ;
    os.close() ;
    assertTrue(xml1.equals(xml2)) ;
    is.close();
    List list = new ArrayList() ;
    list.add("test.....................") ;
    list.add(new Date()) ;
    XMLCollection xmllist = new XMLCollection(list);
    os = new FileOutputStream(projectdir +  "/target/list.xml") ;
    os.write(xmllist.toByteArray("UTF-8")) ;
    os.close() ;
    /*
    int[] intarray = new int[10] ;
    Object[] array = new Object[10] ;
    System.out.println("int array class type = " + intarray.getClass().getName()) ;
    System.out.println("int array class type = " + array.getClass().getName()) ;
    */
  }
  
  static public class TestObject {
    final static public String staticField = "staticField";
    String field = "field";
    String method ;
    Map map = new HashMap() ;
    List list = new ArrayList() ;
    NestedObject nested = new NestedObject() ;
    
    public TestObject() {
      Map nestedMap = new HashMap() ;
      nestedMap.put("nestedMapKey", "nestedMapvalue") ;
      map.put("string", "string") ;
      map.put("int", new Integer(10000)) ;
      map.put("my", nestedMap) ;
      list.add("a list value") ;
      list.add("a list value") ;
    }
    
    public String getMethod() { return method ;}
    public void   setMethod(String s) { method = s ; }
    
    
  }
  
  static public class NestedObject {
    
    String field = "field";
    String method ;
    String xmlstring = "<xmlstring>this is a test</xmlstring>" ;
    int[] intarray = new int[10] ;
    int integer = 10;
    Map map = new HashMap() ;
    
    public NestedObject() {
      intarray[0] = 1 ;
      intarray[2] = 2 ;
    }
    
    public String getMethod() { return method ;}
    public void   setMethod(String s) { method = s ; }
  }
}
