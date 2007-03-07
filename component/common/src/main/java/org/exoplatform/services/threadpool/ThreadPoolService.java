/*
 * Copyright 2001-2003 The eXo platform SARL All rights reserved.
 * Please look at license.txt in info directory for more license detail.
 */

package org.exoplatform.services.threadpool;

/**
 * @author Mestrallet Benjamin
 *         benjmestrallet@users.sourceforge.net
 */
public interface ThreadPoolService {

  public void execute(Runnable task) throws InterruptedException;

}
