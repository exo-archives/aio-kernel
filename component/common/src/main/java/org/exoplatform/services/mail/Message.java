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
package org.exoplatform.services.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by The eXo Platform SAS Author : Phung Hai Nam phunghainam@gmail.com
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
