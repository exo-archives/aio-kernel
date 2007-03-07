/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.monitor.jvm;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Feb 8, 2005
 * @version $Id: ExoInstrument.java 5799 2006-05-28 17:55:42Z geaz $
 * You should pass -javaagent:org.exoplatform.container.monitor.jvm.v15.ExoInstrument
 * to JAVA_OPTS
 */
public class ExoInstrument {
  static private Instrumentation ins_ ;
//  
//  public static void main(String args[] ) {
////    S ystem.out.println("Hello World" );
//  }
//  
//  public static void premain(String options, Instrumentation ins) {
//    //ins.addTransformer(new Logger() );
//    ins_ = ins ;
//    S ystem.out.println("==========================> call premain") ;
//  }
//  
//  public static Instrumentation getInstrumentation() { return ins_ ; }
//  
//  public void printObjectSize(Object object) {
//    S ystem.out.println("size of " + object.getClass().getName() + " = "+ ins_.getObjectSize(object)) ;
//  }
//  
//  /*Sample transformer , this class just print the loaded classes*/
//  public static class Logger implements ClassFileTransformer {
//    public byte[] transform(java.lang.ClassLoader loader,
//                            java.lang.String className,
//                            java.lang.Class classBeingRedefined,
//                            java.security.ProtectionDomain protectionDomain,
//                            byte[] classfileBuffer) throws IllegalClassFormatException   {
//      S ystem.out.println(className );
//      return null;
//    }
//  }
}