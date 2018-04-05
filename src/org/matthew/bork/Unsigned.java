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
 * Contributor(s):
 */
package org.matthew.bork;

/**
 * Unsigned conversion utility.
 * 
 * @author Matthew Phillips
 */
public final class Unsigned {
  private Unsigned() {
    // zip
  }

  /**
   * Turns a 32-bit "unsigned" int into the bitwise equivalent 64-bit unsigned value without sign
   * extension. Needed since Java doesn't do unsigned types.
   * 
   * @param value The value to promote.
   * @return The 64-bit unsigned bitwise equivalent of value.
   * 
   * @see #promote(byte)
   */
  public static long promote(int value) {
    if ((value & 0x80000000) != 0) {
      // create long without sign extension and then re-add high bit
      long longValue = (value & 0x7FFFFFFF);
      longValue |= 0x80000000L;

      return longValue;
    } else {
      return value;
    }
  }

  /**
   * Turns a 8-bit "unsigned" byte into the bitwise equivalent 32-bit unsigned value without sign
   * extension. Needed since Java doesn't do unsigned types.
   * 
   * @param value The value to promote.
   * @return The 32-bit unsigned bitwise equivalent of value.
   * 
   * @see #promote(int)
   */
  public static int promote(byte value) {
    if ((value & (byte) 0x80) != 0) {
      // create int without sign extension and then re-add high bit
      int uintValue = (value & (byte) 0x7F);
      uintValue |= 0x80;

      return uintValue;
    } else {
      return value;
    }
  }
}
