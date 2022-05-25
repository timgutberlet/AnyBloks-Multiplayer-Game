package net.server;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Abstract class to allow connection to SQLite DB.
 *
 * @author tbuscher
 */
public abstract class DbAbstract {

  private final String path = "./bloks3/sqliteDb/Server.db";
  protected Connection con;

  /**
   * Constructor. Connects to DB in location of argument.
   */
  public DbAbstract() throws Exception {
    if (!new File(path).exists()) {
      //In this case the DB has never existed, so it is put to the correct path
      try {
        Files.createDirectories(Paths.get("./bloks3/sqliteDb"));
        setupDb(true);
      } catch (Exception e) {
        e.printStackTrace();
      }
    } else {
      //In this case, the DB exists at the proper location,
      boolean connectSuccess = connect();
      if (!connectSuccess) {
        throw new Exception("Failed to connect to DB");
      }
      setupDb(false);
    }
  }

  /**
   * Open connection to Db.
   */
  protected boolean connect() {
    try {
      Class.forName("org.sqlite.JDBC");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
      return false;
    }
    //if driver exists, try to connect
    try {
      con = DriverManager.getConnection("jdbc:sqlite:" + path);
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
    return true;
  }

  /**
   * create db file if not already in correct location (sqliteDB/Server.db).
   *
   * @param overwrite if existing DBs are to be deleted
   */
  protected abstract boolean setupDb(boolean overwrite);

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
}