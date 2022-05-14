package net;

import static net.server.HashingHandler.checkPassword;
import static net.server.HashingHandler.hashPassword;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author tbuscher
 */
public class HashingTest {

  @BeforeAll
  public static void beforeAll() {

  }

  @Test
  public void testSha256() throws Exception {
    String password = "123456";
    String hashedPassword = hashPassword(password);

    assertTrue(checkPassword(password, hashedPassword));

    String hashedPasswordB = hashPassword(password);

    //assertTrue(checkPasswordHashes(hashedPassword, hashedPasswordB));

  }

}
