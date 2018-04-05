/*
 * Bork source code license.
 *
 * The contents of this file are subject to the Mozilla Public License Version 1.1 (the "License");
 * you may not use this file except in compliance with the License. You may obtain a copy of the
 * License at http://www.mozilla.org/MPL/
 * 
 * Software distributed under the License is distributed on an "AS IS" basis, WITHOUT WARRANTY OF
 * ANY KIND, either express or implied. See the License for the specific language governing rights
 * and limitations under the License.
 * 
 * The Original Code is: Bork file encrypter
 * 
 * The Initial Developer of the Original Code is: Matthew Phillips <mattphil@gmail.com>
 * 
 * Portions created by Matthew Phillips are Copyright (C) 2004 Matthew Phillips. All Rights
 * Reserved.
 * 
 * Contributor(s): Konstantin Knizhnik (knizhnik@garret.ru)
 */
package org.matthew.bork;

import java.io.UnsupportedEncodingException;

/**
 * RC4 cipher algorithm. Adapted from Konstantin Knizhnik's (knizhnik@garret.ru) Perst source with
 * permission.
 *
 * @author Matthew Phillips
 */
public class RC4 implements Cipher {
  private byte[] initState = new byte[256];
  private byte[] state = new byte[256];
  private int x, y;

  public RC4(String key) throws UnsupportedEncodingException {
    this(key.getBytes("UTF-8"));
  }

  public RC4(byte[] key) {
    setKey(key);

    reset();
  }

  private void setKey(byte[] key) {
    for (int counter = 0; counter < 256; ++counter)
      initState[counter] = (byte) counter;

    int index1 = 0;
    int index2 = 0;

    for (int counter = 0; counter < 256; counter++) {
      index2 = (key[index1] + initState[counter] + index2) & 0xff;
      byte temp = initState[counter];
      initState[counter] = initState[index2];
      initState[index2] = temp;
      index1 = (index1 + 1) % key.length;
    }
  }

  public void skip(int bytes) {
    for (int i = 0; i < bytes; i++)
      nextState();
  }

  public void reset() {
    x = y = 0;
    System.arraycopy(initState, 0, state, 0, state.length);

    // skip first 256 states to avoid Fluhrer/Martin/Shamir RC4 attack
    // http://www.fact-index.com/r/rc/rc4_cipher.html
    skip(256);
  }

  public void encrypt(byte[] clearText, int clearOff, byte[] cipherText, int cipherOff, int len) {
    // commented test for nil encryption
    // System.arraycopy (clearText, 0, cipherText, 0, len);
    for (int i = 0; i < len; i++) {
      cipherText[cipherOff + i] = (byte) (clearText[clearOff + i] ^ state[nextState()]);
    }
  }

  public void decrypt(byte[] cipherText, int cipherOff, byte[] clearText, int clearOff, int len) {
    for (int i = 0; i < len; i++) {
      clearText[clearOff + i] = (byte) (cipherText[cipherOff + i] ^ state[nextState()]);
    }
  }

  private int nextState() {
    x = (x + 1) & 0xff;
    y = (y + state[x]) & 0xff;
    byte temp = state[x];
    state[x] = state[y];
    state[y] = temp;
    return (state[x] + state[y]) & 0xff;
  }
}
