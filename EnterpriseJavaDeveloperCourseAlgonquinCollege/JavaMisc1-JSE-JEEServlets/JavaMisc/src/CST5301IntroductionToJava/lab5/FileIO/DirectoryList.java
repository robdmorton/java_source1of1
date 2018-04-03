package CST5301IntroductionToJava.lab5.FileIO;

import java.io.*;

public class DirectoryList {

	public static void main(String[] args) {

		String directoryName; // Directory name entered by the user.
		File directory; // File object referring to the directory.

		TextIO.put("Enter a directory name: ");
		directoryName = TextIO.getln().trim();
		directory = new File(directoryName);

		if (directory.isDirectory() == false) {
			// Program needs a directory name. Print an error message.
			if (directory.exists() == false)
				TextIO.putln("There is no such directory!");
			else
				TextIO.putln("That file is not a directory.");
		} else {
			// List the contents of directory, with no indentation
			// at the top level.
			listContents(directory, "");
		}

	} // end main()

	static void listContents(File dir, String indent) {
		// A recursive subroutine that lists the contents of
		// the directory dir, including the contents of its
		// subdirectories to any level of nesting. It is assumed
		// that dir is in fact a directory. The indent parameter
		// is a string of blanks that is prepended to each item in
		// the listing. It grows in length with each increase in
		// the level of directory nesting.
		String[] files; // List of names of files in the directory.
		TextIO.putln(indent + "Directory \"" + dir.getName() + "\":");
		TextIO.putln(indent + "Absolute Path \"" + dir.getAbsoluteFile()
				+ "\":");
		indent += "  "; // Increase the indentation for listing the contents.
		files = dir.list();
		for (int i = 0; i < files.length; i++) {
			// If the file is a directory, list its contents
			// recursively. Otherwise, just print its name.
			File f = new File(dir, files[i]);
			if (f.isDirectory())
				listContents(f, indent);
			else
				TextIO.putln(indent + files[i]);
		}
	} // end listContents()

} // end class RecursiveDirectoryList
