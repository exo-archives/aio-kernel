/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL    All rights reserved.       *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.commons.debug;

import java.util.* ;
import java.io.PrintStream ;

import javax.faces.component.UIComponent ;

/**
 * Static helper methods for string operations.
 * Author: Tuan Nguyen
 */
public class FacesHelper {
  
  static public void printUIComponentInfo(PrintStream out, UIComponent component) {
  }

  static public void printTreeInfo(PrintStream out, UIComponent component) {
    printTreeInfo(out,component, "")  ;

  }

  static private void printTreeInfo(PrintStream out, UIComponent component, String indent) {
    out.print(indent) ;
    out.print("[class:" + component.getClass().getName() +"]");
    out.print("[hash code:" + component.hashCode() +"]\n" );
    List children = component.getChildren() ;
    for (int i = 0; i < children.size(); i++) {
      UIComponent child = (UIComponent) children.get(i) ;
      printTreeInfo(out, child , indent + "  ") ;
    }
  }
}
