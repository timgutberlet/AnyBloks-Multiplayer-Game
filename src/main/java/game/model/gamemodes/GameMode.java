package game.model.gamemodes;

/**
 * represents the game modes.
 *
 * @author tgeilen
 * @Date 21.03.22
 */
public class GameMode {

  private String name;
  private int neededPlayers;

  /**
   * empty constructor for jackson.
   */
  public GameMode() {

  }

  /**
   * creates a new game mode.
   *
   * @param name of the game mode
   * @param num  number of needed players
   */
  public GameMode(String name, int num) {
    this.name = name;
    this.neededPlayers = num;

  }

  /**
   * gets the name of the game mode.
   *
   * @return name of the game mode
   */
  public String getName() {
    return name;
  }

  /**
   * gets the number of needed player.
   *
   * @return number of needed player
   */
  public int getNeededPlayers() {
    return neededPlayers;
  }
}
