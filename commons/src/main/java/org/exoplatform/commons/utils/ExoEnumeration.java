/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.commons.utils;

/** * Jul 11, 2004 
 * @author: Tuan Nguyen
 * @email:   tuan08@users.sourceforge.net
 * @version: $Id: ExoEnumeration.java,v 1.3 2004/07/13 02:46:19 tuan08 Exp $
 */

import java.util.Iterator;
import java.util.Enumeration;

/**
 * @author Ove Ranheim (oranheim@users.sourceforge.net)
 * @since Nov 9, 2003 4:01:29 PM
 */
public class ExoEnumeration implements Enumeration {
  private Iterator  iterator_ ;

  public ExoEnumeration(Iterator i) {
    iterator_ = i ;
  }

  public boolean hasMoreElements() {
    return iterator_.hasNext();
  }

  public Object nextElement() {
    return iterator_.next();
  }
}