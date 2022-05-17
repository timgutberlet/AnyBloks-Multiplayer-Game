package net.server;

import game.model.Debug;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.xml.transform.Result;

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
    //Create all tables with one statement per table

    try {
      // Table for players
      Statement players = con.createStatement();
      players.execute(
          "CREATE TABLE IF NOT EXISTS players (id INTEGER PRIMARY KEY AUTOINCREMENT,"
              + " username TEXT UNIQUE, passwordHash TEXT NOT NULL)");
      players.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      // Table for Games
      Statement games = con.createStatement();
      games.execute(
          "CREATE TABLE IF NOT EXISTS games (id INTEGER PRIMARY KEY AUTOINCREMENT,"
              + " gamemode TEXT)");
      games.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      // Table for Scores
      Statement scores = con.createStatement();
      scores.execute(
          "CREATE TABLE IF NOT EXISTS scores (id INTEGER PRIMARY KEY AUTOINCREMENT,"
              + " scoreValue INTEGER,"
              + " players_username TEXT NOT NULL,"
              + " games_id INTEGER NOT NULL)");
      scores.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      // Table for gameSessionScores
      Statement gameSessionScores = con.createStatement();
      gameSessionScores.execute(
          "CREATE TABLE IF NOT EXISTS gameSessionScore (id INTEGER PRIMARY KEY AUTOINCREMENT,"
              + "endtime datetime default current_timestamp)");
      gameSessionScores.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      // Table for gameSessionScores2Game
      Statement gameSessionScores = con.createStatement();
      gameSessionScores.execute(
          "CREATE TABLE IF NOT EXISTS gameSessionScores2Game (gameSessionScore_id INTEGER NOT NULL,"
              + " games_id INTEGER  NOT NULL)");
      gameSessionScores.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }


  }

  /**
   * puts a game into the DB so that scores can be added (with the id of the game)
   *
   * @param gameMode of the played game
   * @return id of inserted game as String
   */
  public synchronized String insertGame(String gameMode) {
    int insertedId = 0;
    try {
      Statement insertGame = con.createStatement();
      insertGame.executeUpdate(
          "INSERT INTO games (gamemode) VALUES('" + gameMode + "')");
      ResultSet rs = insertGame.getGeneratedKeys();
      insertedId = rs.getInt(1);
      insertGame.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return String.valueOf(insertedId);
  }

  /**
   * Insert a score.
   *
   * @param gameId     to which the score belongs
   * @param username   to which the score belongs
   * @param scoreValue number of points scored
   */
  public synchronized void insertScore(String gameId, String username, int scoreValue) {
    try {
      Statement insertScore = con.createStatement();
      insertScore.execute(
          "INSERT INTO scores (players_username, scoreValue, games_id) VALUES('" + username + "','"
              + scoreValue + "', '" + gameId + "')");
      insertScore.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Insert a new GameSessionScore
   *
   * @return the id of the newly inserted GameSessionScore
   */
  public synchronized String insertGameSessionScore() {
    int insertedId = 0;

    try {
      Statement insertGameSessionScore = con.createStatement();
      insertGameSessionScore.executeUpdate(
          "INSERT INTO GameSessionScore (endtime) VALUES ('')");
      ResultSet rs = insertGameSessionScore.getGeneratedKeys();
      insertedId = rs.getInt(1);
      insertGameSessionScore.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return String.valueOf(insertedId);
  }

  /**
   * Insert into the table GameSessionScore2Game after a GameSessionScore has been inserted
   *
   * @param gameSessionScoreId of gameSessionScore
   * @param gameId             and the game that belongs to that sessionScore
   */
  public synchronized void insertGameSessionScore2Game(String gameSessionScoreId, String gameId) {
    try {
      Statement insertScore = con.createStatement();
      insertScore.execute(
          "INSERT INTO gameSessionScores2Game (gameSessionScore_id, games_id) VALUES('"
              + Integer.parseInt(gameSessionScoreId) + "','"
              + Integer.parseInt(gameId) + "')");
      insertScore.close();
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
  public synchronized boolean newAccount(String usernameInsert, String passwordHashInsert) {
    boolean success = true;
    try {
      Statement newAccount = con.createStatement();
      newAccount.execute(
          "INSERT INTO players (username, passwordHash) VALUES('" + usernameInsert + "' ,'"
              + passwordHashInsert + "')");

    } catch (SQLException e) {
      success = false;
      e.printStackTrace();
    }
    return success;
  }


  /**
   * Checks whether or not a username is already present in the database
   *
   * @param username to check
   * @return true if there is an account / false if the username is unused
   */
  public synchronized boolean doesUsernameExist(String username) {
    boolean doesExist = true;
    try {
      Statement getUsers = con.createStatement();
      ResultSet resultSet = getUsers.executeQuery(
          "SELECT* FROM players WHERE players.username = '" + username + "';");
      int count = 0;
      if (resultSet.next()) {
        count = Integer.parseInt(resultSet.getString(1));
      }
      //Since usernames are unique, if the username is set, it will appear exactly once
      doesExist = count == 1;
      resultSet.close();
      getUsers.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return doesExist;
  }

  /**
   * Fetch the passwordHash of a certain user.
   *
   * @param username of interest
   * @return passwordHash saved
   */
  public synchronized String getUserPasswordHash(String username) {
    String passwordHash;
    try {
      Statement getPw = con.createStatement();
      ResultSet rs = getPw.executeQuery(
          "SELECT passwordHash FROM players WHERE players.username = '" + username + "';");
      passwordHash = rs.getString("passwordHash");
      rs.close();
      getPw.close();
    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
    return passwordHash;
  }


  /**
   * Returns an Array of Strings with Scores for users of a game.
   *
   * @param gameId id of game info is wanted for
   * @return String Arr with scores & gamemodeInfo (at [0])
   */
  public synchronized void getGameScores(String gameId){
    //TODO return a new datatyp with info about gameScores
    String gameInfo = "";
    Statement getGameInfo = null;
    try {
      getGameInfo = con.createStatement();
      ResultSet rsGameInfo = getGameInfo.executeQuery("SELECT * FROM games WHERE id = '" + gameId + "'");
      Debug.printMessage(rsGameInfo.getString("gamemode"));
      rsGameInfo.close();
      getGameInfo.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }


}
