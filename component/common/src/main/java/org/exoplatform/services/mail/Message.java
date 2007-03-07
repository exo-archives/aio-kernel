/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by The eXo Platform SARL Author : Phung Hai Nam phunghainam@gmail.com
 * Dec 28, 2005
 */

public class Message {
  private String           sender   = "";

  private String           receiver = "";

  private String           CC       = "";

  private String           BCC      = "";

  private String           subject  = "";

  private String           body     = "";

  private String           mimeType = "text/plain";

  private List<Attachment> attachments;

  public String getFrom() {
    return sender;
  }

  public void setFrom(String value) {
    sender = value;
  }

  public String getTo() {
    return receiver;
  }

  public void setTo(String value) {
    receiver = value;
  }

  public String getReceiver() {
    return receiver;
  }

  public void setReceiver(String value) {
    receiver = value;
  }

  public String getCC() {
    return CC;
  }

  public void setCC(String value) {
    CC = value;
  }

  public String getBCC() {
    return BCC;
  }

  public void setBCC(String value) {
    BCC = value;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String value) {
    body = value;
  }

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String value) {
    mimeType = value;
  }

  public String getSubject() {
    return subject;
  }

  public void setSubject(String value) {
    subject = value;
  }

  public void addAttachment(Attachment value) {
    if (attachments == null)
      attachments = new ArrayList();
    attachments.add(value);
  }

  public List<Attachment> getAttachment() {
    return attachments;
  }
}
