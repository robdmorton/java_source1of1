/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CountBeanPkg;

import CountBeanPkg.CountBeanRemote;
import javax.ejb.Remote;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.interceptor.Interceptors;

/**
 *
 * @author rmorton
 */
@Stateful
@Remote(CountBeanPkg.CountBeanRemote.class)
@Interceptors(CountBeanPkg.CountCallBacks.class)
public class CountBean implements CountBeanRemote {
    private int val;

    @Override
    public int count() {
        System.out.println("count()");
        return ++val;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void set(int val) {
        this.val = val;
        System.out.println("set()");
    }

    @Remove
    @Override
    public void remove() {
        System.out.println("remove()");
    }
    
}
