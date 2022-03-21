package game.model;

import java.io.Serializable;

/**
 * This class represents one player of the game.
 */
public class Player implements Serializable {

  /**
   * Name of the player.
   */
  private final String name;

  /**
   * Type of the player.
   */
  private final PlayerType type;

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
   * current session of player
   */
  private Session session;

  /**
   * ___deprecated___ use player(name) and joinBoard() instead (ask tobi)
   * initializing the default values of a player
   *
   * @param name name of the player
   * @param type type of the player
   * @author tiotto
   */
  public Player(String name, PlayerType type, int startX, int startY) {
    this.name = name;
    this.type = type;
    this.score = 0;
    this.startX = startX;
    this.startY = startY;
  }

  /**
   * creates a new Player with only a name
   * @param name
   * @author tgeilen
   */
  public Player(String name, PlayerType playerType){
    this.name = name;
    this.type = playerType;
  }

  /**
   * joines an existing session
   * @param session
   * @author tgeilen
   */
  public void joinSession (Session session){
    this.session = session;
  }


  /**
   * adds more info about player when joining a board
   * @param startX
   * @param startY
   * @author tgeilen
   */

  public void joinBoard(int startX, int startY){
    this.score = 0;
    this.startX = startX;
    this.startY = startY;
  }

  /**
   * add message to chat
   * @param msg
   */
  public void addChatMessage(String msg){
    this.session.getChat().addMessage(this, msg);

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

  public String getName() {
    return name;
  }

  public String toString() {
    return name;
  }
}
