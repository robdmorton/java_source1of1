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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

class FileCopyByte {
	public static void main(String args[]) {
		try {
			FileInputStream fin = new FileInputStream("file_to_copy.txt");
			FileOutputStream fout = new FileOutputStream("copy_of_file.txt");

			int ch = 0;
			while ((ch = fin.read()) != -1)
			{
//		    System.out.printf("ch: %c\n", ch);
				fout.write(ch);
			}
				
			fin.close();
			fout.flush();
			fout.close();
		} catch (IOException ioex) {
			System.out.println("File copy failed: " + ioex.getMessage());
		}
	}
}
