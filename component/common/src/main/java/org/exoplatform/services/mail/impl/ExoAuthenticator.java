/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.mail.impl;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Sep 12, 2004
 * @version $Id: ExoAuthenticator.java 5332 2006-04-29 18:32:44Z geaz $
 */
public class ExoAuthenticator extends Authenticator{
  private PasswordAuthentication authentication_ ;
  
  public ExoAuthenticator(String userName , String password) {
    authentication_ = new PasswordAuthentication(userName, password) ;
  }
  
  protected PasswordAuthentication getPasswordAuthentication() {
    return authentication_ ;
  }
}