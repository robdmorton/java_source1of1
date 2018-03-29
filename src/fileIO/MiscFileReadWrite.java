package fileIO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

import conceptTesting.TestUtility;


public class MiscFileReadWrite {

  public void testRandomAccessOfFlatFile()
  {
    String srcName = "C:\\Users\\rob\\Desktop\\temp\\photosFromBkup\\1966\\CertificateOfBaptism.jpg";
    srcName = "C:\\Users\\rob\\Desktop\\bob.jpg";
    srcName = "H:\\Google Drive\\work-career\\03-ProfDev\\10-misc\\java_source1of1\\eclipseWorkspace\\misc\\bob.jpg";
    srcName = "inputTestFile.txt";
    srcName = "Cute munchkin baby kitten talks too much - YouTube[via torchbrowser.com].mp4";
    String outName = "Cute munchkin baby kitten talks too much - YouTube[via torchbrowser.com]-badByteInserted.mp4";
    RandomAccessFile randomAccessInputFile = null;
    File fileOut = new File("outputTestFile.txt");
    fileOut = new File(outName);
    FileOutputStream writer=null;
    boolean badByteInserted = false;
    int badBytesInserted = 0;

    File srcFile = new File(srcName);
    try {
      randomAccessInputFile = new RandomAccessFile(srcFile, "r");
      writer = new FileOutputStream(fileOut);
    } catch (FileNotFoundException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

    int currentBlockSize=10000;
    int currentPos=0;
    byte[] b = new byte[currentBlockSize];
    try {
      long fileSize = randomAccessInputFile.length();

      for( int i = currentPos ; i <= fileSize  ; i = i + currentBlockSize )
      {
        // use seek to advance to any byte offset location in file
        randomAccessInputFile.seek( i );
        TestUtility.printer( "i=" + i);
        try
        {
          TestUtility.printer( "(rFile.length() - i)=" + (randomAccessInputFile.length() - i));
          if((randomAccessInputFile.length() - i) <= currentBlockSize)
          {
            int rc=0;
            while(rc != -1)
            {
              //            	randomAccessInputFile.seek( i++ );
              rc = randomAccessInputFile.read(b, 0, 1);
              if(rc != -1)
              {
                if ( !badByteInserted )
                {
                  badBytesInserted++;
                  if ( badBytesInserted > 1000 )
                  {
                    badByteInserted = true;
                  }
                  byte badByte = 'b';
                  // insert a 'bad byte'
                  TestUtility.printer( "Inserting a 'bad byte'...good byte:" + b[0] + " bad byte:" + badByte);
                  b[0] = badByte;
                  writer.write(b,0,1);
                }
                else
                {	
                  writer.write(b,0,1);
                }
              }
            }
          }
          else
          {
            randomAccessInputFile.readFully(b);
            writer.write(b,0,currentBlockSize);
            TestUtility.printer( "b.length=" + b.length);
          }
        }
        catch(Exception e){}
        finally
        {
        }
      }
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    finally
    {
      try 
      {
        randomAccessInputFile.close();
        writer.close();
      }
      catch(Exception e){}
      TestUtility.printer( "returning from testRandomAccessOfFlatFile..." );
    }
  }


}
