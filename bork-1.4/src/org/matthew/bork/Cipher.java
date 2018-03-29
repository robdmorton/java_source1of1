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

/**
 * Generic stream cipher interface.
 * 
 * @author Matthew Phillips
 */
public interface Cipher
{
  /**
   * Reset the cipher to initial state.
   */
  public void reset ();

  /**
   * Skip a given number of bytes i.e. advance cipher as if it has
   * been used to encrypt the number of bytes.
   */
  public void skip (int bytes);

  /**
   * Encrypt a block of data.
   * 
   * @param clearText The cleartext to be encrypted.
   * @param clearOff The offset into clearText to start encrypting.
   * @param cipherText The buffer where the ciphertext will be
   *          written.
   * @param cipherOff The offset into cipherText to place the
   *          encrypted data.
   * @param len The length (in bytes) of data (starting from clearText +
   *          clearOff) to encrypt.
   */
  public void encrypt (byte [] clearText, int clearOff,
                        byte [] cipherText, int cipherOff, int len);

  /**
   * Decryppt a block of data.
   * 
   * @param cipherText The ciphertext to be decrypted.
   * @param cipherOff The offset into cipherText to start decrypting.
   * @param clearText The buffer where the cleartext will be
   *          written.
   * @param clearOff The offset into clearText to place the
   *          decrypted data.
   * @param The length (in bytes) of data (starting from cipherText +
   *          cipherOff) to decrypt. 
   */
  public void decrypt (byte [] cipherText, int cipherOff,
                        byte [] clearText, int clearOff, int len);
}
