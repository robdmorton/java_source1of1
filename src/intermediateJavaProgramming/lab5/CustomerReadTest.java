package intermediateJavaProgramming.lab5;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class CustomerReadTest {
  public static void main(String args[]) {
    if (args.length == 0) {
      System.err.println("no customer file specified");
      System.exit(1);
    }
    // added
    ArrayList customers = new ArrayList();
    File file = new File(args[0]);
    try {
      CustomerReader cr = new CustomerReader(new FileReader(file));
      Customer customer = null;
      while ((customer = cr.readCustomer()) != null) {
        // added
        customers.add(customer);
      }
      cr.close();
    } catch (IOException ioex) {
      System.err.println(ioex.getMessage());
    } catch (Exception ex) {
      System.err.println(ex.getMessage());
    }

    Iterator it = customers.iterator();
    while (it.hasNext()) {
      Customer c = (Customer) it.next();
      c.configure();
      System.out.println(c);
    }
  }
}
