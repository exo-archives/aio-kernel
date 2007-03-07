/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.services.mail;

import java.io.InputStream;

/**
 * Created by The eXo Platform SARL Author : Phung Hai Nam phunghainam@gmail.com
 * Dec 28, 2005
 */
public class Attachment {
  private InputStream inputStream = null;

  private String      mimeType    = "";

  public InputStream getInputStream() {
    return inputStream;
  }

  public void setInputStream(InputStream value) {
    inputStream = value;
  }

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String value) {
    mimeType = value;
  }
}