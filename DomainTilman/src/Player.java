import java.io.Serializable;

/**
 * This class represents one player of the game.
 *
 */
public class Player implements Serializable {

  /**
   * Name of the player.
   */
  private String name;

  /**
   * Type of the player.
   */
  private PlayerType type;

  /**
   * Current score of the player.
   */
  private int score;

  /**
   * Starting coordinate on the x-scale
   */
  private int startX;

  /**
   * Starting coordinate on the y-scale
   */
  private int startY;

  /**
   * initializing the default values of a player
   * @param name name of the player
   * @param type type of the player
   * @author tiotto
   */
  public Player(String name, PlayerType type,int startX, int startY){
    this.name = name;
    this.type = type;
    this.score = 0;
    this.startX = startX;
    this.startY = startY;
  }

  public PlayerType getType() {
    return type;
  }

  public int getStartX() {
    return startX;
  }

  public int getStartY() {
    return startY;
  }

  public String toString(){
    return name;
  }
}