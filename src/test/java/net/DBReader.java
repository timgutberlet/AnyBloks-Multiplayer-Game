package net;

import static org.junit.jupiter.api.Assertions.assertTrue;

import net.server.DbServer;
import org.junit.jupiter.api.Test;

/**
 * @author tbuscher
 */
public class DBReader {

  @Test
  public void test() {
    DbServer dbServer = null;
    try {
      dbServer = DbServer.getInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
    dbServer.newAccount("172.19.112.1", "123456");
    System.out.println(dbServer.doesUsernameExist("172.19.112.1"));
    assertTrue(dbServer.doesUsernameExist("172.19.112.1"));

  }

}
