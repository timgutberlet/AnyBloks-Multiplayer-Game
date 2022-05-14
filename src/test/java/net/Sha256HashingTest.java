package net;


import static org.junit.jupiter.api.Assertions.assertTrue;

import net.server.HashingHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author tbuscher
 */
public class Sha256HashingTest {

  @BeforeAll
  public static void beforeAll() {

  }

  @Test
  public void testSha256() {
    String password = "123456";
    //The string below is the proper encodng of "123456"
    String sha256encodedpassword =
        "8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92";
    String passwordHash = HashingHandler.sha256encode(password);

    assertTrue(HashingHandler.checkPassword(passwordHash,
        sha256encodedpassword));


  }
}
