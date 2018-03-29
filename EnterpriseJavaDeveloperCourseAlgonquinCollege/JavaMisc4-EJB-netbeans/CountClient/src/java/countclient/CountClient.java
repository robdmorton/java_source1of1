/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package countclient;

import CountBeanPkg.CountBeanRemote;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 *
 * @author rmorton
 */
public class CountClient {

    public static final int noOfClients = 3;

    public static void main(String[] args) {
        try {
            /* Get a reference to the bean */
            Context ctx = new InitialContext(System.getProperties());

            /* An array to hold the Count beans */
            CountBeanRemote count[] = new CountBeanRemote[noOfClients];
            int countVal = 0;

            /* Create and count() on each member of array */
            System.out.println("Instantiating beans...");
            for (int i = 0; i < noOfClients; i++) {
                count[i] = (CountBeanRemote) ctx.lookup(CountBeanRemote.class.getName());

                /* initialize each bean to the current count value */
                count[i].set(countVal);

                /* Add 1 and print */
                countVal = count[i].count();
                System.out.println(countVal);

                /*  Sleep for 1/2 second */
                Thread.sleep(100);
            } // end for loop
            /*
             * Let's call count() on each bean to  make sure the
             * beans were passivated and activated properly.
             */
            System.out.println("Calling count() on beans...");
            for (int i = 0; i < noOfClients; i++) {

                /* Add 1 and print */
                countVal = count[i].count();
                System.out.println(countVal);

                /* call remove to let the container dispose of the bean */
                count[i].remove();

                /*  Sleep for 1/2 second */
                Thread.sleep(50);
            } // end for loop
        } // end try
        catch (Exception e) {
            e.printStackTrace();
        }
    } // end main()
} //end class
