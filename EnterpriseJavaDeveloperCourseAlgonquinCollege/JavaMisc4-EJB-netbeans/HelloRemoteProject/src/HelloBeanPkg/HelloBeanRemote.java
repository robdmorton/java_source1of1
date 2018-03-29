/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package HelloBeanPkg;

import javax.ejb.Remote;

/**
 *
 * @author rmorton
 */
@Remote
public interface HelloBeanRemote {

    String greeting();
    
}
