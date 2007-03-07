/**
 * Copyright 2001-2006 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 */

package org.exoplatform.services.command.impl;

import org.apache.commons.chain.Context;

/**
 * Created by The eXo Platform SARL        .
 * @author Gennady Azarenkov
 * @version $Id: $
 */
public interface CommandLineParser {
  
  /**
   * parses command line and puts some parameters (if any) to the Context by some rules 
   * @param commandLine
   * @param context
   * @return command name
   */
  String parse(String commandLine, Context context);
}
