/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.mail;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Oct 13, 2004
 * @version $Id: MailService.java 5332 2006-04-29 18:32:44Z geaz $
 */
public interface MailService {
  public Session getMailSession();

  public String getOutgoingMailServer();
  
  public void sendMessage(String from, String to, String subject, String body)throws Exception;
  
  public void sendMessage(Message message) throws Exception ;
  
  public void sendMessage(MimeMessage message) throws Exception;
}