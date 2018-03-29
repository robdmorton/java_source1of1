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
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class FileCopyChar {
	public static void main(String args[]) {
		try {
			FileReader fread = new FileReader("file_to_copy.txt");
			FileWriter fwrite = new FileWriter("copy_of_file.txt");

			int ch = 0;
			while ((ch = fread.read()) != -1)
			{
//			  System.out.printf("ch: 0x%x\n", ch);
				fwrite.write(ch);
			}

			fread.close();
			fwrite.flush();
			fwrite.close();
		} catch (IOException ioex) {
			System.out.println("File copy failed: " + ioex.getMessage());
		}
	}
}
