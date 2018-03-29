/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CallByRefOrByValue;

import javax.ejb.Remote;

/**
 *
 * @author rmorton
 */
@Remote
public interface CallByRefOrByValueRemote {

    void change(int myarray[]);
    
}
