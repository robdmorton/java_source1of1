package intermediateJavaProgramming.lab2;

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
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.SequenceInputStream;
import java.util.Arrays;

class Concatenate {
	BufferedReader getConcatenatedFiles(String args[]) throws IOException {
		InputStream current = null;
		boolean firstStream = false;

		for (int i = 0; i < args.length; i++) {
			FileInputStream fis = new FileInputStream(args[i]);
			if (firstStream == false) {
				firstStream = true;
				current = fis;
				continue;
			}
			current = new SequenceInputStream(current, fis);
		}
		return new BufferedReader(new InputStreamReader(current));
	}

	public static void main(String args[]) {
//		if (args.length == 0)
//			System.exit(0);
		
		String[] temp = {"-l","J:\\eclipsePortable\\Data\\workspace\\LearningJava\\xanadu.txt"};
		args = temp;
		
    boolean printLineNumbers = false;
    String[] fileList = args.clone();
    String[] realFileList = Arrays.copyOfRange(args, 1, args.length);

    if(args[0].contentEquals("-l"))
    {
      printLineNumbers = true;
      fileList=realFileList;
    }
    
		Concatenate concat = new Concatenate();
		try {
			String input = null;
			
			String curDir = System.getProperty("user.dir");
			System.out.println("pwd: " + curDir);
			
			BufferedReader br = concat.getConcatenatedFiles(fileList);
			LineNumberReader lnr = new LineNumberReader(br);
			while ((input = lnr.readLine()) != null) {
			  if(printLineNumbers)
			  {
	        System.out.println(lnr.getLineNumber() + " " + input);
			  }
			  else
			  {
          System.out.println(input);
			  }
			}
			br.close();
		} catch (IOException ioex) {
			System.out.println(ioex.getMessage());
		}
	}
}
