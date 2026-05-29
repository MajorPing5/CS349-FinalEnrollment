/**
 * 
 */
package util;

import java.nio.charset.StandardCharsets;
import java.security.*;

/**
 * 
 */
public class PasswordHash {
  public String hash(String plainPassword) {

    if (plainPassword == null) {
      throw new IllegalArgumentException("Password must not be null");
    }
    
    try {
      MessageDigest digest = MessageDigest.getInstance("SHA-256");
      byte[] bytes = digest.digest(plainPassword.getBytes(StandardCharsets.UTF_8));
      return toHexString(bytes);

    } catch (NoSuchAlgorithmException e) {
      // This should not happen for SHA-256, but if it does,
      // wrap it in a runtime exception for simplicity.
      throw new IllegalStateException("SHA-256 algorithm not available", e);
    }
  }
  
  /**
   *  Following code was pulled from docs.oracle.com on
   *  "Java Cryptography Architecture (JCA) Reference Guide"'s section on
   *  "Diffie-Hellman Key Exchange between Two Parties" example
   */
  
  /**
   *  Converts a byte to hex digit and writes to the supplied buffer
   * 
   *  @param b
   *  @param buf
   */
  private static void byte2hex(byte b, StringBuffer buf) {
      char[] hexChars = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
              '9', 'A', 'B', 'C', 'D', 'E', 'F' };
      int high = ((b & 0xf0) >> 4);
      int low = (b & 0x0f);
      buf.append(hexChars[high]);
      buf.append(hexChars[low]);
  }
  
  /** 
   *  Converts a byte array to hex string
   *  
   *  @param block
   */
  private static String toHexString(byte[] block) {
      StringBuffer buf = new StringBuffer();
      int len = block.length;
      for (int i = 0; i < len; i++) {
          byte2hex(block[i], buf);
          if (i < len-1) {
              buf.append(":");
          }
      }
      return buf.toString();
  }
}
