package game.scores;

import java.util.ArrayList;
import java.util.HashMap;
import net.server.DbServer;

/**
 * This class provides the data shown while waiting
 *
 * @author tbuscher
 */
public class ScoreProvider {

  /**
   * Gives last gameScores to UI.
   *
   * @return GameScoreBoard of last played game
   */
  public static GameScoreBoard getLastGameScoreBoard() {

    DbServer dbServer = null;

    try {
      dbServer = DbServer.getInstance();
      if (dbServer == null) {
        throw new Exception("DB Connect failed!");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    String lastGameId = dbServer.getLastGameId();
    GameScoreBoard gameScoreBoard = dbServer.getGameScores(lastGameId);

    return gameScoreBoard;

  }

  /**
   * Method to construct a GameSessionScoreBoard out of an ArrayList of GameScoreBoards.
   *
   * @param gameScoreBoards ArrayList of ScoreBoards of relevant games.
   * @return GameSessionScoreBoard.
   */
  private static GameSessionScoreBoard analyzeGames(ArrayList<GameScoreBoard> gameScoreBoards) {
    HashMap<String, Integer[]> user2ScoreAndWin = new HashMap<>();

    //Iterate over all gameScores and find all username and count played games
    int gamesPlayed = 0;
    for (GameScoreBoard gameScoreBoard : gameScoreBoards) {
      gamesPlayed += 1;
      for (String uName : gameScoreBoard.getPlayerScores().keySet()) {
        if (!user2ScoreAndWin.containsKey(uName)) {
          user2ScoreAndWin.put(uName, new Integer[2]);
        }
      }
    }

    //Iterate over all gameScores and now save wins
    for (GameScoreBoard gameScoreBoard : gameScoreBoards) {
      //Evaluate game Winner
      int max = 0;
      String winner = "";
      for (String uName : gameScoreBoard.getPlayerScores().keySet()) {
        if (gameScoreBoard.getPlayerScores().get(uName) > max) {
          winner = uName;
        }
      }
      Integer[] oldEntry = user2ScoreAndWin.get(winner);
      if (oldEntry[1] == null) {
        oldEntry[1] = 0;
      }
      oldEntry[1] += 1;
      user2ScoreAndWin.put(winner, oldEntry);
    }

    //Iterate over all gameScores and now count scores
    for (GameScoreBoard gameScoreBoard : gameScoreBoards) {
      for (String uName : gameScoreBoard.getPlayerScores().keySet()) {
        Integer[] oldEntry = user2ScoreAndWin.get(uName);
        if (oldEntry[0] == null) {
          oldEntry[0] = 0;
        }
        oldEntry[0] += gameScoreBoard.getPlayerScores().get(uName);
        user2ScoreAndWin.put(uName, oldEntry);
      }
    }

    for (String uName : user2ScoreAndWin.keySet()) {
      if (user2ScoreAndWin.get(uName)[0] == null) {
        Integer[] oldEntry = user2ScoreAndWin.get(uName);
        if (oldEntry[0] == null) {
          oldEntry[0] = 0;
        }
        user2ScoreAndWin.put(uName, oldEntry);
      }
      if (user2ScoreAndWin.get(uName)[1] == null) {

        Integer[] oldEntry = user2ScoreAndWin.get(uName);
        if (oldEntry[1] == null) {
          oldEntry[1] = 0;
        }
        user2ScoreAndWin.put(uName, oldEntry);
      }
    }

    GameSessionScoreBoard gameSessionScoreBoard = new GameSessionScoreBoard(gamesPlayed,
        user2ScoreAndWin);

    return gameSessionScoreBoard;

  }

  /**
   * Get gameSessionScoreBoard for a specific gameSession with an id.
   *
   * @param gameSessionScoreId as String
   * @return GameSessionScoreBoard
   */
  public static GameSessionScoreBoard getGameSessionScoreBoard(String gameSessionScoreId) {
    DbServer dbServer = null;
    try {
      dbServer = DbServer.getInstance();
      if (dbServer == null) {
        throw new Exception("Connection to DB failed");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    ArrayList<GameScoreBoard> gameScoreBoards = dbServer.getGameSessionScores(gameSessionScoreId);
    return analyzeGames(gameScoreBoards);
  }

  /**
   * Get a GameSessionScoreboard based on gameIds.
   *
   * @param gameIds as StringArray.
   * @return GameSessionScoreBoard
   */
  public static GameSessionScoreBoard getGameSessionScoreBoard(String[] gameIds) {
    DbServer dbServer = null;
    try {
      dbServer = DbServer.getInstance();
      if (dbServer == null) {
        throw new Exception("Connection to DB failed");
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    ArrayList<GameScoreBoard> gameScoreBoards = new ArrayList<>();

    for (String gameScoreId : gameIds) {
      gameScoreBoards.add(dbServer.getGameScores(gameScoreId));
    }

    return analyzeGames(gameScoreBoards);
  }

  /**
   * This function gathers all the information about previously hosted games
   *
   * @return
   */
  public static LobbyScoreBoard getLobbyScoreBoard() {
    DbServer dbServer = null;
    try {
      dbServer = DbServer.getInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
    int gamesPlayed = dbServer.getNumberOfLocalGames();
    HashMap<String, Integer[]> playerWins = dbServer.getGameWins();

    return new LobbyScoreBoard(gamesPlayed, playerWins);

  }

  /**
   * Calculate the winner of the stored games.
   *
   * @return String
   */
  public static String getWinner(GameScoreBoard gameScoreBoard) {
    String winnerName = "";
    HashMap<String, Integer> playerScores = gameScoreBoard.getPlayerScores();
    int maxScore = 0;
    for (String username : playerScores.keySet()) {
      if (playerScores.get(username) > maxScore) {
        maxScore = playerScores.get(username);
        winnerName = username;
      }
    }
    return winnerName;
  }

}
