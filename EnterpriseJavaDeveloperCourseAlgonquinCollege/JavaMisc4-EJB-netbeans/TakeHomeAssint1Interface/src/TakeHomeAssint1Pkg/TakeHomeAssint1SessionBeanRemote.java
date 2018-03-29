/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package TakeHomeAssint1Pkg;

import java.math.BigDecimal;
import javax.ejb.Remote;

/**
 *
 * @author rmorton
 */
@Remote
public interface TakeHomeAssint1SessionBeanRemote {

    BigDecimal dollarToYen(BigDecimal parameter);

    BigDecimal yenToDollar(BigDecimal parameter);

    BigDecimal yenToEuro(BigDecimal parameter);

    BigDecimal euroToYen(BigDecimal parameter);
    
}
