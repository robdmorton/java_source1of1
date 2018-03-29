/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package takehomeassint1app;

import TakeHomeAssint1Pkg.TakeHomeAssint1SessionBeanRemote;
import java.math.BigDecimal;
import javax.ejb.EJB;

/**
 *
 * @author rmorton
 */
public class TakeHomeAssint1App {
    @EJB
    private static TakeHomeAssint1SessionBeanRemote takeHomeAssint1SessionBean;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //testing the stateless session bean methods here...
        BigDecimal input;
        input = new BigDecimal(20);
        System.out.println("Annotated version: " + takeHomeAssint1SessionBean.dollarToYen(input));
        input = new BigDecimal(2000);
        System.out.println("Annotated version: " + takeHomeAssint1SessionBean.yenToDollar(input));
        input = new BigDecimal(20);
        System.out.println("Annotated version: " + takeHomeAssint1SessionBean.euroToYen(input));
        input = new BigDecimal(2000);
        System.out.println("Annotated version: " + takeHomeAssint1SessionBean.yenToEuro(input));
        
        //now...use them to convert $ to Euro and vica versa... 
        //first...$ to Euro:
        input = new BigDecimal(2000);
        System.out.println("Annotated version:($2000->Euro) " + takeHomeAssint1SessionBean.
                dollarToYen(takeHomeAssint1SessionBean.yenToEuro(input)));
        //then...Euro to $:
        input = new BigDecimal(2000);
        System.out.println("Annotated version:(2000 Euro->$)) " + takeHomeAssint1SessionBean.
                euroToYen(takeHomeAssint1SessionBean.yenToDollar(input)));
    }

}
