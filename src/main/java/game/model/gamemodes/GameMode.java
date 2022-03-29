package game.model.gamemodes;

/**
 * @author tgeilen
 * @Date 21.03.22
 */
public class GameMode {

  private final String name;
  private final int neededPlayers;

  public GameMode(String name, int num) {
    this.name = name;
    this.neededPlayers = num;

  }

  public String getName() {
    return name;
  }

  public int getNeededPlayers() {
    return neededPlayers;
  }
}
