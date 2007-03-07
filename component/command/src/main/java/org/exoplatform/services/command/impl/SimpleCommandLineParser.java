/**
 * Copyright 2001-2006 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 */

package org.exoplatform.services.command.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.chain.Context;

/**
 * Created by The eXo Platform SARL        .
 * @author Gennady Azarenkov
 * @version $Id: $
 */

public class SimpleCommandLineParser implements CommandLineParser {

  protected final String parametersPropertyName;
  
  public SimpleCommandLineParser(String parametersPropertyName) {
    this.parametersPropertyName = parametersPropertyName;
  }

  /* (non-Javadoc)
   * @see org.exoplatform.services.command.impl.CommandLineParser#parse(java.lang.String, org.apache.commons.chain.Context)
   */
  public String parse(String commandLine, Context context) {
    
    context.remove(parametersPropertyName);
    
    // TODO make regexp parser
    // the rules:
    //first word is command name (should be returned)
    //else are parameters of command (should be put into Context under name == parametersPropertyName as array of Strings)
    //mind <space> contained string parameters - should be quoted (" or ')
    ///////////////////////
    
    StringTokenizer parser = new StringTokenizer(commandLine);
    String commandName = null;
    List <String> params = new ArrayList <String>();
    
    while(parser.hasMoreTokens()) {
      String str = parser.nextToken();
      if(commandName == null)
        commandName = str;
      else
        params.add(str);
    }
    ////////////////////////
    context.put(parametersPropertyName, params);
    return commandName; 
  }

  /**
   * @return parameters Property Name
   */
  public String getParametersPropertyName() {
    return parametersPropertyName;
  }

}
