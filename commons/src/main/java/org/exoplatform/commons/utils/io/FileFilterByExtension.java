/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.commons.utils.io;

import java.util.HashSet;
import java.io.FileFilter;
import java.io.File;
/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Sep 13, 2004
 * @version $Id: FileFilterByExtension.java,v 1.2 2004/09/16 03:03:19 tuan08 Exp $
 */
public class FileFilterByExtension implements FileFilter {
  private boolean acceptDir_ ;
  private HashSet knownExt_ ;
  
  public FileFilterByExtension(String[] ext, boolean acceptDir) {
    acceptDir_ = acceptDir ;
    knownExt_ = new HashSet() ;
    for(int i = 0; i < ext.length; i++ ) {
      knownExt_.add(ext[i].trim().toLowerCase()) ;
    }
  }
  
  public boolean accept(File file) {
    if(file.isDirectory()) {
      if(acceptDir_) return true ;
      return false ;
    }
    String temp = file.getName() ;
    int idx = temp.lastIndexOf(".") ;
    if(idx > 0 ) temp = temp.substring(idx + 1, temp.length()) ;
    else  return false ;
    return knownExt_.contains(temp.toLowerCase()) ;
  }
}
