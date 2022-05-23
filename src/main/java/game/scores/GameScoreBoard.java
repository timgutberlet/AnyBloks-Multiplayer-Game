package game.scores;

import java.util.HashMap;

/**
 * Class to implement a ScoreBoard within a Game. Contains info about: The gamemode.
 * usernames of
 * players and their scores
 *
 * @author tbuscher
 */
public class GameScoreBoard {

  public String gamemode = "";
  public HashMap<String, Integer> playerScores = new HashMap();

  /**
   * Default Constructor with gamemode and Hashmap of username and score.
   *
   * @param gamemode
   * @param playerScores
   */
  public GameScoreBoard(String gamemode, HashMap<String, Integer> playerScores) {
    this.gamemode = gamemode;
    this.playerScores = playerScores;
  }

  /**
   * Constructor for Jackson.
   */
  public GameScoreBoard(){
    this.gamemode = null;
    this.playerScores = null;
  }

  /**
   * Getter.
   */
  public HashMap<String, Integer> getPlayerScores() {
    return playerScores;
  }

  /**
   * Getter.
   */
  public String getGamemode() {
    return gamemode;
  }

  /**
   * Calculate the winner of the stored games.
   *
   * @return String
   */
  public String getWinner(){
    String winnerName = "";
    int maxScore = 0;
    for(String username : playerScores.keySet()){
      if(playerScores.get(username) > maxScore){
        maxScore = playerScores.get(username);
        winnerName = username;
      }
    }
    return winnerName;
  }
}
