package net.server;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Enable actual usage of a sqlite Db.
 *
 * @author tbuscher
 */
public class DbServer extends DbHandler {

  /**
   * Location of Db file.
   */
  private static final String location = "./blocks3/sqliteDb/Server.db";
  /**
   * Instance that can be provided
   */
  private static DbServer instance;

  /**
   * Constructor.
   *
   * @throws Exception if init fails.
   */
  private DbServer() throws Exception {
    super(location);
  }

  /**
   * Method to provide the instance.
   *
   * @throws Exception if instance can't be provided
   */

  public static synchronized DbServer getInstance() throws Exception {
    if (instance == null) {
      instance = new DbServer();
    }
    return instance;
  }

  /**
   * Prepare the Db.
   *
   * @param forceReset flag to ignore any checks
   */
  protected synchronized boolean setupDb(boolean forceReset) {
    if (forceReset) {
      super.connect(location);
      resetDb();
    }
    return true;
  }

  /**
   * Harshly emptying the Db. Any data will be lost.
   */
  public synchronized void resetDb() {
    dropAllTables();
    createTables();
  }

  /**
   * Harshly dropping data & structure of Db.
   */
  private synchronized void dropAllTables() {
    try {
      Statement statementDropPlayers = con.createStatement();
      statementDropPlayers.execute("DROP TABLE IF EXISTS players");
      statementDropPlayers.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  /**
   * Init the Db so it can be used.
   */
  private synchronized void createTables() {
    try {
      // Players table
      Statement players = con.createStatement();
      players.execute("CREATE TABLE IF NOT EXISTS players("
          + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
          + "username TEXT NOT NULL UNIQUE ,"
          + "passwordHash TEXT NOT NULL,"
          + "disconnects INTEGER NOT NULL DEFAULT 0,"
          + ");");
      players.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  /**
   * put a new account into the Db.
   *
   * @param username     to be inserted.
   * @param passwordHash to be inserted.
   */
  public synchronized void newAccount(String username, String passwordHash) {
    try {
      PreparedStatement statement = con.prepareStatement(
          "INSERT INTO players (username, password) " + "VALUES (?, ?)");
      statement.setString(1, username);
      statement.setString(2, passwordHash);
      statement.execute();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public synchronized String getUserPasswordHash(String username) {
    String passwordHash;
    try {
      PreparedStatement statement = con.prepareStatement(
          "SELECT passwordHash FROM players "
              + "WHERE players.username = ?");
      statement.setString(1, username);
      ResultSet rs = statement.executeQuery();
      passwordHash = rs.getString("password");
    } catch (SQLException e){
      e.printStackTrace();
      return null;
    }
    return passwordHash;
  }




}
