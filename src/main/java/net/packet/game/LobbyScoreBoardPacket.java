package net.packet.game;

import game.scores.LobbyScoreBoard;
import net.packet.abstr.Packet;

/**
 * Packet that sends a LobbyScoreBoard to remote clients.
 *
 * @author tbuscher
 */
public class LobbyScoreBoardPacket extends Packet {

  public LobbyScoreBoard lobbyScoreBoard;

  /**
   * Default Constructor for Jackson.
   */
  public LobbyScoreBoardPacket() {
    this.lobbyScoreBoard = null;
  }

  /**
   * Constructor with lobbyScoreBoard.
   *
   * @param lobbyScoreBoard
   */
  public LobbyScoreBoardPacket(LobbyScoreBoard lobbyScoreBoard) {
    this.lobbyScoreBoard = lobbyScoreBoard;
  }

  /**
   * Getter.
   *
   * @return lobbyScoreBoard
   */
  public LobbyScoreBoard getLobbyScoreBoard() {
    return this.lobbyScoreBoard;
  }
}
