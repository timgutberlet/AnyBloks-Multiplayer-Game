package net.tests.game;

import game.model.Debug;
import java.net.Inet4Address;
import java.net.UnknownHostException;
import net.server.DbServer;

/**
 * @author tgeilen
 * @Date 16.05.22
 */
public class testInetAdress {

  public static void main(String[] args) throws UnknownHostException {
    Debug.printMessage(Inet4Address.getLocalHost().getHostAddress());

    DbServer dbServer = null;
    try {
      dbServer = DbServer.getInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
    dbServer.newAccount("testuser", "123456");
    String passwordHash = dbServer.getUserPasswordHash("testuser");

    System.out.println(dbServer.doesUsernameExist("testuser"));
    System.out.println(passwordHash);
  }

}
