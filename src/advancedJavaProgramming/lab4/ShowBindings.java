package advancedJavaProgramming.lab4;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NameClassPair;
import javax.naming.NamingEnumeration;

/**
 * This programs shows all RMI bindings.
 */
public class ShowBindings {
  public static void main(String[] args) {
    try {
      Context namingContext = new InitialContext();
      NamingEnumeration<NameClassPair> e = namingContext.list("rmi://localhost/");
      while (e.hasMore())
        System.out.println(e.next().getName());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
