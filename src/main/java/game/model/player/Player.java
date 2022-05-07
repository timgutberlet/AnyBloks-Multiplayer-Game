package game.model.player;

import game.model.GameState;
import game.model.GameSession;
import game.model.Turn;
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
  private GameSession gameSession;

  /**
   * tells wether player is played by AI or not
   */
  private Boolean isAI;

  /**
   * tells whether player is the host of a session
   */
  private Boolean isHost;

  /**
   * number in range (0,3) stating the order of the player
   */
  private int orderNum;
  /**
   * word list for automated chat messages
   */

  private String[] wordlist = {"Great Move!!", "Let's go!!", "Is that all you've got?", "n00b",
      "How can become as good as yoU?"};

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
    this.isAI = (type.equals(PlayerType.AI_EASY) || type.equals(PlayerType.AI_MIDDLE) ||
        type.equals(PlayerType.AI_HARD) || type.equals(PlayerType.AI_RANDOM));
    this.isHost = false;

  }

  /**
   * values of a player
   *
   * @param name name of the player
   * @param type type of the player
   * @author tbuscher
   */
  public Player(String name, PlayerType type, boolean isHost) {
    this.name = name;
    this.type = type;
    this.score = 0;
    this.isAI = (type.equals(PlayerType.AI_EASY) || type.equals(PlayerType.AI_MIDDLE) ||
        type.equals(PlayerType.AI_HARD) || type.equals(PlayerType.AI_RANDOM));
    this.isHost = isHost;
  }

  /**
   * join an existing session
   *
   * @param gameSession
   * @author tgeilen
   */
  public void setSession(GameSession gameSession) {
    this.gameSession = gameSession;
  }


  /**
   * add message to chat
   *
   * @param msg
   * @author tgeilen
   */
  public void addChatMessage(String msg) {
    this.gameSession.getChat().addMessage(this, msg);

  }

  /**
   * function used to return the next turn of a player
   * @param gameState
   * @return
   * @author tgeilen
   */
  public Turn makeTurn(GameState gameState) {
    if (this.isAI) {
      return AI.calculateNextMove(gameState, this);
    } else {
      return null; //TODO add logic for non ai players
    }

  }

  /**
   * function that adds a random chat message from the given wordlist
   *
   * @author tgeilen
   */
  public void talk() {
    if (Math.floor(Math.random() * 100) % 2 == 0) {

      String msg = this.wordlist[(int) Math.floor(Math.random() * this.wordlist.length)];

      this.gameSession.getChat().addMessage(this, msg);
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

  public int getOrderNum() {return this.orderNum;};
}
