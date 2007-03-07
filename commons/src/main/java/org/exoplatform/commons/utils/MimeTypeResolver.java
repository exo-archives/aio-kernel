package org.exoplatform.commons.utils;

import java.util.Properties;
import java.util.Iterator;
import java.io.IOException;

public class MimeTypeResolver {

  private Properties mimeTypes = new Properties();

  private String defaultMimeType = "application/octet-stream";

  public MimeTypeResolver() {
    try {
      mimeTypes.load(getClass().getResourceAsStream("mimetypes.properties"));
    } catch (IOException e) {
      throw new InternalError("Unable to load mimetypes: " + e.toString());
    }
  }

  public String getDefaultMimeType() {
    return defaultMimeType;
  }

  public void setDefaultMimeType(String defaultMimeType) {
    this.defaultMimeType = defaultMimeType;
  }

  public String getMimeType(String filename) {
    String ext = filename.substring(filename.lastIndexOf(".") + 1);
    if (ext.equals("")) {
      ext = filename;
    }
    return mimeTypes.getProperty(ext.toLowerCase(), defaultMimeType);
  }
  
  public String getExtension(String mimeType)  {
	  if(mimeType.equals("")||mimeType.equals(defaultMimeType)) return "";
	  Iterator iterator = mimeTypes.keySet().iterator();
	  //if true than this flag define multiple mimetypes for different extensions exists
	  String ext = "";
	  while (iterator.hasNext()) {  
	    String key = (String) iterator.next();
	    String value = (String) mimeTypes.get(key);
	    if (value.equals(mimeType)&&mimeType.endsWith(key)) return key;
	    if (value.equals(mimeType)&&ext.equals("")) ext = new String(key);
	    else if (value.equals(mimeType)&&(!ext.equals(""))) return ext;
	  }
	  return "";
  }
}
