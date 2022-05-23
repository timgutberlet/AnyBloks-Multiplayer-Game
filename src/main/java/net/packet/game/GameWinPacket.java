package net.packet.game;

import game.model.gamemodes.GameMode;
import game.scores.GameScoreBoard;
import game.scores.GameSessionScoreBoard;
import java.util.LinkedList;
import net.packet.abstr.Packet;

/**
 * Packet sent after every game including scoreboards.
 *
 * @author tgeilen
 * @Date 10.05.22
 */
public class GameWinPacket extends Packet {

  private final GameScoreBoard gameScoreBoard;

  private final GameSessionScoreBoard gameSessionScoreBoard;

  private final LinkedList<GameMode> gameList;

  /**
   * Constructor without arguments for Jackson.
   */
  public GameWinPacket() {
    this.gameScoreBoard = null;
    this.gameSessionScoreBoard = null;
    this.gameList = null;
  }

  /**
   * Constructor.
   *
   * @param gameScoreBoard        of past game
   * @param gameSessionScoreBoard of entire gameSession
   */
  public GameWinPacket(GameScoreBoard gameScoreBoard, GameSessionScoreBoard gameSessionScoreBoard,
      LinkedList<GameMode> gameList) {
    this.gameScoreBoard = gameScoreBoard;
    this.gameSessionScoreBoard = gameSessionScoreBoard;
    this.gameList = gameList;
  }

  /**
   * Getter.
   *
   * @return gameScoreBoard
   */
  public GameScoreBoard getGameScoreBoard() {
    return gameScoreBoard;
  }

  /**
   * Getter.
   *
   * @return gameSessionScoreBoard
   */
  public GameSessionScoreBoard getGameSessionScoreBoard() {
    return gameSessionScoreBoard;
  }

  /**
   * Getter.
   *
   * @return
   */
  public LinkedList<GameMode> getGameList() {
    return gameList;
  }
}
