/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package helloappclientproject;

import HelloBeanPkg.HelloBeanRemote;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author rmorton
 */
public class HelloAppClient {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NamingException {
        // TODO code application logic here
		Context ctx = new InitialContext();
		HelloBeanRemote hello = (HelloBeanRemote) ctx.lookup("HelloBeanPkg.HelloBeanRemote");
		System.out.println("non-annotated version: " + hello.greeting());
     }
}
