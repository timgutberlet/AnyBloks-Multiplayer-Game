package net.server;

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
      players.execute(
          "CREATE TABLE IF NOT EXISTS players (id INTEGER PRIMARY KEY AUTOINCREMENT,"
              + " username TEXT UNIQUE, passwordHash TEXT NOT NULL)");
      players.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


  /**
   * put a new account into the Db.
   *
   * @param usernameInsert     username to be inserted.
   * @param passwordHashInsert passwordHash to be inserted.
   */
  public synchronized void newAccount(String usernameInsert, String passwordHashInsert) {
    try {
      Statement newAccount = con.createStatement();
      newAccount.execute(
          "INSERT INTO players (username, passwordHash) VALUES('" + usernameInsert + "' ,'"
              + passwordHashInsert + "')");

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public synchronized String getUserPasswordHash(String username) {
    String passwordHash;
    try {
      Statement getPw = con.createStatement();
      ResultSet rs = getPw.executeQuery(
          "SELECT passwordHash FROM players WHERE players.username = '" + username + "';");
      passwordHash = rs.getString("passwordHash");
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    return passwordHash;
  }


}
