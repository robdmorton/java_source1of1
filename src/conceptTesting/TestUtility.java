package conceptTesting;

import java.util.concurrent.ThreadPoolExecutor;

public class TestUtility {

  static public void printer(String aInStr) {
    System.out.println(System.currentTimeMillis() + ":" + "(" + Thread.currentThread().getName()
        + ")" + ":" + aInStr);
  }

  static public void threadPoolMonitor(String inPoolName, ThreadPoolExecutor tpe) {
    String input = String.format("[thread pool monitor:%s] [%d/%d] Active: %d, Completed: %d, "
        + "#Total Tasks Submitted to Q: %d, Enqueued: %d, Remaining Capacity in Q: %d, isShutdown: %s, isTerminated: %s",
        inPoolName, tpe.getPoolSize(), tpe.getCorePoolSize(), tpe.getActiveCount(),
        tpe.getCompletedTaskCount(), tpe.getTaskCount(), tpe.getQueue().size(),
        tpe.getQueue().remainingCapacity(), tpe.isShutdown(), tpe.isTerminated());

    printer(input);
  }

}
