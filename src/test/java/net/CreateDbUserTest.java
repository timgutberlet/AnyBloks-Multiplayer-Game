package net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import net.server.DbServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Testing the creation of a user in the Database
 *
 * @author tbuscher
 */
public class CreateDbUserTest {

  @BeforeAll
  public static void beforeAll() {

  }

  @Test
  public void testDbUserCreation() throws Exception {
    DbServer dbServer = DbServer.getInstance();
    dbServer.newAccount("testuser", "123456");
    String passwordHash = dbServer.getUserPasswordHash("testuser");

    assertTrue(dbServer.doesUsernameExist("testuser"));
    assertEquals(passwordHash, "123456");
  }
}
