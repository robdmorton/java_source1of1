package intermediateJavaProgramming.lab4;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class CustomerReadTest {
  public static void main(String args[]) {
    if (args.length == 0) {
      System.err.println("no customer file specified");
      System.exit(1);
    }

    String curDir = System.getProperty("user.dir");
    System.out.println("pwd: " + curDir);

    File file = new File(args[0]);
    try {
      CustomerReader cr = new CustomerReader(new FileReader(file));

      Customer customer = null;
      while ((customer = cr.readCustomer()) != null) {
        customer.parseCustomer();
        System.out.println(customer);
      }
      cr.close();
    } catch (IOException ioex) {
      System.err.println(ioex.getMessage());
    } catch (Exception ex) {
      System.err.println(ex.getMessage());
    }
  }
}
