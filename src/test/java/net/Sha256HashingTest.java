package net;


import static org.junit.jupiter.api.Assertions.assertTrue;

import game.model.Debug;
import net.server.HashingHandler;
import org.junit.jupiter.api.Test;

/**
 * Tests the sha256 encoding against one pre-encoded String.
 *
 * @author tbuscher
 */
public class Sha256HashingTest {

  @Test
  public void testSha256() {
    String password = "123456";
    //The string below is the proper encoding of "123456"
    String sha256encodedpassword =
        "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92";
    Debug.printMessage("The encoding of 123456 in sha256 is: " + sha256encodedpassword);
    String passwordHash = HashingHandler.sha256encode(password);
    Debug.printMessage("The encoding generated by the HashingHandler is: " + passwordHash);

    assertTrue(HashingHandler.checkPassword(passwordHash,
        sha256encodedpassword));


  }
}
