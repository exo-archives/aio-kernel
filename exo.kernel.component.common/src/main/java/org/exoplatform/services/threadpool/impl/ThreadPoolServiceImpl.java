/**
 * $Id: ThreadPoolServiceImpl.java 5332 2006-04-29 18:32:44Z geaz $
 *
 * The contents of this file are subject to the ClickBlocks Public
 * License Version 1.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of
 * the License at http://www.clickblocks.org
 *
 * Software distributed under the License is distributed on an "AS
 * IS" basis, WITHOUT WARRANTY OF ANY KIND, either express or
 * implied, including, but not limited to, the implied warranties of
 * merchantability, fitness for a particular purpose and
 * non-infringement. See the License for the specific language
 * governing rights and limitations under the License.
 *
 * ClickBlocks, the ClickBlocks logo and combinations thereof are
 * trademarks of ClickBlocks, LLC in the United States and other
 * countries.
 *
 * The Initial Developer of the Original Code is ClickBlocks, LLC.
 * Portions created by ClickBlocks, LLC are Copyright (C) 2000.
 * All Rights Reserved.
 *
 * Contributor(s): Mark Grand
 */

package org.exoplatform.services.threadpool.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.apache.commons.logging.Log;

import org.exoplatform.services.log.LogService;
import org.exoplatform.services.threadpool.ThreadPoolService;

@Deprecated
public class ThreadPoolServiceImpl implements ThreadPoolService {

  /**
   * The maximum pool size; used if not otherwise specified. Default value is
   * 500
   */
  public static final int  DEFAULT_MAXIMUMPOOLSIZE = 500;

  /**
   * The normal pool size; used if not otherwise specified. Default value is 1.
   */
  public static final int  DEFAULT_NORMALPOOLSIZE  = 1;

  /**
   * The maximum time to keep worker threads alive waiting for new tasks; used
   * if not otherwise specified. Default value is one minute (60000
   * milliseconds).
   */
  public static final long DEFAULT_MAXIDLETIME     = 60 * 1000;

  // Bounds declared as volatile to avoid having to carefully
  // synchronize responses to changes in value.
  protected volatile int   maximumPoolSize         = DEFAULT_MAXIMUMPOOLSIZE;

  protected volatile int   normalPoolSize          = DEFAULT_NORMALPOOLSIZE;

  protected long           maxIdleTime             = DEFAULT_MAXIDLETIME;

  /*
   * The queue is used to hand off the task to a thread in the pool
   */
  protected Queue          handOff;

  /**
   * Lock used for protecting poolSize and threads map *
   */
  protected Object         poolLock                = new Object();

  /**
   * Current pool size. Relies on poolLock for all locking. But is also volatile
   * to allow simpler checking inside worker thread runloop.
   */
  protected volatile int   poolSize                = 0;

  /**
   * An object to map active worker objects to their active thread. This is used
   * by the interruptAll method. It may also be useful in subclasses that need
   * to perform other thread management chores. All operations on the Map should
   * be done holding a synchronization lock on poolLock.
   */
  protected Map            threads;

  /**
   * This object delegates the creation of threads to the factory object
   * referenced by this variable.
   */
  private ThreadFactoryIF  threadFactory           = new DefaultThreadFactory();

  private Log              log;

  /**
   * Construct a new pool with all default settings
   */
  public ThreadPoolServiceImpl(LogService logService) {
    log = logService.getLog("org.exoplatform.services.threadpool");
    maximumPoolSize = DEFAULT_MAXIMUMPOOLSIZE;
    handOff = new Queue();
    runWhenBlocked();
    threads = new HashMap();
  } // constructor()

  /**
   * Return the maximum number of threads to simultaneously execute New requests
   * are handled according to the current blocking policy once this limit is
   * exceeded.
   */
  public int getMaximumPoolSize() {
    return maximumPoolSize;
  } // getMaximumPoolSize

  /**
   * Set the maximum number of threads to use. Decreasing the pool size will not
   * immediately kill existing threads, but they may later die when idle.
   * 
   * @throws IllegalArgumentException if less or equal to zero. (It is not
   *           considered an error to set the maximum to be less than than the
   *           normal. However, in this case there are no guarantees about
   *           behavior.)
   */
  public void setMaximumPoolSize(int newMaximum) {
    if (newMaximum <= 0)
      throw new IllegalArgumentException();
    maximumPoolSize = newMaximum;
  } // setMaximumPoolSize(int)

