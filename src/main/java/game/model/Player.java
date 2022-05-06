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
   * current session of player
   */
  private Session session;

  /**
   * ___deprecated___ use player(name) and joinBoard() instead (ask tobi) initializing the default
   * values of a player
   *
   * @param name name of the player
   * @param type type of the player
   * @author tiotto
   */
  public Player(String name, PlayerType type) {
    this.name = name;
    this.type = type;
    this.score = 0;
  }

  /**
   * joines an existing session
   *
   * @param session
   * @author tgeilen
   */
  public void setSession(Session session) {
    this.session = session;
  }


  /**
   * add message to chat
   *
   * @param msg
   */
  public void addChatMessage(String msg) {
    this.session.getChat().addMessage(this, msg);

  }

  public void talk() {
    if (Math.floor(Math.random() * 100) % 4 == 0) {
      this.session.getChat().addMessage(this, "Hallo Welt " + this.score);
    }
  }

  public PlayerType getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public String toString() {
    return name;
  }
}
