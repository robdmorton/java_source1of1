package CST6526IntermediateJavaProgramming.lab6.ArrayList;

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
		
		List <Customer> lCustomer = new  ArrayList<Customer>();
		File file = new File(args[0]);
		try {
			CustomerReader cr = new CustomerReader(new FileReader(file));
			Customer customer = null;
			while ((customer = cr.readCustomer()) != null ) {
				customer.parseCustomer();
				lCustomer.add(customer); 
			}
			cr.close();
		} catch ( IOException ioex ) {
			System.err.println(ioex.getMessage());
		} catch ( Exception ex ) {
			System.err.println(ex.getMessage());
		}
		System.out.println("Printing Customer list via for loop");
		for (Customer myCustomer: lCustomer) {
			System.out.println(myCustomer);
		}
		
		Iterator <Customer> iCustomer = lCustomer.iterator();
		System.out.println("\ncustomer list via Iterator");
		
		while (iCustomer.hasNext()) {
			System.out.println(iCustomer.next());
		}
	} // main
}
