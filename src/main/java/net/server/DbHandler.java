package net.server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Abstract class to allow connection to SQLite DB
 *
 * @author tbuscher
 */
public abstract class DbHandler {

  /**
   * Connection to Db
   */
  protected Connection con;

  /**
   * Constructor. Connects to DB in location of argument.
   *
   * @param location of the DB
   */
  public DbHandler(String location) throws Exception {
    if (!new File(location).exists()) {
      try {
        Files.createDirectories(Paths.get("./blocks3/sqliteDb"));
      } catch (IOException e) {
        e.printStackTrace();
      }
      setupDb(true);
    } else {
      if (!connect(location)) {
        disconnect();
      }
      try {
        Files.createDirectories(Paths.get("./blocks3/sqliteDb"));
      } catch (IOException e) {
        e.printStackTrace();
      }
      setupDb(true);
      if (!connect(location)) {
        throw new Exception("Failed to connect to DB");
      }
    }
  }

  /**
   * Open connection to Db.
   *
   * @param location of Db.
   */

  protected boolean connect(String location) {
    try {
      Class.forName("org.sqlite.JDBC");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      return false;
    }
    //if driver exists, try to connect
    try {
      con = DriverManager.getConnection("jdbc:sqlite:" + location);
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * Close con to the Db.
   */
  protected boolean disconnect() {
    try {
      if (con != null && !con.isClosed()) {
        con.close();
      } else {
        return false;
      }
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * create db file.
   */
  protected boolean setupDb() {
    return setupDb(false);
  }

  /**
   * create db file.
   *
   * @param forceReset to force rebuild.

   */
  protected abstract boolean setupDb(boolean forceReset);

}
