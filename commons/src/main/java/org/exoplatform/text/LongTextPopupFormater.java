/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.text;

import java.io.IOException;
import java.io.Writer;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Feb 3, 2005
 * @version $Id$
 */
public class LongTextPopupFormater implements ObjectFormater {
  private int maxLength_ ;
  
  public LongTextPopupFormater(int textSize) {
    maxLength_ = textSize ;
  }
  
  public void   format(Writer w, Object obj) throws IOException {
    String value  = obj.toString() ;
    if(value.length() < maxLength_) {
      w.write(value) ;
    } else {
      w.write("<span title='");
      w.write(value.toString()) ;
      w.write("'>") ;
      w.write(value.substring(0 , maxLength_)) ;
      w.write(".....") ;
      w.write("</span>") ;
    }
  }
  
  public String format(Object obj) throws IOException {
    String value  = obj.toString() ;
    StringBuffer b = new StringBuffer() ;
    if(value.length() < maxLength_) {
      b.append(value) ;
    } else {
      b.append("<span title='");
      b.append(value.toString()) ;
      b.append("'>") ;
      b.append(value.substring(0 , maxLength_)) ;
      b.append(".....") ;
      b.append("</span>") ;
    }
    return b.toString() ;
  }
}