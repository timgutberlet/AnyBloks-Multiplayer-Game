package net.packet;

import game.model.GameState;

/**
 * Packet to send GameState and allow Players to determine they have to make a move
 *
 * @author tbuscher
 */
public class GameUpdatePacket extends Packet {
  private GameState gameState;
  private String nextPlayersUsername;

  /**
   * Constructor
   *
   * @param gameState gs
   * @param nextPlayersUsername npu
   */
  public GameUpdatePacket(GameState gameState, String nextPlayersUsername){
    this.gameState = gameState;
    this.nextPlayersUsername = nextPlayersUsername;
  }

  /**
   * Constructor
   */
  public GameUpdatePacket(){

  }


  /**
   * Getter
   */
  public String getNextPlayersUsername() {
    return nextPlayersUsername;
  }

  /**
   * Getter
   */
  public GameState getGameState() {
    return gameState;
  }

  /**
   * Setter
   */
  public void setGameState(GameState gameState) {
    this.gameState = gameState;
  }

  /**
   * Setter
   */
  public void setNextPlayersUsername(String nextPlayersUsername) {
    this.nextPlayersUsername = nextPlayersUsername;
  }
}
