/*
 * Copyright (C) 2003-2007 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */
package org.exoplatform.services.mail.impl;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.exoplatform.container.xml.InitParams;
import org.exoplatform.services.mail.Attachment;
import org.exoplatform.services.mail.MailService;
import org.exoplatform.services.mail.Message;

/**
 * Created by The eXo Platform SAS Author : Phung Hai Nam phunghainam@gmail.com
 * Dec 23, 2005
 */
public class MailServiceImpl implements MailService {

  private Session    mailSession_;

  private Properties props_;

  public MailServiceImpl(InitParams params) throws Exception {
    props_ = new Properties(System.getProperties());
    props_.putAll(params.getPropertiesParam("config").getProperties());
    if ("true".equals(props_.getProperty("mail.smtp.auth"))) {
      String username = props_.getProperty("mail.smtp.auth.username");
      String password = props_.getProperty("mail.smtp.auth.password");
      ExoAuthenticator auth = new ExoAuthenticator(username, password);
      // S ystem.out.println("username: " + username + " , password " +
      // password);
      mailSession_ = Session.getDefaultInstance(props_, auth);
    } else {
      mailSession_ = Session.getDefaultInstance(props_, null);
    }
  }

  public Session getMailSession() {
    return mailSession_;
  }

  public String getOutgoingMailServer() {
    return props_.getProperty("mail.smtp.host");
  }

  public void sendMessage(String from, String to, String subject, String body) throws MessagingException,
                                                                              IOException {
    Message message = new Message();
    message.setFrom(from);
    message.setTo(to);
    message.setSubject(subject);
    message.setBody(body);
    sendMessage(message);
  }

  public void sendMessage(Message message) throws MessagingException {
    MimeMessage mimeMessage = new MimeMessage(getMailSession());
    String FROM = message.getFrom();
    String TO = message.getTo();
    String CC = message.getCC();
    String BCC = message.getBCC();
    String subject = message.getSubject();
    String mimeType = message.getMimeType();
    String body = message.getBody();
    List<Attachment> attachment = message.getAttachment();
    // set From to the message
    if (FROM != null && !FROM.equals("")) {
      InternetAddress sentFrom = new InternetAddress(FROM);
      mimeMessage.setFrom(sentFrom);
    }
    // set To to the message
    InternetAddress[] sendTo = new InternetAddress[getArrs(TO).length];
    for (int i = 0; i < getArrs(TO).length; i++) {
      sendTo[i] = new InternetAddress(getArrs(TO)[i]);
    }
    mimeMessage.setRecipients(javax.mail.Message.RecipientType.TO, sendTo);
    // set CC to the message
    if ((getArrs(CC) != null) && (getArrs(CC).length > 0)) {
      InternetAddress[] copyTo = new InternetAddress[getArrs(CC).length];
      for (int i = 0; i < getArrs(CC).length; i++) {
        copyTo[i] = new InternetAddress(getArrs(CC)[i]);
      }
      mimeMessage.setRecipients(javax.mail.Message.RecipientType.CC, copyTo);
    }
    // set BCC to the message
    if ((getArrs(BCC) != null) && (getArrs(BCC).length > 0)) {
      InternetAddress[] bccTo = new InternetAddress[getArrs(BCC).length];
      for (int i = 0; i < getArrs(BCC).length; i++) {
        bccTo[i] = new InternetAddress(getArrs(BCC)[i]);
      }
      mimeMessage.setRecipients(javax.mail.Message.RecipientType.BCC, bccTo);
    }
    // set Subject to the message
    mimeMessage.setSubject(subject);
    // set body to the message
    if ((attachment != null) && (attachment.size() > 0)) {
      MimeBodyPart messageBodyPart = new MimeBodyPart();
      if (mimeType != null && !mimeType.equals("")) {
        messageBodyPart.setContent(body, mimeType);
      } else
        messageBodyPart.setContent(body, "text/plain");
      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(messageBodyPart);
      // // set attachment to the message
      // MimeBodyPart attachePart ;
      // for (int i = 0; i < is.length; i++) {
      // MimeBodyPart part = new MimeBodyPart() ;
      // part.setContent(is[i], "text/plain") ;
      // DataSource dsource = new MimePartDataSource(part) ;
      // part.setDataHandler(new DataHandler(dsource)) ;
      // part.setFileName("attache-" + i + ".txt") ;
      // multipart.addBodyPart(part) ;
      // attachePart = new MimeBodyPart(is[i]) ;
      // DataSource source = new MimePartDataSource(attachePart) ;
      // attachePart.setDataHandler(new DataHandler(source)) ;
      // attachePart.setContent("daf ====================== dsfdsf"
      // ,"text/html") ;
      // attachePart.setFileName("attachPart 1") ;
      // multipart.addBodyPart(attachePart,0) ;
      // }
      mimeMessage.setContent(multipart);
    } else {
      if (mimeType != null && !mimeType.equals("")) {
        mimeMessage.setContent(body, mimeType);
      } else
        mimeMessage.setContent(body, "text/plain");
    }
    sendMessage(mimeMessage);
  }

  public void sendMessage(MimeMessage message) throws MessagingException {
    try {
      Transport.send(message);
    } catch (MessagingException e) {
      e.printStackTrace();
    }
  }

  private String[] getArrs(String toArray) {
    if (toArray != null && !toArray.equals("")) {
      return toArray.split(",");
    }
    return null;
  }
}
