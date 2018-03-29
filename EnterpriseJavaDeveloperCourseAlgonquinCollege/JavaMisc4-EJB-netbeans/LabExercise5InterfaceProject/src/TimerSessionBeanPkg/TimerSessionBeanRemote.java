/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TimerSessionBeanPkg;

import javax.ejb.Remote;
import javax.ejb.Timer;

/**
 *
 * @author rmorton
 */
@Remote
public interface TimerSessionBeanRemote {
    public void setTimer(long intervalDuration);

    public void timeout(Timer timer);
}
