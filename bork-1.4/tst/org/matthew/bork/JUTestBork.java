package org.matthew.bork;

import java.util.Arrays;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import junit.framework.TestCase;

/**
 * JUnit test cases for Bork.
 * 
 * @author Matthew Phillips
 */
public class JUTestBork extends TestCase
{
  private static final String PASSWORD = "wibble is a stupid password";
  private static final byte [] TEST_DATA =
    ("This is a line of text\n" + 
    "This is the second line of text\n" +
    "Some special characters \000\0900\0420\b\t").getBytes ();
  
  public static void main (String [] args)
  {
    junit.textui.TestRunner.run (JUTestBork.class);
  }

  public void testUnsignedConversion ()
  {
    int integer = 0xFF;
    byte ubyte = (byte)integer;
    
    assertTrue (ubyte < 0);
    assertTrue ((ubyte ^ (byte)0xFF) == (byte)0);
    assertFalse (integer == ubyte);
    assertTrue (integer == Unsigned.promote (ubyte));
    
    long longVal = 0xFFFFFFFFL;
    integer = (int)longVal;
    
    assertTrue (integer < 0);
    assertTrue ((integer ^ 0xFFFFFFFF) == 0);
    assertFalse (longVal == integer);
    assertTrue (longVal == Unsigned.promote (integer));
  }
  
  /**
   * Test encrypt/decrypt produces the same content.
   */
  public void testRoundtrip () throws Exception
  {
    // create plain text
    File plaintextFile = createFile (TEST_DATA);
    
    // encrypt
    Bork bork = new Bork (plaintextFile.getPath (), PASSWORD);
    bork.encrypt ();
    File ciphertextFile = bork.outfile;
    ciphertextFile.deleteOnExit ();
    
    assertTrue ("No encrypted file created", ciphertextFile.exists ());
    
    byte [] contents = contents (ciphertextFile);
    byte [] cipherText =
      subarray (contents, contents.length - TEST_DATA.length, TEST_DATA.length);
    
    assertFalse ("Not encrypted!", Arrays.equals (TEST_DATA, cipherText));
    
    assertTrue ("Cannot delete plaintext", plaintextFile.delete ());
    
    // decrypt
    bork = new Bork (ciphertextFile.getPath (), PASSWORD);
    bork.decrypt ();
    assertEquals (plaintextFile, bork.outfile);
    assertTrue ("No decrypted file created: " + plaintextFile,
                plaintextFile.exists ());
    
    assertTrue ("Failed to decrypt back to original",
                Arrays.equals (TEST_DATA, contents (bork.outfile)));
   
    assertEquals ("Last modified not preserved",
                  plaintextFile.lastModified (), ciphertextFile.lastModified ());
    
    ciphertextFile.delete ();
  }
  
  public void testIncorrectPassword () throws Exception
  {
    // create plain text
    File plaintextFile = createFile (TEST_DATA);
    
    // encrypt
    Bork bork = new Bork (plaintextFile.getPath (), PASSWORD);
    bork.encrypt ();
    File ciphertextFile = bork.outfile;
    ciphertextFile.deleteOnExit ();
    
    assertTrue ("No encrypted file created", ciphertextFile.exists ());
    
    byte [] contents = contents (ciphertextFile);
    byte [] cipherText =
      subarray (contents, contents.length - TEST_DATA.length, TEST_DATA.length);
    
    assertFalse ("Not encrypted!", Arrays.equals (TEST_DATA, cipherText));
    
    assertTrue ("Cannot delete plaintext", plaintextFile.delete ());
    
    // decrypt
    bork = new Bork (ciphertextFile.getPath (), PASSWORD + "x");
    
    try
    {
      bork.decrypt ();
      
      fail ("Failed to detect CRC error");
    } catch (IOException ex)
    {
      // simple check that a CRC error was detected
      assertTrue ("Failed to detect CRC error",
                  ex.getMessage ().indexOf ("CRC") != -1);
    }
    
    ciphertextFile.delete ();
  }
  
  public void testSkipAlreadyExisting () throws Exception
  {
    // create plain text
    File plaintextFile = createFile (TEST_DATA);
    File ciphertextFile = null;
    
    try
    {
      // encrypt
      Bork bork = new Bork (plaintextFile.getPath (), PASSWORD);
      bork.encrypt ();
      ciphertextFile = bork.outfile;
      ciphertextFile.deleteOnExit ();
      
      assertTrue ("No encrypted file created", bork.outfile.exists ());
      assertFalse ("Skipped incorrectly", bork.skipped);
      
      // do it again
      bork = new Bork (plaintextFile.getPath (), PASSWORD);
      bork.encrypt ();
      assertTrue ("Did not skip", bork.skipped);
    } finally
    {
      if (ciphertextFile != null)
        ciphertextFile.delete ();
      plaintextFile.delete ();
    }
  }
  
  public void testDecryptReferenceSample () throws Exception
  {
    File infile = new File ("tst/org/matthew/bork/test_data.bork");
    File outfile = new File ("tst/org/matthew/bork/test_data.txt");
    File origfile = new File ("tst/org/matthew/bork/test_data.orig.txt");
    
    outfile.deleteOnExit ();
    
    Bork bork = new Bork (infile, "wibble");
    bork.decrypt ();
    
    assertEquals (outfile, bork.outfile);
    assertTrue ("Failed to decrypt",
                Arrays.equals (contents (origfile), contents (outfile)));
  }
  
  private static File createFile (byte [] contents) throws IOException
  {
    File plaintextFile = File.createTempFile ("temp", "bork");
    plaintextFile.deleteOnExit ();
    
    FileOutputStream output = new FileOutputStream (plaintextFile);
    
    output.write (contents);
    
    output.close ();
    
    return plaintextFile;
  }

  private byte [] subarray (byte [] bytes, int start, int length)
  {
    byte [] slice = new byte [length];
    
    System.arraycopy (bytes, start, slice, 0, length);
    
    return slice;
  }

  private static byte [] contents (File file) throws IOException
  {
    byte [] buffer = new byte [(int)file.length ()];
    
    FileInputStream input = new FileInputStream (file);
    
    if (input.read (buffer) != buffer.length)
      throw new IOException ("Failed to read entire file");
    
    input.close ();
    
    return buffer;
  }
}
