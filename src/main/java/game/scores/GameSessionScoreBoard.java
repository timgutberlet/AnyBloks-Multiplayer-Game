package game.scores;

import java.util.HashMap;

/**
 * represents the scorebaord of a gamesession.
 *
 * @author tbuscher
 */
public class GameSessionScoreBoard {

  public int gamesPlayed;

  public HashMap<String, Integer[]> usernames2pointsAndWins;

  /**
   * Constructor.
   *
   * @param gamesPlayed             as int
   * @param usernames2pointsAndWins as Hashmap
   */
  public GameSessionScoreBoard(int gamesPlayed,
      HashMap<String, Integer[]> usernames2pointsAndWins) {
    this.gamesPlayed = gamesPlayed;
    this.usernames2pointsAndWins = usernames2pointsAndWins;
  }

  /**
   * Default Constructor for Jackson.
   */
  public GameSessionScoreBoard() {
    this.gamesPlayed = 0;
    this.usernames2pointsAndWins = null;
  }

}
