package game.model.player;

import game.model.GameState;
import game.model.Session;
import game.model.Turn;
import game.model.player.AI;
import game.model.player.PlayerType;
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
   * tells wether player is played by AI or not
   */
  private Boolean isAI;

  /**
   * word list for automated chat messages
   */

  private String[] wordlist = {"Great Move!!", "Let's go!!", "Is that all you've got?", "n00b", "How can become as good as yoU?"};

  /**
   *
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
    this.isAI = (type.equals(PlayerType.AI_EASY)||type.equals(PlayerType.AI_MIDDLE)||
        type.equals(PlayerType.AI_HARD)||type.equals(PlayerType.AI_RANDOM));

  }

  /**
   * join an existing session
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
   * @author tgeilen
   */
  public void addChatMessage(String msg) {
    this.session.getChat().addMessage(this, msg);

  }

  /**
   * function used to return the next turn of a player
   * @param gameState
   * @return
   * @author tgeilen
   */
  public Turn makeTurn(GameState gameState){
    if(this.isAI){
      return AI.calculateNextMove(gameState, this);
    }
    else return null; //TODO add logic for non ai players

  }

  /**
   * function that adds a random chat message from the given wordlist
   * @author tgeilen
   */
  public void talk() {
    if (Math.floor(Math.random() * 100) % 2 == 0) {

      String msg = this.wordlist[(int)Math.floor(Math.random()*this.wordlist.length)];

      this.session.getChat().addMessage(this, msg);
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
