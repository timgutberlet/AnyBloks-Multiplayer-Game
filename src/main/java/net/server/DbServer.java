package net.server;

import game.config.Config;
import game.scores.GameScoreBoard;
import game.scores.ScoreProvider;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Enable actual usage of a sqlite Db.
 *
 * @author tbuscher
 */
public class DbServer extends DbAbstract {

  /**
   * Location of Db file.
   */
  private static final String location = "./bloks3/sqliteDb/Server.db";
  /**
   * Instance that can be provided.
   */
  private static DbServer instance;

  /**
   * Constructor.
   *
   * @throws Exception if init fails.
   */
  private DbServer() throws Exception {
    super();
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
      super.connect();
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
      Statement statementDropGames = con.createStatement();
      statementDropGames.execute("DROP TABLE IF EXISTS games");
      statementDropGames.close();
      Statement statementDropScores = con.createStatement();
      statementDropScores.execute("DROP TABLE IF EXISTS scores");
      statementDropScores.close();
      Statement statementDropGameSessionScore = con.createStatement();
      statementDropGameSessionScore.execute("DROP TABLE IF EXISTS gameSessionScore");
      statementDropGameSessionScore.close();
      Statement statementDropGameSessionScores2Game = con.createStatement();
      statementDropGameSessionScores2Game.execute("DROP TABLE IF EXISTS gameSessionScores2Game");
      statementDropGameSessionScores2Game.close();
      Statement statementDropAuthTokens = con.createStatement();
      statementDropAuthTokens.execute("DROP TABLE IF EXISTS authTokens");
      statementDropAuthTokens.close();

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
      players.execute("CREATE TABLE IF NOT EXISTS players (id INTEGER PRIMARY KEY AUTOINCREMENT,"
          + " username TEXT UNIQUE, passwordHash TEXT NOT NULL)");
      players.close();

    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      // Table for Games
      Statement games = con.createStatement();
      games.execute("CREATE TABLE IF NOT EXISTS games (id INTEGER PRIMARY KEY AUTOINCREMENT,"
          + " gamemode TEXT)");
      games.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      // Table for Scores
      Statement scores = con.createStatement();
      scores.execute("CREATE TABLE IF NOT EXISTS scores (id INTEGER PRIMARY KEY AUTOINCREMENT,"
          + " scoreValue INTEGER," + " players_username TEXT NOT NULL,"
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

    try {
      // Table for authTokens
      Statement gameSessionScores = con.createStatement();
      gameSessionScores.execute(
          "CREATE TABLE IF NOT EXISTS authTokens (authToken TEXT NOT NULL,"
              + " username TEXT  NOT NULL)");
      gameSessionScores.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }


  }

  /**
   * Test whether there is a token already saved for a user.
   *
   * @param username of user
   * @return boolean
   */
  public synchronized boolean doesUserHaveAuthToken(String username) {
    boolean userHasToken = false;

    try {
      Statement getAuthToken = con.createStatement();
      ResultSet resultSet = getAuthToken.executeQuery(
          "SELECT authToken from authTokens WHERE username = '" + username + "' ");
      if (resultSet.next()) {
        //in this case the result was not empty, therefore a token for the user has been saved
        userHasToken = true;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return userHasToken || username.equals(Config.getStringValue("HOSTPLAYER")) || username.equals(
        Config.getStringValue("AIPLAYER1")) || username.equals(
        Config.getStringValue("AIPLAYER2")) || username.equals(Config.getStringValue("AIPLAYER3"));
  }

  /**
   * Deletes any saved tokens for a user.
   *
   * @param username of user
   */
  public synchronized void deleteAuthToken(String username) {
    if (doesUserHaveAuthToken(username)) {
      try {
        Statement deleteStatement = con.createStatement();
        deleteStatement.execute("DELETE FROM authTokens WHERE username = '" + username + "'");
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Inserts an authToken or a certain user.
   *
   * @param username of user
   * @param token    to be inserted
   */
  public synchronized void insertAuthToken(String username, String token) {
    try {
      Statement insert = con.createStatement();
      insert.execute(
          "INSERT INTO authTokens(username, authToken) VALUES('" + username + "', '" + token
              + "')");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Method to check if a provided token for a username equals the one saved in the database.
   *
   * @param username      of user trying to authenticate
   * @param providedToken by user
   * @return boolean: is the token valid?
   */
  public synchronized boolean testAuthToken(String username, String providedToken) {
    boolean authSucess = false;

    if (!(username.equals(Config.getStringValue("AIPLAYER1")) || username.equals(
        Config.getStringValue("AIPLAYER2")) || username.equals(
        Config.getStringValue("AIPLAYER3")))) {
      if (doesUserHaveAuthToken(username)) {

        try {
          Statement getAuthToken = con.createStatement();
          ResultSet resultSet = getAuthToken.executeQuery(
              "SELECT authToken from authTokens WHERE username = '" + username + "' ");
          String dbToken = resultSet.getString(1);
          if (providedToken.equals(dbToken)) {
            authSucess = true;
          }
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    } else {
      authSucess = true;
    }

    return authSucess || username.equals(Config.getStringValue("HOSTPLAYER"));
  }

  /**
   * puts a game into the DB so that scores can be added (with the id of the game).
   *
   * @param gameMode of the played game
   * @return id of inserted game as String
   */
  public synchronized String insertGame(String gameMode) {
    int insertedId = 0;
    try {
      Statement insertGame = con.createStatement();
      insertGame.executeUpdate("INSERT INTO games (gamemode) VALUES('" + gameMode + "')");
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
          "INSERT INTO scores (players_username, scoreValue, games_id) VALUES('" + username + "',"
              + scoreValue + ", '" + gameId + "')");
      insertScore.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * Insert a new GameSessionScore.
   *
   * @return the id of the newly inserted GameSessionScore
   */
  public synchronized String insertGameSessionScore() {
    int insertedId = 0;

    try {
      Statement insertGameSessionScore = con.createStatement();
      insertGameSessionScore.executeUpdate("INSERT INTO GameSessionScore (endtime) VALUES ('')");
      ResultSet rs = insertGameSessionScore.getGeneratedKeys();
      insertedId = rs.getInt(1);
      insertGameSessionScore.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return String.valueOf(insertedId);
  }

  /**
   * Insert into the table GameSessionScore2Game after a GameSessionScore has been inserted.
   *
   * @param gameSessionScoreId of gameSessionScore
   * @param gameId             and the game that belongs to that sessionScore
   */
  public synchronized void insertGameSessionScore2Game(String gameSessionScoreId, String gameId) {
    try {
      Statement insertScore = con.createStatement();
      insertScore.execute(
          "INSERT INTO gameSessionScores2Game (gameSessionScore_id, games_id) VALUES('"
              + Integer.parseInt(gameSessionScoreId) + "','" + Integer.parseInt(gameId) + "')");
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
   * Checks whether or not a username is already present in the database.
   *
   * @param username to check
   * @return true if there is an account / false if the username is unused
   */
  public synchronized boolean doesUsernameExist(String username) {
    boolean doesExist = false;
    try {
      Statement getUsers = con.createStatement();
      ResultSet resultSet = getUsers.executeQuery(
          "SELECT* FROM players WHERE players.username = '" + username + "';");
      if (resultSet.next()) {
        doesExist = true;
      }
      //Since usernames are unique, if the username is set, it will appear exactly once
      resultSet.close();
      getUsers.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return doesExist;
  }

  /**
   * Fetch the passwordHash of a certain user. Do not call this unless you a user with this username
   * exists (doesUserExist returned true!) or an Exception will be thrown
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
  public synchronized GameScoreBoard getGameScores(String gameId) {
    HashMap<String, Integer> scoreMap = new HashMap<>();
    Statement getGameInfo = null;
    String gameMode = "";
    try {
      //Get the gamemode of the game
      getGameInfo = con.createStatement();
      ResultSet rsGameInfo = getGameInfo.executeQuery(
          "SELECT * FROM games WHERE id = '" + gameId + "'");
      gameMode = rsGameInfo.getString("gamemode");
      rsGameInfo.close();
      getGameInfo.close();

      //Get all scores that belong to the game
      Statement getScores = con.createStatement();
      ResultSet rsScores = getScores.executeQuery(
          "SELECT * FROM scores WHERE games_id = '" + gameId + "'");
      while (rsScores.next()) {
        //And put all the fetched scores into the HashMap
        scoreMap.put(rsScores.getString("players_username"), rsScores.getInt(2));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    //Build a new scoreboard with the gamemode and the prepared Hashmap of usernames and scores
    return new GameScoreBoard(gameMode, scoreMap);
  }

  /**
   * Gat all Scores of games associated with a gameSession(Score).
   *
   * @param gameSessionScoreId id of relevant gameSession
   * @return ArrayList<GameScoreBoard></GameScoreBoard>
   */
  public synchronized ArrayList<GameScoreBoard> getGameSessionScores(String gameSessionScoreId) {
    ArrayList<GameScoreBoard> gameScores = new ArrayList<>();
    String gameId = "";
    try {

      //Get all games that belong to the gameSession
      Statement getGameIds = con.createStatement();
      ResultSet rsGames = getGameIds.executeQuery(
          "SELECT * FROM GameSessionScores2Game WHERE gameSessionScore_id = '" + gameSessionScoreId
              + "'");
      while (rsGames.next()) {
        //Fetch the Scoreboard for every game and save it to the ArrayList
        gameId = String.valueOf(rsGames.getInt("games_id"));
        gameScores.add(getGameScores(gameId));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return gameScores;
  }

  /**
   * change the passwordHash of a user in the DB.
   *
   * @param username            of user
   * @param updatedPasswordHash the new passwordHash to be inserted
   * @return boolean: success
   */
  public synchronized boolean updatePassword(String username, String updatedPasswordHash) {
    boolean passed = false;
    try {
      Statement updatePasswordHashStatement = con.createStatement();
      updatePasswordHashStatement.executeUpdate(
          "UPDATE players SET passwordHash = '" + updatedPasswordHash + "' WHERE username = '"
              + username + "'");
      passed = true;
    } catch (SQLException e) {
      //In case of exceptions, they are being caught and passed remains set to false
      e.printStackTrace();
    }
    return passed;
  }

  /**
   * Delete an account along with any stored tokens. Only call this once the request has been
   * authenticated!
   *
   * @param username of user to be deleted.
   * @return boolean whether the deletion was successful or not
   */
  public synchronized boolean deleteAccount(String username) {
    boolean passed = false;
    if (!doesUsernameExist(username)) {
      passed = false;
    } else {
      deleteAuthToken(username);
      try {
        Statement deleteAccountStatement = con.createStatement();
        deleteAccountStatement.executeUpdate(
            "DELETE FROM players WHERE username = '" + username + "'");
        passed = true;
      } catch (SQLException e) {
        e.printStackTrace();
      }

    }
    return passed;
  }

  /**
   * Returns the ID of the last inserted game.
   *
   * @return String
   */
  public String getLastGameId() {
    String id = "";
    try {
      Statement getGameId = con.createStatement();
      ResultSet rsGames = getGameId.executeQuery(
          "SELECT * FROM games");
      while (rsGames.next()) {
        //Fetch the Scoreboard for every game and save it to the ArrayList
        id = String.valueOf(rsGames.getInt("id"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return id;
  }

  /**
   * return how many games are saved in the DB.
   *
   * @return int
   */
  public int getNumberOfLocalGames() {
    ResultSet resultSet = null;
    try {
      Statement statement = con.createStatement();
      resultSet = statement.executeQuery("SELECT COUNT(*) FROM games");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    int number = 0;
    if (resultSet != null) {
      try {
        number = resultSet.getInt(1);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    return number;
  }

  /**
   * Function to provide a Hashmap of all players and the number of wins they have won locally.
   *
   * @return Hashmap of String, Integer
   */
  public HashMap<String, Integer[]> getGameWins() {
    ResultSet resultSet = null;
    try {
      Statement statement = con.createStatement();
      resultSet = statement.executeQuery("SELECT * from GAMES");
    } catch (SQLException e) {
      e.printStackTrace();
    }
    int number = 0;
    GameScoreBoard gameScoreBoard;
    HashMap<String, Integer[]> topPlayers = new HashMap<>();
    try {
      while (resultSet.next()) {
        String gameId = String.valueOf(resultSet.getRow());
        gameScoreBoard = getGameScores(gameId);
        String winnerUsername = ScoreProvider.getWinner(gameScoreBoard);

        for (String username : gameScoreBoard.getPlayerScores().keySet()) {
          //Set the updatedMaxScore to the maximum of saved scores and
          int formerMaxScore = topPlayers.get(username) == null ? 0 : topPlayers.get(username)[1];
          int currentScore = gameScoreBoard.getPlayerScores().get(username);
          int updatedMaxScore = Math.max(formerMaxScore, currentScore);

          int wins = 0;
          if (username.equals(winnerUsername)) {
            wins =
                topPlayers.get(winnerUsername) == null ? 1
                    : (topPlayers.get(winnerUsername)[0] + 1);
          } else {
            wins = topPlayers.get(winnerUsername) == null ? 0
                : (topPlayers.get(winnerUsername)[0]);
          }

          Integer[] value = {wins, updatedMaxScore};
          topPlayers.put(ScoreProvider.getWinner(gameScoreBoard), value);

        }

      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return topPlayers;


  }

  /**
   * This method prepares an account in the database to be used in testing.
   */
  public void prepareTokenGenerationTest() {
    try {
      Statement statement = con.createStatement();
      statement.execute("DELETE FROM players WHERE username = 'testuser'");
      statement.close();
      Statement statementInsert = con.createStatement();
      statementInsert.execute(
          "INSERT INTO players(username, passwordHash)  VALUES('testuser', '123456')");
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /**
   * This method ensures there is no account with the username of the account created in tests.
   */
  public void prepareRemoteAccountManagementTest() {
    try {
      Statement statement = con.createStatement();
      statement.execute("DELETE FROM players WHERE username = 'testuserUpdate1'");
      statement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}

