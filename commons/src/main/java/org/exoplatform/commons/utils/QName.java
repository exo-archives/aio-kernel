/**
 * Copyright 2001-2006 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 */

package org.exoplatform.commons.utils;


/**
 * Created by The eXo Platform SARL .
 * 
 * Qualified name
 * 
 * @author Gennady Azarenkov
 * @version $Id: $
 */

public class QName {

  protected final String namespace;

  protected final String name;
  
  protected final String stringName;
  
  protected int hashCode;

  // [PN] 05.02.07 use of canonical representation for the string values
  // see: http://java.sun.com/j2se/1.5.0/docs/api/java/lang/String.html#intern()  
  public QName(String namespace, String name) {
    this.namespace = (namespace != null ? namespace : "").intern();
    this.name = (name != null ? name : "").intern();
    
    this.stringName = ("[" + this.namespace + "]" + this.name).intern();
    this.hashCode = 31 * stringName.hashCode();
  }

  public String getNamespace() {
    return namespace;
  }

  public String getName() {
    return name;
  }

  public String getAsString() {
    return stringName;
  }
  
  @Override
  public String toString() {
    return super.toString() + " (" + getAsString() + ")";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this)
      return true;
    
    if (o == null)
      return false;

    if (!(o instanceof QName))
      return false;

    return hashCode == o.hashCode();
  }

  @Override
  public int hashCode() {
    return hashCode;
  }

}
