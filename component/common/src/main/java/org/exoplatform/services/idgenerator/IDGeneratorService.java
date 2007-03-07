/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.idgenerator;

import java.io.Serializable;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Oct 14, 2004
 * @version $Id: IDGeneratorService.java 5332 2006-04-29 18:32:44Z geaz $
 */
public interface IDGeneratorService {
  public static final int ID_LENGTH = 32;
  
  public Serializable generateID(Object o) ;
  public String generateStringID(Object o)  ; 
  public int   generatIntegerID(Object o)  ; 
  public long   generateLongID(Object o)  ; 
}