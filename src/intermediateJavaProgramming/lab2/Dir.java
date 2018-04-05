package intermediateJavaProgramming.lab2;

/*
 * Created on Feb 23, 2005
 *
 * To change the template for this generated file go to Window>Preferences>Java>Code Generation>Code
 * and Comments
 */

/**
 * @author wce00053
 *
 *         To change the template for this generated type comment go to Window>Preferences>Java>Code
 *         Generation>Code and Comments
 */
import java.io.File;
import java.text.DateFormat;
import java.util.Date;

class Dir {

  static int level = 0;

  void printAttributes(File file) {
    if (file == null || file.exists() == false)
      return;

    // handle date
    Date date = new Date(file.lastModified());
    DateFormat dataformat = DateFormat.getDateInstance(DateFormat.LONG);

    // System.out.println(file.getName() + (file.isDirectory()?"d":"f")
    // + file.length() + file.lastModified());
    System.out.printf("%30s %10s %5d %20s\n", file.getName(),
        file.isDirectory() ? "directory" : "file", file.length(), dataformat.format(date));
  }

  void printDirectory(File directory) {
    if (directory == null || directory.exists() == false)
      return;

    File contents[] = null;
    if (directory.isDirectory())
      contents = directory.listFiles();
    else
      contents = new File[] {directory};
    for (int i = 0; i < contents.length; i++) {
      System.out.print("  ");
      printAttributes(contents[i]);
      if (contents[i].isDirectory()) {
        level++;

        System.out.println("");
        System.out.println("level:" + level);
        System.out.println("");
        System.out.println(contents[i].getName() + ":");
        printDirectory(contents[i]);
      }
    }
  }

  void printListing(String args[]) {
    if (args == null || args.length == 0)
      printDirectory(new File("."));
    else {
      for (int i = 0; i < args.length; i++) {
        if (args[i] != null) {
          System.out.println(args[i] + ":");
          printDirectory(new File(args[i]));
        }
      }
    }
  }

  public static void main(String args[]) {
    Dir dir = new Dir();
    dir.printListing(args);
  }
}
