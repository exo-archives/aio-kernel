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
package org.exoplatform.container.monitor.jvm;

import java.lang.instrument.Instrumentation;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Feb 8, 2005
 * @version $Id: ExoInstrument.java 5799 2006-05-28 17:55:42Z geaz $ You should
 *          pass
 *          -javaagent:org.exoplatform.container.monitor.jvm.v15.ExoInstrument
 *          to JAVA_OPTS
 */
public class ExoInstrument {
  static private Instrumentation ins_;
  //  
  // public static void main(String args[] ) {
  // // S ystem.out.println("Hello World" );
  // }
  //  
  // public static void premain(String options, Instrumentation ins) {
  // //ins.addTransformer(new Logger() );
  // ins_ = ins ;
  // S ystem.out.println("==========================> call premain") ;
  // }
  //  
  // public static Instrumentation getInstrumentation() { return ins_ ; }
  //  
  // public void printObjectSize(Object object) {
  // S ystem.out.println("size of " + object.getClass().getName() + " = "+
  // ins_.getObjectSize(object)) ;
  // }
  //  
  // /*Sample transformer , this class just print the loaded classes*/
  // public static class Logger implements ClassFileTransformer {
  // public byte[] transform(java.lang.ClassLoader loader,
  // java.lang.String className,
  // java.lang.Class classBeingRedefined,
  // java.security.ProtectionDomain protectionDomain,
  // byte[] classfileBuffer) throws IllegalClassFormatException {
  // S ystem.out.println(className );
  // return null;
  // }
  // }
}
