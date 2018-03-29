package pkg_asynch;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import pkg_asynch.AsynchronousSessionBeanRemote;

public class App {

  @EJB
  private static AsynchronousSessionBeanRemote async;

  public void invokeEjbMethods() {
      
    long startTime = System.currentTimeMillis();
    long endTime;
    async.slowMethod();
    endTime = System.currentTimeMillis();
    System.out.println("slow method invocation returned in "
        + (endTime - startTime) + " milliseconds");
    
    try {
        Thread.sleep(0);
    } catch (InterruptedException ex) {
        Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
    }
    Future<Long> retVal =
        async.slowMethodWithReturnValue();
    if (!retVal.isDone()) {
      System.out.println("isDone: " + retVal.isDone());
      System.out.println("Cancelling second method call");
      boolean cancelled = retVal.cancel(true);
      System.out.println("isDone: " + retVal.isDone());
      if(!cancelled)
      {
        System.out.println("Cancelling second method call...failed");
        System.out.println("isCancelled: " + retVal.isCancelled());
        System.out.println("isDone: " + retVal.isDone());
      }
    } else {
      try {
        System.out.println("second method call done, "
            + "return value is: " + retVal.get());
      } catch (InterruptedException ex) {
        Logger.getLogger(App.class.getName()).
            log(Level.SEVERE, null, ex);
      } catch (ExecutionException ex) {
        Logger.getLogger(App.class.getName()).
            log(Level.SEVERE, null, ex);
      }
    }
  }

  public static void main(String[] args) {
    new App().invokeEjbMethods();
  }
}
