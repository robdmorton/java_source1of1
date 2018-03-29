/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package labexercise5clientproject;

import javax.ejb.EJB;
import TimerSessionBeanPkg.*;

/**
 *
 * @author rmorton
 */
public class TimerSessionClient {

    @EJB
    private static TimerSessionBeanRemote timer;

    public TimerSessionClient(String[] args) {
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        TimerSessionClient client = new TimerSessionClient(args);
        client.doClient();
    }

    public void doClient() {
        try {
            long intervalDuration = 8000;
            System.out.println(
                    "Creating a timer with an interval duration of "
                    + intervalDuration + " ms.");
            timer.setTimer(intervalDuration);

            System.exit(0);
        } catch (Exception ex) {
            System.err.println("Caught an unexpected exception.");
            ex.printStackTrace();
            System.exit(1);
        }
    }
}
