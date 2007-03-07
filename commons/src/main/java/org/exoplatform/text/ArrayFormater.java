/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.text;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Feb 3, 2005
 * @version $Id$
 */
public class ArrayFormater implements ObjectFormater {
  private String starItemSeparator_ ;
  private String endItemSeparator_ ;
  private ObjectFormater valueFormater_ ;
  
  public ArrayFormater(String startItemSeparator, String endItemSeparator) {
    starItemSeparator_ = startItemSeparator ;
    endItemSeparator_ = endItemSeparator;
  }
  
  public void   format(Writer w, Object obj) throws IOException {
    Object[] list = (Object[]) obj ;
    for(int i = 0; i < list.length ; i++) {
      if(starItemSeparator_ != null)  w.write(starItemSeparator_); 
      if(valueFormater_ == null )w.write(list[i].toString());
      else valueFormater_.format(w, list[i]) ;
      if(endItemSeparator_ != null)w.write(endItemSeparator_) ;
    }
  }
  
 public String format(Object obj) throws IOException {
   StringWriter w = new StringWriter() ;
   format(w, obj) ;
   return w.getBuffer().toString() ;
  }
  
  public ObjectFormater setValueFormater(ObjectFormater formater) {
    valueFormater_ = formater ;
    return this ;
  }
}