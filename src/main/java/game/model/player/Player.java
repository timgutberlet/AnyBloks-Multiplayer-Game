package game.model.player;

import game.model.Debug;
import game.model.GameSession;
import game.model.GameState;
import game.model.Turn;
import game.model.polygon.Poly;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * This class represents one player of the game.
 */
public class Player implements Serializable {

  private final String[] wordlist = {"Great Move!!", "Let's go!!", "Is that all you've got?",
      "n00b",
      "How can become as good as yoU?"};

  /**
   * Sets null turn
   */
  private boolean nullTurn = false;
  /**
   * Name of the player.
   */
  private String username;
  /**
   * Type of the player.
   */
  private PlayerType type;
  /**
   * Boolean if player is Connected
   */
  private boolean isPlayerConnected;

  /**
   * Current score of the player.
   */
  private int score;
  /**
   * tells wether player is played by AI or not
   */
  private Boolean isAI;
  /**
   * tells whether player is the host of a session
   */
  private Boolean isHost;
  /**
   * word list for automated chat messages
   */
  /**
   * number in range (0,3) stating the order of the player
   */
  private int orderNum;
  /**
   * Check if AI Calc is currently running
   */
  private boolean aiCalcRunning;
  /**
   * Poly the Player has Selected in the UI
   */
  private Poly selectedPoly;
  /**
   * Boolean saying if the player has active turn or not
   */
  private boolean hasTurn;
  private Turn selectedTurn;
  private Turn setTurn;
  private Boolean threadIsActive = false;

  //public EndpointClient endpointClient;
  //public Session session;

  /**
   * values of a player
   *
   * @param username name of the player
   * @param type     type of the player
   * @author tiotto
   */
  public Player(String username, PlayerType type) {
    this.username = username;
    this.type = type;
    this.score = 0;
    this.isAI = (type.equals(PlayerType.AI_EASY) || type.equals(PlayerType.AI_MIDDLE) ||
        type.equals(PlayerType.AI_HARD) || type.equals(PlayerType.AI_RANDOM));
    this.isHost = false;
    this.selectedTurn = null;
    if (!this.isAI) {
      this.aiCalcRunning = false;

    }
  }

  /**
   * values of a player
   *
   * @param username name of the player
   * @param type     type of the player
   * @author tbuscher
   */
  public Player(String username, PlayerType type, boolean isHost) {
    this(username, type);
    this.isHost = isHost;
  }

  /**
   * empty constructor for jackson
   */

  public Player() {

  }

  /**
   * @return returns if boolean connected
   * @author tgutberl
   */
  public boolean isPlayerConnected() {
    return this.isPlayerConnected;
  }

  public void setPlayerConnected(boolean playerConnected) {
    this.isPlayerConnected = playerConnected;
  }

  public void run() {
    this.threadIsActive = true;
    Debug.printMessage(this, this.username + " Thread has been started");
    while (this.threadIsActive) {

    }
  }


  public void killThread() {
    this.threadIsActive = false;
  }

  /**
   * join an existing session
   *
   * @param gameSession
   * @author tgeilen
   */
  public void setGameSession(GameSession gameSession) {
    //this.gameSession = gameSession;
  }


  /**
   * add message to chat
   *
   * @param msg
   * @author tgeilen
   */
  public void addChatMessage(String msg) {
    //this.gameSession.getChat().addMessage(this, msg);

  }
  /**
   * Function to set Nullturn
   */
  public void nullTurn(){
    this.setSelectedTurn(null);
    this.nullTurn = true;
  }
  /**
   * function used to return the next turn of a player
   *
   * @param gameState
   * @return
   * @author tgeilen
   */
  public Turn makeTurn(GameState gameState) {
    if (this.isAI) {
      this.aiCalcRunning = true;

      try {
        TimeUnit.MILLISECONDS.sleep(200);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      return AI.calculateNextMove(gameState, this);
    } else {
      this.aiCalcRunning = false;
      while (this.selectedTurn == null && nullTurn == false) {
        try {
          //Debug.printMessage(this, this.getUsername() + " " + this);
          // Debug.printMessage(this, "A TURN NEEDS TO BE MADE");
          Thread.sleep(10);
          //Debug.printMessage(this, "Waiting for PlayerInput from this " + this);
        } catch (InterruptedException e) {
        }
      }
      System.out.println("Turn Selected from player " + this);

      gameState.playTurn(this.selectedTurn);
      Turn returnTurn = this.selectedTurn;
      this.selectedTurn = null;
      this.aiCalcRunning = true;
      this.nullTurn = false;
      return returnTurn;
    }
  }

  public void setSelectedTurn(Turn turn) {
    this.selectedTurn = turn;
  }

  public Boolean getAiCalcRunning() {
    return this.aiCalcRunning;
  }

  public void setAiCalcRunning(boolean aiCalcRunning) {
    this.aiCalcRunning = aiCalcRunning;
  }

  public Poly getSelectedPoly() {
    return this.selectedPoly;
  }

  public void setSelectedPoly(Poly selectedPoly) {
    this.selectedPoly = selectedPoly;
  }

  /**
   * function that adds a random chat message from the given wordlist
   *
   * @author tgeilen
   */
  public void talk() {
    if (Math.floor(Math.random() * 100) % 2 == 0) {

      String msg = this.wordlist[(int) Math.floor(Math.random() * this.wordlist.length)];

      //this.gameSession.getChat().addMessage(this, msg);
    }
  }

  public PlayerType getType() {
    return type;
  }

  public String getUsername() {
    return username;
  }

  //public String toString() {
  //  return username;
  //}

  public int getOrderNum() {
    return this.orderNum;
  }

  public void setAI(Boolean AI) {
    isAI = AI;
  }

  public Boolean isAI() {
    return isAI;
  }

  public void setType(PlayerType type) {
    this.type = type;
  }

  @Override
  public boolean equals(Object object){
    Player p = (Player) object;
    return this.username.equals(p.getUsername());
  }
}
