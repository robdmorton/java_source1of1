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

import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Encrypts an underlying output stream.
 * 
 * @author Matthew Phillips
 */
public class CipherOutputStream extends FilterOutputStream
{
  private Cipher cipher;
  private byte [] buffer;
  private CRC32 crc;
  
  public CipherOutputStream (OutputStream out, Cipher cipher)
  {
    super (out);

    this.cipher = cipher;
    this.buffer = new byte [Bork.BUFFER_SIZE];
    this.crc = new CRC32 ();
  }

  public void write (int b) throws IOException
  {
    byte [] byteBuffer = new byte [1];
    
    byteBuffer [0] = (byte)b;
    
    crc.update (b);
    
    writeEncrypted (byteBuffer, 0, 1);
  }

  public void write (byte [] src, int off, int len) throws IOException
  {
    if (len > buffer.length)
      throw new IOException ("Request size too big: " + len);
    
    crc.update (src, off, len);
    
    writeEncrypted (src, off, len);
  }

  private void writeEncrypted (byte [] src, int off, int len) throws IOException
  {
    cipher.encrypt (src, off, buffer, 0, len);
    
    out.write (buffer, 0, len);
  }
  
  public OutputStream getWrappedStream ()
  {
    return out;
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