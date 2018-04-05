package intermediateJavaProgramming.lab6.HashMap;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class CustomerReadTest {
  public static void main(String args[]) {
    if (args.length == 0) {
      System.err.println("no customer file specified");
      System.exit(1);
    }

    Map<String, Customer> mCustomer = new HashMap<String, Customer>();
    File file = new File(args[0]);
    try {
      CustomerReader cr = new CustomerReader(new FileReader(file));
      Customer customer = null;
      while ((customer = cr.readCustomer()) != null) {
        customer.parseCustomer();
        mCustomer.put(customer.getAccountNumber(), customer);
      }
      cr.close();
    } catch (IOException ioex) {
      System.err.println(ioex.getMessage());
    } catch (Exception ex) {
      System.err.println(ex.getMessage());
    }
    System.out.println("Key list");
    Set<String> customerKeys = mCustomer.keySet();
    System.out.println(customerKeys);
    System.out.println("Value list");
    Collection<Customer> customerValues = mCustomer.values();
    System.out.println(customerValues);
    System.out.println("Entry List");
    Set<Map.Entry<String, Customer>> customerSet = mCustomer.entrySet();
    System.out.println(customerSet);


  } // main
}
