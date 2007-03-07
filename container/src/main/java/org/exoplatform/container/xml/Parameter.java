/***************************************************************************
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 **************************************************************************/
package org.exoplatform.container.xml;

/**
 * @author Tuan Nguyen (tuan08@users.sourceforge.net)
 * @since Oct 20, 2004
 * @version $Id: Parameter.java 5799 2006-05-28 17:55:42Z geaz $
 */
abstract public class Parameter {
  private String  name;
  private String  description;

  public String getDescription() { return description; }
  public void setDescription(String description) { this.description = description; }

  public String getName() { return name; }
  public void setName(String name) { this.name = name; }
}
