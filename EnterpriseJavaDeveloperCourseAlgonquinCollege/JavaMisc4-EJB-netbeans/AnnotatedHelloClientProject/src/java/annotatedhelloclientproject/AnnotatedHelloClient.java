/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package annotatedhelloclientproject;

import HelloBeanPkg.HelloBeanRemote;
import javax.ejb.EJB;


/**
 *
 * @author rmorton
 */
public class AnnotatedHelloClient {
    @EJB
    private static HelloBeanRemote helloBean;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
		System.out.println("Annotated version: " + helloBean.greeting());
    }
}
