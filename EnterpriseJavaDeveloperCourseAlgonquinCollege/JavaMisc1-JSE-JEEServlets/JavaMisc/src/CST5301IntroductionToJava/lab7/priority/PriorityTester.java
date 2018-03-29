package CST5301IntroductionToJava.lab7.priority;

import java.lang.*;
// Tests the behaviour of Thread priorities.
class SchedTest implements Runnable {
    int ttl = 10;
    public void run() {     
        while (true) {
            ttl--;
            System.out.println(Thread.currentThread().getName()+" with priority: " + Thread.currentThread().getPriority()+" has ttl: " + ttl);
            if( ttl < 1 )
                destroy();
            Thread.currentThread().yield();
        }
    }
    public void destroy(){
       System.out.println("Destroying: " + Thread.currentThread().getName());
       System.exit(0);
    }
}
public class PriorityTester {
    public static void main(String argv[]) {
        SchedTest test = new SchedTest();
        Thread t1 = new Thread(test, "Thread one");
        Thread t2 = new Thread(test, "Thread two");
        t1.setPriority(Thread.NORM_PRIORITY);
        t2.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        t2.start();
    }
}

