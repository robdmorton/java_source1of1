package CST5301IntroductionToJava.lab5.FileIO;

import java.io.*;

/**
 * Example of a copying a file using binary-level (byte) I/O. Demonstrates use
 * of File Streams and the File class. Demonstrates Exceptions. To demonstrate:
 * java FileCopy FileCopy.java abc.java java FileCopy FileCopy.java abc.java
 * java FileCopy blah blah
 */
public class FileCopy {
	private String sourceFileName;
	private String destFileName;
	private String sourceFileBackupName;
	private File sourceFile;
	private File destFile;
	private File sourceFileBkup;
	private FileInputStream fis; // is-an InputStream
	private FileOutputStream fos; // is-an OutputStream
	private FileOutputStream fosBkup; // is-an OutputStream

	public FileCopy(String source, String dest) throws IOException {
		this.sourceFileName = source;
		this.destFileName = dest;
		this.sourceFileBackupName = dest + ".backup";
		init(); // init() can throw IOException
	}

	/**
	 * Intialization and checks. Construct Files using the filenames and do
	 * checks.
	 */
	private void init() throws IOException {

		sourceFile = new File(sourceFileName);
		if (!sourceFile.exists()) {
			throw new IOException("Source file does not exist");
		}
		if (sourceFile.isDirectory()) {
			throw new IOException("Directory copy is not supported");
		}
		sourceFileBkup = new File(sourceFileBackupName);
		
		destFile = new File(destFileName);
		if (destFile.isDirectory()) {
			throw new IOException("Directory copy is not supported");
		}
	}

	/**
	 * Copy the files using the filenames passed into the constructor.
	 */
	public void copy() throws IOException {

		if (destFile.exists()) {
			System.out.println("About to overwrite " + destFileName);
		}

		// Construct streams using the File objects
		fis = new FileInputStream(sourceFile);
		fos = new FileOutputStream(destFile);
		fosBkup = new FileOutputStream(sourceFileBkup);

		// now do the copying
		int sourceByte = fis.read();
		while (sourceByte != -1) { // -1 is EOF
			fos.write(sourceByte);
			fosBkup.write(sourceByte);
			byte outByte = (byte)sourceByte;
			//%s prints out the decimal representation of the byte
			//%c converts the byte into an ASCII char
			System.out.printf("%c",outByte);
			sourceByte = fis.read();
		}
		fis.close();
		fos.close();
		fosBkup.close();
	}

	/**
	 * Args[0] is source file name. Args[1] is dest file name.
	 */
	public static void main(String[] args) {
		try {
			if (args.length != 2) {
				System.out.println("usage: java FileCopy srcfile dstfile");
				System.exit(0);
			}
			FileCopy fCopy = new FileCopy(args[0], args[1]);
			fCopy.copy();
		} catch (IOException ioe) {
			System.out.println(ioe);
			System.out.println("==============================");
			ioe.printStackTrace();
		}
	}
}