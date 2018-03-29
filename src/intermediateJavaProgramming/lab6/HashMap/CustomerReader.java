package intermediateJavaProgramming.lab6.HashMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class CustomerReader extends BufferedReader {
	public CustomerReader(Reader in) {
		super(in);
	}
	
	public CustomerReader(Reader in, int sz) {
		super(in, sz);
	}

	public Customer readCustomer() throws IOException {
		String line = readLine();
		if ( line == null )
			return null;
		return new Customer(line);
	}
}
