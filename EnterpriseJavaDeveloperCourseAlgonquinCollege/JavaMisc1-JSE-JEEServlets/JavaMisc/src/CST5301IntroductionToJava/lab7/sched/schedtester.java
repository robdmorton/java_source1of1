/*
 * schedtest.java
 *
 * Created on October 19, 2003, 5:04 PM
 */

package CST5301IntroductionToJava.lab7.sched;
import java.lang.*; 

/**
 *
 * @author  James Tinkess
 */
// Test for preemptive vs non-preemptive scheduler behaviour 
class SchedTest implements Runnable { 
    public void run() { 
        while (true) { 
            System.out.println(Thread.currentThread().getName()); 
            try{
            Thread.sleep(10);
            }catch( InterruptedException e ){
                System.out.println("oops");
            }
        } 
    } 
} 
public class schedtester { 
    public static void main(String argv[]) { 
        SchedTest test = new SchedTest(); 
        new Thread(test, "Thread one").start(); 
        new Thread(test, "Thread two").start(); 
    } 
} 
