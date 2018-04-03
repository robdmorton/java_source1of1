package ws.client;

import java.net.*;
import java.io.*;
/**
 * @author jlarstone
 *
 * Simple command line client program used to
 * transmit a XML file to a service and receive
 * XML in return.
 */
public class WSClient {

	public static void main(String[] args) {
		if (args.length < 2) {
			System.out.println("Usage:  java ws.client.WSClient URL Filename");
			System.exit(1);
		}

		try {
			// save command line arguments
			URL url = new URL(args[0]);
			String document = args[1];
			
			// get length of the input file and
			// create a buffer to hold the file characters.
			File file = new File(document);
			char[] buffer = new char[(int)file.length()];
			
			// get a FileReader for the input document
			FileReader fr = new FileReader(file);
			
			// read the file all in one go
			int bytes_read = 0;
			if ((bytes_read = fr.read(buffer)) != -1) {
				
				// open service connection
				URLConnection urlc = url.openConnection();
				
				// we assume its an xml document we are sending
				urlc.setRequestProperty("Content-Type", "text/xml");
				
				// indicate we are both sending and receiving
				urlc.setDoOutput(true);
				urlc.setDoInput(true);
				
				// get a PrintWriter for delivering character data
				PrintWriter pw = new PrintWriter(urlc.getOutputStream());

				// send the xml document to the service
				pw.write(buffer, 0, bytes_read);
				
				// close output
				pw.close();

				// get a BufferedReader to receive the output
				BufferedReader in =
					new BufferedReader(
						new InputStreamReader(urlc.getInputStream()));
				
				// get each line of response and output to the console
				String inputLine;
				while ((inputLine = in.readLine()) != null)
					System.out.println(inputLine);

				// close input
				in.close();
			}
		} catch (Exception e) {
			// if there's a document or server error it will show
			// up here as well as local IO errors.
			e.printStackTrace();
		}
	}
}
