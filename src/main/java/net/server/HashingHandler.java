package net.server;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author tbuscher
 */
public class HashingHandler {

  public static String sha256encode(String input){
    String output = DigestUtils.sha256Hex(input);
    return output;
  }

  public static boolean checkPassword(String passwordA, String passwordB){
    return passwordA.equals(passwordB);
  }


}
