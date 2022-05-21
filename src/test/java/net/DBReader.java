package net;

import net.server.DbServer;

/**
 * @author tbuscher
 */
public class DBReader {

  public static void main(String[] args) {
    DbServer dbServer = null;
    try {
      dbServer = DbServer.getInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
    dbServer.newAccount("172.19.112.1", "123456");
    System.out.println(dbServer.doesUsernameExist("172.19.112.1"));
  }

}
