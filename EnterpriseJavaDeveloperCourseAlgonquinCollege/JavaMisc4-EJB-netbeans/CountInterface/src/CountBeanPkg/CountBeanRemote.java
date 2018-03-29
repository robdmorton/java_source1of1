/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CountBeanPkg;

import javax.ejb.Remote;

/**
 *
 * @author rmorton
 */
@Remote
public interface CountBeanRemote {

    int count();

    void set(int val);

    void remove();
    
}
