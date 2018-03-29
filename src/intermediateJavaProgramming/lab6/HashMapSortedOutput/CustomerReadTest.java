package intermediateJavaProgramming.lab6.HashMapSortedOutput;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CustomerReadTest {
	public static void main(String args[]) {
		if ( args.length == 0 ) {
			System.err.println("no customer file specified");
			System.exit(1);
		}
		ArrayList keys = new ArrayList();
		Map <String,Customer> mCustomer = new HashMap<String,Customer>();
		File file = new File(args[0]);
		try {
			CustomerReader cr = new CustomerReader(new FileReader(file));
			Customer customer = null;
			while ((customer = cr.readCustomer()) != null ) {
				customer.parseCustomer();
				keys.add(customer.getAccountNumber());
				mCustomer.put(customer.getAccountNumber(),customer);
			}
			cr.close();
		} catch ( IOException ioex ) {
			System.err.println(ioex.getMessage());
		} catch ( Exception ex ) {
			System.err.println(ex.getMessage());
		}
		System.out.println("Key list");
		Set <String> customerKeys = mCustomer.keySet();
		System.out.println(customerKeys);
		System.out.println("Value list");
		Collection <Customer> customerValues = mCustomer.values();
		System.out.println(customerValues);
		System.out.println("Entry List");
		Set <Map.Entry<String, Customer>> customerSet = mCustomer.entrySet();
		System.out.println(customerSet);
		
		System.out.println("\nSorted by Account Number");
		Collections.sort(keys);
		System.out.println("Keys\t\tValues");
		for (int i = 0; i < keys.size(); i++) {
			String key = (String) keys.get(i);
		
			System.out.printf("%s\t\t%s\n", key, mCustomer.get(key));
		}
	} //main
}
