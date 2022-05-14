package game.model.gamemodes;

/**
 * @author tgeilen
 * @Date 21.03.22
 */
public class GameMode {

  private String name;
  private int neededPlayers;

  public GameMode() {

  }

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
