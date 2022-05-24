package net.server;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * used to hash the passwords.
 *
 * @author tbuscher
 */
public class HashingHandler {

  /**
   * encodes a string input into the output string.
   *
   * @param input input
   * @return hashedPassword
   */
  public static String sha256encode(String input) {
    String output = DigestUtils.sha256Hex(input);
    return output;
  }

  /**
   * checks if two passwords are equal.
   *
   * @param passwordA password
   * @param passwordB password to comapre with
   * @return boolean if the passwords are equal
   */
  public static boolean checkPassword(String passwordA, String passwordB) {
    return passwordA.equals(passwordB);
  }


}
