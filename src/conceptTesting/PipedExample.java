package conceptTesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedReader;
import java.io.PipedWriter;

class PipedExample
{
  static BufferedReader system_in = new BufferedReader
      (new InputStreamReader(System.in));
  public static void main(String argv[])
  {
    PipedReader pr = new PipedReader();
    PipedWriter pw = null;
    try {
      pw = new PipedWriter(pr);
    }
    catch (IOException e)
    {
      System.err.println(e);
    }
    // Create it {
    // Read in three hotels
    for (int i = 0; i < 3; i++)
    {
      Hotel a_hotel = new Hotel();
      a_hotel.input(system_in);
      a_hotel.write_to_pw(pw);
    }
  
    // Print it
    {
      char [] buffer = new char[1000];
      int length = 0;
      try
      {
        length = pr.read(buffer);
      }
      catch (IOException e)
      {
        System.err.println(e);
      }
      String output =new String(buffer, 0, length);
      System.out.println("String is ");
      System.out.println(output);
    }

  }
}

class Hotel
{
  private String name;
  private int rooms;
  private String location;
  
  boolean input(BufferedReader in)
  {
    try
    {
      System.out.println("Name: ");
      name = in.readLine();
      System.out.println("Rooms: ");
      String temp = in.readLine();
      rooms = to_int(temp);
      System.out.println("Location: ");
      location = in.readLine();
    }
    catch(IOException e)
    {
      System.err.println(e);
      return false;
    }
    return true;
  }
  
  boolean write_to_pw(PipedWriter pw)
  {
    try
    {
      pw.write(name);
      Integer i = new Integer(rooms);
      pw.write(i.toString());
      pw.write(location);
      pw.write("\n");
      // red font indicates that an actual backslash n (carriage return character)
      // should be inserted in the code.
    }
    catch(IOException e)
    {
      System.err.println(e);
      return false;
    }
    return true;
  }
  
  void debug_print()
  {
    System.out.println("Name :" + name +
        ": Rooms : " + rooms + ": at :" + location+ ":");
  }
  
  static int to_int(String value)
  {
    int i = 0;
    try
    {
      i = Integer.parseInt(value);
    }
    catch(NumberFormatException e)
    {}
    return i;
  }
}