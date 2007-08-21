/***************************************************************************
 * Copyright 2001-2007 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.test;

import org.exoplatform.mocks.MockService;

/**
 * @author <a href="mailto:andrew00x@gmail.com">Andrey Parfonov</a>
 * @version $Id: $
 */
public class DefaultComponent {
    
    public DefaultComponent(MockService ms) {
      System.out.println("(Constructor) MoskService in DefaultComponent: " + ms);
    }
    
    public int hash() {
      return this.hashCode();
    }

}