  /**
   * Return the normal number of threads to simultaneously execute. (Default
   * value is 1). If fewer than the mininum number are running upon reception of
   * a new request, a new thread is started to handle this request.
   */
  public int getNormalPoolSize() {
    return normalPoolSize;
  } // getNormalPoolSize()

  /**
   * Set the normal number of threads to use.
   * 
   * @throws IllegalArgumentException if less than zero. (It is not considered
   *           an error to set the normal to be greater than the maximum.
   *           However, in this case there are no guarantees about behavior.)
   */
  public void setNormalPoolSize(int newNormal) {
    if (newNormal < 0) {
      throw new IllegalArgumentException();
    } // if
    normalPoolSize = newNormal;
  } // setNormalPoolSize(int)

  /**
   * Return the current number of active threads in the pool. This number is
   * just a snaphot, and may change immediately.
   */
  public int getPoolSize() {
    return poolSize;
  } // getPoolSize()

  /**
   * Set the object that will be used to create threads.
   */
  public void setThreadFactory(ThreadFactoryIF newValue) {
    threadFactory = newValue;
  } // setThreadFactory(ThreadFactoryIF)

  /**
   * Return the current thread factory object.
   */
  protected ThreadFactoryIF getThreadFactory() {
    return threadFactory;
  } // getThreadFactory()

  /**
   * Create and start a thread to handle a new task. Call only when holding
   * poolLock.
   */
  protected void addThread(Runnable task) {
    ++poolSize;
    Worker worker = new Worker(task);
    Thread thread = getThreadFactory().createThread(worker);
    threads.put(worker, thread);
    thread.start();
  } // addThread(Runnable)

  /**
   * Create and start up to numberOfThreads threads in the pool.
   * 
   * @return The actual number of threads created. This may be less than the
   *         number of threads requested if creating more would exceed maximum
   *         pool size.
   */
  public int createThreads(int numberOfThreads) {
    int ncreated = 0;
    for (int i = 0; i < numberOfThreads; ++i) {
      synchronized (poolLock) {
        if (getPoolSize() < getMaximumPoolSize()) {
          ++ncreated;
          addThread(null);
        } else {
          break;
        } // if
      } // synchronlized
    } // for
    return ncreated;
  } // createThreads

  /**
   * Interrupt all threads in the pool, causing them all to terminate. Threads
   * will terminate sooner if the executed tasks themselves respond to
   * interrupts.
   */
  public void interruptAll() {
    // Synchronized to avoid concurrentModification exceptions
    synchronized (poolLock) {
      for (Iterator it = threads.values().iterator(); it.hasNext();) {
        Thread t = (Thread) (it.next());
        t.interrupt();
      } // for
    } // synchronized
  } // interruptAll()

  /**
   * Remove all unprocessed tasks from pool queue, and return them in a
   * java.util.List. It should normally be used only when there are not any
   * active clients of the pool (otherwise you face the possibility that the
   * method will loop pulling out tasks as clients are putting them in.) This
   * method can be useful after shutting down a pool (via interruptAll) to
   * determine whether there are any pending tasks that were not processed. You
   * can then, for example execute all unprocessed tasks via code along the
   * lines of:
   * 
   * <pre>
   * List tasks = pool.drain();
   * for (Iterator it = tasks.iterator(); it.hasNext();)
   *   ((Runnable) (it.next())).run();
   * </pre>
   */
  public List drain() {
    boolean wasInterrupted = false;
    Vector tasks = new Vector();
    for (;;) {
      try {
        Object x = handOff.get(0);
        if (x == null)
          break;
        else
          tasks.addElement(x);
      } catch (InterruptedException ex) {
        wasInterrupted = true; // postpone re-interrupt until drained
      } // try
    } // for
    if (wasInterrupted)
      Thread.currentThread().interrupt();
    return tasks;
  } // drain()

  /**
   * Return the number of milliseconds to keep threads alive waiting for new
   * tasks. A negative value means to wait forever. A zero value means not to
   * wait at all.
   */
  public synchronized long getMaxIdleTime() {
    return maxIdleTime;
  } // getMaxIdleTime()

  /**
   * Set the number of milliseconds to keep threads alive waiting for new tasks.
   * A negative value means to wait forever. A zero value means not to wait at
   * all.
   */
  public synchronized void setMaxIdleTime(long msecs) {
    maxIdleTime = msecs;
  } // setMaxIdleTime(long)

  /**
   * Called upon termination of worker thread *
   */
  protected void workerDone(Worker w) {
    synchronized (poolLock) {
      --poolSize;
      threads.remove(w);
    } // synchronized
  } // sooner

