/**
 * Copyright 2001-2003 The eXo Platform SARL         All rights reserved.  *
 * Please look at license.txt in info directory for more license detail.   *
 */

package org.exoplatform.services.command;

import org.apache.commons.chain.Command;
import org.apache.commons.chain.Context;
/**
 * Created by The eXo Platform SARL        .
 * @author <a href="mailto:gennady.azarenkov@exoplatform.com">Gennady Azarenkov</a>
 * @version $Id: TestCommand1.java 5799 2006-05-28 17:55:42Z geaz $
 */

public class TestCommand1 implements Command {

  public boolean execute(Context ctx) throws Exception {
    int tval = ((Integer)ctx.get("test")).intValue()+1;
    ctx.put("test", Integer.valueOf(tval));
    return false;
  }

}
