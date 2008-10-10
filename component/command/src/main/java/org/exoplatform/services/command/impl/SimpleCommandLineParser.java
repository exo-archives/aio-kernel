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
package org.exoplatform.services.command.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.chain.Context;

/**
 * Created by The eXo Platform SAS.
 * 
 * @author Gennady Azarenkov
 * @version $Id: $
 */

public class SimpleCommandLineParser implements CommandLineParser {

  protected final String parametersPropertyName;

  public SimpleCommandLineParser(String parametersPropertyName) {
    this.parametersPropertyName = parametersPropertyName;
  }

  /*
   * (non-Javadoc)
   * @see
   * org.exoplatform.services.command.impl.CommandLineParser#parse(java.lang
   * .String, org.apache.commons.chain.Context)
   */
  public String parse(String commandLine, Context context) {

    context.remove(parametersPropertyName);

    // TODO make regexp parser
    // the rules:
    // first word is command name (should be returned)
    // else are parameters of command (should be put into Context under name ==
    // parametersPropertyName as array of Strings)
    // mind <space> contained string parameters - should be quoted (" or ')
    // /////////////////////

    StringTokenizer parser = new StringTokenizer(commandLine);
    String commandName = null;
    List<String> params = new ArrayList<String>();

    while (parser.hasMoreTokens()) {
      String str = parser.nextToken();
      if (commandName == null)
        commandName = str;
      else
        params.add(str);
    }
    // //////////////////////
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
