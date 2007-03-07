/**
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 */

package org.exoplatform.container.configuration;

/**
 * Created by The eXo Platform SARL        .
 * @author <a href="mailto:gennady.azarenkov@exoplatform.com">Gennady Azarenkov</a>
 * @version $Id: ConfigurationException.java 5799 2006-05-28 17:55:42Z geaz $
 */

public class ConfigurationException extends Exception {

	public ConfigurationException() {
		super();
	}

	public ConfigurationException(String arg0) {
		super(arg0);
	}

	public ConfigurationException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ConfigurationException(Throwable arg0) {
		super(arg0);
	}

}
