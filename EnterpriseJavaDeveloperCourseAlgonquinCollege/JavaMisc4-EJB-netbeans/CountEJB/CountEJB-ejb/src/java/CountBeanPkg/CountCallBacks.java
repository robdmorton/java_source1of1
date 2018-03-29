/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package CountBeanPkg;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.interceptor.InvocationContext;

/**
 *
 * @author rmorton
 */
public class CountCallBacks {
    
    public CountCallBacks() {
    }

    //Called by the container after construction
    @PostConstruct
    public void construct(InvocationContext ctx) throws Exception {
        System.out.println("cb:construct() ");
        ctx.proceed();
    }

    //Called by the container after activation
    @PostActivate
    public void activate(InvocationContext ctx) throws Exception {
        System.out.println("cb:activate()");
        ctx.proceed();
    }

    //Called by the container before passivation
    @PrePassivate
    public void passivate(InvocationContext ctx) throws Exception {
        System.out.println("cb:passivate()");
        ctx.proceed();
    }

    //Called by the container before destruction
    @PreDestroy
    public void destroy(InvocationContext ctx) throws Exception {
        System.out.println("cb:destroy()");
        ctx.proceed();
    }
}
