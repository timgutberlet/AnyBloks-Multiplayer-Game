package game.scores;

import game.model.player.Player;

/**
 * @author tiotto
 * @date 23.05.2022
 */
public class PlayerScore {

  /**
   * number of played games on the considered server.
   */
  int numberOfGames;

  /**
   * three players with the most wins.
   */
  Player[] playerMostWins = new Player[3];

  /**
   * number of wins for the player with the most wins.
   */
  int[] numberMostWins = new int[3];

  public PlayerScore(int numberOfGames, Player[] playerMostWins, int[] numberMostWins) {
    this.numberOfGames = numberOfGames;
    this.playerMostWins = playerMostWins;
    this.numberMostWins = numberMostWins;
  }


  public int getNumberOfGames() {
    return numberOfGames;
  }

  public void setNumberOfGames(int numberOfGames) {
    this.numberOfGames = numberOfGames;
  }

  public Player[] getPlayerMostWins() {
    return playerMostWins;
  }

  public void setPlayerMostWins(Player[] playerMostWins) {
    this.playerMostWins = playerMostWins;
  }

  public int[] getNumberMostWins() {
    return numberMostWins;
  }

  public void setNumberMostWins(int[] numberMostWins) {
    this.numberMostWins = numberMostWins;
  }
}
