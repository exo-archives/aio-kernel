 /***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/

package org.exoplatform.commons.utils;

import java.util.ResourceBundle;

import junit.framework.TestCase;
import java.util.*;

/**
 * @author Benjamin Mestrallet
 * benjamin.mestrallet@exoplatform.com
 */
public class TestMapResourceBundle extends TestCase {
  public void testResolveDependencies() {
    ResourceBundle rB = ResourceBundle.getBundle("org.exoplatform.commons.utils.resources");
    MapResourceBundle mapRB = new MapResourceBundle(rB, new Locale("en"));
    mapRB.resolveDependencies();
    //System.out.println("KEYYYYYYYYY2 "+mapRB.getString("key1"));
    System.out.println("KEYYYYYYYYY2 "+mapRB.getString("key2"));
    //System.out.println("KEYYYYYYYYY2 "+mapRB.getString("key3"));
  }
  
}
