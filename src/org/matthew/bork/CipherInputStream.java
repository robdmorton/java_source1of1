/*
 * Bork source code license.
 *
 * The contents of this file are subject to the Mozilla Public License
 * Version 1.1 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License
 * at http://www.mozilla.org/MPL/
 * 
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See
 * the License for the specific language governing rights and
 * limitations under the License.
 * 
 * The Original Code is: Bork file encrypter
 * 
 * The Initial Developer of the Original Code is:
 * Matthew Phillips <mattphil@gmail.com>
 * 
 * Portions created by Matthew Phillips are Copyright (C) 2004 Matthew
 * Phillips. All Rights Reserved.
 * 
 * Contributor(s):
 */
package org.matthew.bork;

import java.util.zip.CRC32;

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Encrypts an underlying input stream.
 * 
 * @author Matthew Phillips
 */
public class CipherInputStream extends FilterInputStream
{
  private Cipher cipher;
  private byte [] buffer;
  private CRC32 crc;
  
  public CipherInputStream (InputStream in, Cipher cipher)
  {
    super (in);
    
    this.cipher = cipher;
    this.buffer = new byte [Bork.BUFFER_SIZE];
    this.crc = new CRC32 ();
  }
  
  public int read ()
    throws IOException
  {
    int value = in.read ();
    
    if (value != -1)
    {
      byte [] byteBuff = new byte [1];
      byteBuff [0] = (byte)value;
      
      cipher.decrypt (byteBuff, 0, buffer, 0, 1);
      
      value = Unsigned.promote (buffer [0]);
      
      crc.update (value);
    }
    
    return value;
  }
  
  public int read (byte [] trg, int off, int len)
    throws IOException
  {
    if (len > buffer.length)
      throw new IOException ("Request size too big: " + len);
    
    int bytesRead = in.read (buffer, 0, len);
    
    if (bytesRead != -1)
    {
      cipher.decrypt (buffer, 0, trg, off, len);
    
      crc.update (trg, off, bytesRead);
    }
    
    return bytesRead;
  }
  
  public InputStream getWrappedStream ()
  {
    return in;
  }
  
  public long getCRC ()
  {
    return crc.getValue ();
  }
  
  public void resetCRC ()
  {
    crc.reset ();
  }
}