  /**
   * get a task from the handoff queue *
   */
  protected Runnable getTask() throws InterruptedException {
    long waitTime = getMaxIdleTime();
    if (waitTime >= 0) {
      return (Runnable) (handOff.get(waitTime));
    } else {
      return (Runnable) (handOff.get());
    } // if
  } // getTask()

  /**
   * Class defining the basic run loop for pooled threads.
   */
  protected class Worker implements Runnable {
    protected Runnable firstTask;

    Worker(Runnable firstTask) {
      this.firstTask = firstTask;
    } // constructor(Runnable)

    public void run() {
      try {
        Runnable task = firstTask;
        firstTask = null;
        if (task != null) {
          task.run();
        } // if
        // Continue working until max lowered
        while (getPoolSize() <= getMaximumPoolSize()) {
          task = getTask();
          if (task != null) {
            task.run();
          } else {
            break;
          } // if
        } // while
      } catch (InterruptedException e) {
        // let this just fall through so the thread
        // dies a quiet death.
      } finally {
        workerDone(this);
      } // try
    } // run()
  } // class Worker

  /**
   * Class for actions to take when execute() blocks. Uses Strategy pattern to
   * represent different actions. You can add more in subclasses, and/or create
   * subclasses of these. If so, you will also want to add or modify the
   * corresponding methods that set the current blockedExectionStrategy.
   */
  protected interface BlockedExecutionStrategy {
    /**
     * Return true if successfully handled so, execute should terminate; else
     * return false if execute loop should be retried.
     */
    public boolean blockedAction(Runnable task);
  } // interface BlockedExecutionStrategy

  /**
   * Class defining Run action *
   */
  protected class RunWhenBlocked implements BlockedExecutionStrategy {
    public boolean blockedAction(Runnable task) {
      task.run();
      return true;
    } // blockedAction(Runnable)
  } // class RunWhenBlocked

  /**
   * Class defining Wait action *
   */
  protected class WaitWhenBlocked implements BlockedExecutionStrategy {
    public boolean blockedAction(Runnable task) {
      try {
        handOff.put(task);
      } catch (InterruptedException ex) {
        Thread.currentThread().interrupt(); // propagate
      } // try
      return true;
    } // blockedAction(Runnable)
  } // class WaitWhenBlocked

  /**
   * Class defining Discard action *
   */
  protected class DiscardWhenBlocked implements BlockedExecutionStrategy {
    public boolean blockedAction(Runnable task) {
      return true;
    } // blockedAction(Runnable)
  } // class DiscardWhenBlocked

  /**
   * The current strategy *
   */
  protected BlockedExecutionStrategy blockedExecutionStrategy;

  /**
   * Get the strategy for blocked execution *
   */
  protected synchronized BlockedExecutionStrategy getBlockedExecutionStrategy() {
    return blockedExecutionStrategy;
  } // getBlockedExecutionStrategy()

  /**
   * Set the policy for blocked execution to be that the current thread executes
   * the task if there are no available threads in the pool.
   */
  public synchronized void runWhenBlocked() {
    blockedExecutionStrategy = new RunWhenBlocked();
  } // runWhenBlocked()

  /**
   * Set the policy for blocked execution to be to wait until a thread is
   * available.
   */
  public synchronized void WhenBlocked() {
    blockedExecutionStrategy = new WaitWhenBlocked();
  } // WaitWhenBlocked()

  /**
   * Set the policy for blocked execution to be to return without executing the
   * request
   */
  public synchronized void discardWhenBlocked() {
    blockedExecutionStrategy = new DiscardWhenBlocked();
  } // discardWhenBlocked()

  /**
   * Arrange for the given task to be executed by a thread in this pool. The
   * method normally returns when the task has been handed off for (possibly
   * later) execution.
   */
  public void execute(Runnable task) throws InterruptedException {
    log.debug("execute method called");
    while (true) {
      synchronized (poolLock) {
        // Ensure normal number of threads
        if (getPoolSize() < getNormalPoolSize()) {
          addThread(task);
          return;
        } // if

        // Try to give to existing thread
        if (handOff.put(task, 0)) {
          return;
        } // if put

        // There was no immediately available thread,
        // so try to add a new thread to pool.
        if (getPoolSize() < getMaximumPoolSize()) {
          addThread(task);
          return;
        } // if maximumPoolSize
      } // synchronized

      // Cannot hand off and cannot create -- ask for help
      if (getBlockedExecutionStrategy().blockedAction(task)) {
        return;
      } // if blockedAction
    } // while
  } // execute(Runnable)

}
