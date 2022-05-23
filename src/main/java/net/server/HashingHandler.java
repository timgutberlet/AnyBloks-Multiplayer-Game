package net.server;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author tbuscher
 */
public class HashingHandler {

  /**
   * encodes a string input into the output string.
   *
   * @param input
   * @return
   */
  public static String sha256encode(String input) {
    String output = DigestUtils.sha256Hex(input);
    return output;
  }

  /**
   * checks if two passwords are equal.
   *
   * @param passwordA
   * @param passwordB
   * @return boolean if the passwords are equal
   */
  public static boolean checkPassword(String passwordA, String passwordB) {
    return passwordA.equals(passwordB);
  }


}
