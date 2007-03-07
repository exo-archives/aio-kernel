/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.text;

import java.util.Map ;
import java.util.Iterator;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Feb 3, 2005
 * @version $Id$
 */
public class MapFormater implements ObjectFormater {
  private String starEntrySeparator_ ;
  private String endEntrySeparator_ ;
  private ObjectFormater keyFormater_ ;
  private ObjectFormater valueFormater_ ;
  
  public MapFormater(String startItemSeparator, String endItemSeparator) {
    starEntrySeparator_ = startItemSeparator ;
    endEntrySeparator_ = endItemSeparator;
  }
  
  public void   format(Writer w, Object obj) throws IOException {
    Map list = (Map) obj ;
    Iterator i = list.entrySet().iterator() ;
    while(i.hasNext()) {
      Map.Entry entry = (Map.Entry) i.next();
      if(starEntrySeparator_ != null)  w.write(starEntrySeparator_); 
      if(keyFormater_ == null ) w.write(entry.getKey().toString()); 
      else  keyFormater_.format(w, entry.getKey()) ;
      w.write("="); 
      if(valueFormater_ == null ) w.write(entry.getValue().toString()); 
      else  valueFormater_.format(w, entry.getValue()) ;
      if(endEntrySeparator_ != null)w.write(endEntrySeparator_) ;
    }
  }
  
  public String  format(Object obj) throws IOException {
    StringWriter w = new StringWriter() ;
    format(w, obj) ;
    return w.getBuffer().toString() ;
  }
  
  public ObjectFormater setValueFormater(ObjectFormater formater) {
    valueFormater_ = formater ;
    return this ;
  }
  
  public ObjectFormater setKeyFormater(ObjectFormater formater) {
    keyFormater_ = formater ;
    return this ;
  }
}