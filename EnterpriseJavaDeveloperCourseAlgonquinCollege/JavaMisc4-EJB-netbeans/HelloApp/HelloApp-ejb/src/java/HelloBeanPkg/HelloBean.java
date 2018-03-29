/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HelloBeanPkg;

import javax.ejb.Stateless;

/**
 *
 * @author rmorton
 */
@Stateless
public class HelloBean implements HelloBeanRemote {

    public String greeting() {
        System.out.println("+++ Inside HelloBean");
        return "Hello from HelloBean";
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    
}
