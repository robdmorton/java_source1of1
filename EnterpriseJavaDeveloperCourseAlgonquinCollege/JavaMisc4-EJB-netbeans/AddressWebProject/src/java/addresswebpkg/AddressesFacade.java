/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package addresswebpkg;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author rmorton
 */
@Stateless
public class AddressesFacade extends AbstractFacade<Addresses> {
    @PersistenceContext(unitName = "AddressWebProjectPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public AddressesFacade() {
        super(Addresses.class);
    }
    
}
