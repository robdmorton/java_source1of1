package intermediateJavaProgramming.lab2;

import java.io.File;

public class Lab2 
{
  //main method
  public static void main(String[] args)
  {  
    File file = new File("J:\\eclipsePortable\\Data\\workspace\\LearningJava");
//    Dir d = new Dir();
//    d.printAttributes(file);
//    if(file.isDirectory())
//    {
//    	System.out.println();
//    	d.printDirectory(file);
//    }
    
    if(file.isDirectory())
    {
      File contents[] = null;
      contents = file.listFiles();
      for(int ctr=1;ctr<contents.length;ctr++)
      {
        System.out.println(contents[ctr].getName());
      }
    }

  }
  
  public void pintDir(String folderName)
  {
    File file = new File(folderName);
    if(file.isDirectory())
    {
      File contents[] = null;
      contents = file.listFiles();
      for(int ctr=1;ctr<contents.length;ctr++)
      {
        contents[ctr].getName();
      }
    }
  }
}
