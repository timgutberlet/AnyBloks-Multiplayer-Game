package net.packet.game;

import game.model.gamemodes.GameMode;
import game.model.player.PlayerType;
import java.util.LinkedList;
import net.packet.abstr.Packet;

/**
 * send to the server from the host, when he wants to start a new game.
 *
 * @author tgeilen
 * @Date 10.05.22
 */
public class InitGamePacket extends Packet {

  private final LinkedList<GameMode> gameMode;
  private final LinkedList<PlayerType> playerTypes;
  private final PlayerType defaultAi;

  /**
   * empty constructor for jackson.
   */
  public InitGamePacket() {
    this.gameMode = null;
    this.playerTypes = null;
    this.defaultAi = null;
  }


  /**
   * constructor.
   *
   * @param gameMode    gameMode
   * @param playerTypes playerTypes
   */
  public InitGamePacket(LinkedList<GameMode> gameMode, LinkedList<PlayerType> playerTypes) {
    this.gameMode = gameMode;
    this.playerTypes = playerTypes;
    this.defaultAi = null;
  }

  /**
   * constructor.
   *
   * @param gameMode Gamemode
   */
  public InitGamePacket(LinkedList<GameMode> gameMode) {
    this.gameMode = gameMode;
    this.playerTypes = null;
    this.defaultAi = null;
  }

  /**
   * constructor.
   *
   * @param gameMode  gameMode
   * @param defaultAi defaultAi
   */
  public InitGamePacket(LinkedList<GameMode> gameMode, PlayerType defaultAi) {
    this.gameMode = gameMode;
    this.playerTypes = null;
    this.defaultAi = defaultAi;
  }

  /**
   * getter.
   *
   * @return gameMode.
   */
  public LinkedList<GameMode> getGameMode() {
    return gameMode;
  }

  /**
   * getter.
   *
   * @return playerTypes
   */
  public LinkedList<PlayerType> getPlayerTypes() {
    return playerTypes;
  }

  /**
   * getter.
   *
   * @return playerType
   */
  public PlayerType getDefaultAi() {
    return defaultAi;
  }

}
