package org.exoplatform.services.threadpool.test;

import org.exoplatform.container.PortalContainer;
import org.exoplatform.services.threadpool.impl.ThreadPoolServiceImpl;
import org.exoplatform.test.BasicTestCase;

/**
 * Created by the Exo Development team.
 * Author : Mestrallet Benjamin
 * benjamin.mestrallet@exoplatform.com
 */
public class TestSimpleThreadPoolService extends BasicTestCase{
  private ThreadPoolServiceImpl service_;

  private static int counter = 0;

  public TestSimpleThreadPoolService(String name) {
    super(name);
  }

  protected String getDescription() {
    return "Test the Thread pool service";
  }

	public void setUp() throws Exception {
	  PortalContainer manager  = PortalContainer.getInstance();
		service_ = 
			(ThreadPoolServiceImpl) manager.getComponentInstanceOfType(ThreadPoolServiceImpl.class) ;
	}

  public void testSimpleThreadPoolService() throws InterruptedException {
    service_.setMaximumPoolSize(10);
    for(int i= 0; i < 20; i++){
      Runnable r = new SimpleRunnable();
      service_.execute(r);
    }
  }

  public class SimpleRunnable implements Runnable {
    public void run() {
      System.out.println("In run method of for threads number : " + counter);
      counter++;
    }
  }
}
