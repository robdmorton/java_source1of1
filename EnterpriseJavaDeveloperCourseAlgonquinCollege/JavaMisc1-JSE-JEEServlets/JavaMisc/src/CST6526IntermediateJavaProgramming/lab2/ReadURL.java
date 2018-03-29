package CST6526IntermediateJavaProgramming.lab2;

/*
 * Created on Feb 23, 2005
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

/**
 * @author wce00053
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
import java.io.*;
import java.net.*;

class ReadURL {  
  public static void main(String args[]) throws IOException {
	if ( args.length == 0 || args.length > 2)
	  System.exit(0);
	URL url = new URL(args[0]);
	URLConnection uc = url.openConnection();
	BufferedReader br = 
	  new BufferedReader(new InputStreamReader(uc.getInputStream()));
	String input = null;
	
  File f = new File(args[1]);
  if(!f.exists())
  {
    f.createNewFile();
  }
	PrintWriter pw =
	  new PrintWriter(new BufferedWriter(new FileWriter(args[1])));
	while ( (input = br.readLine()) != null ) 
	{
	  pw.println(input);
//	  System.out.println(input);
	}
	pw.close();
  }
}
