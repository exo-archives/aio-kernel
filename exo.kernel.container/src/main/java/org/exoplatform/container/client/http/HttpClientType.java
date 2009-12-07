/*
 * Jul 8, 2004, 3:39:40 PM
 * @author: F. MORON
 * @email: francois.moron@rd.francetelecom.com
 * 
 * */
package org.exoplatform.container.client.http;


public class HttpClientType {
  
	private String name_;
	private String userAgentPattern_;
	private String preferredMimeType_;
	private String type_ = HttpClientInfo.STANDARD_BROWSER_TYPE ;
	
	public HttpClientType(String name, String userAgentPattern, String preferredMimeType, String type) {
		this(name, userAgentPattern, preferredMimeType);
		type_ = type;
	}

	public HttpClientType(String name, String userAgentPattern, String preferredMimeType) {
		name_ = name;
		userAgentPattern_ = userAgentPattern;
		preferredMimeType_ = preferredMimeType;
	}
	
	public HttpClientType() {
		this("", "", "");
	}

	/**
	 * @return Returns the name_.
	 */
	public String getName() {		return name_;	}
  
	/**
	 * @return Returns the preferredMimeType_.
	 */
	public String getPreferredMimeType() { return preferredMimeType_;	}
  
	/**
	 * @return Returns the userAgentPattern_.
	 */
	public String getUserAgentPattern() { return userAgentPattern_; }

	/**
	 * @return Returns the renderer_.
	 */
	public String getType() {		return type_; }
  
  public String toString() {
    StringBuilder b = new StringBuilder() ;
    b.append("[").append(name_).append(",").
      append(preferredMimeType_).append(",").
      append(userAgentPattern_).append(",").
      append(type_).append("]");
    return b.toString() ;
  }
}
