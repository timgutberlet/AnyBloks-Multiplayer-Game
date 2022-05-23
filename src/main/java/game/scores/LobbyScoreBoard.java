package game.scores;

import java.util.HashMap;

/**
 * Represents data needed to display statistics in the Lobby.
 *
 * @author tbuscher
 */
public class LobbyScoreBoard {

  public int gamesPlayedOnServer = 0;
  public HashMap<String, Integer> playerScores = new HashMap<>();

  /**
   * Constructor.
   *
   * @param gamesPlayedOnServer int
   * @param playerScores        HashMap of String, int
   */
  public LobbyScoreBoard(int gamesPlayedOnServer, HashMap<String, Integer> playerScores) {
    this.gamesPlayedOnServer = gamesPlayedOnServer;
    this.playerScores = playerScores;
  }

}
