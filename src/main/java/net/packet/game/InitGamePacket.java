package net.packet.game;

import game.model.gamemodes.GameMode;
import game.model.player.PlayerType;
import java.util.ArrayList;
import java.util.LinkedList;
import net.packet.abstr.Packet;

/**
 * @author tgeilen
 * @Date 10.05.22
 */
public class InitGamePacket extends Packet {

  private final LinkedList<GameMode> gameMode;
  private final LinkedList<PlayerType> playerTypes;
  private final PlayerType defaultAi;


  public InitGamePacket() {
    this.gameMode = null;
    this.playerTypes = null;
    this.defaultAi = null;
  }

  public InitGamePacket(LinkedList<GameMode> gameMode, LinkedList<PlayerType> playerTypes) {
    this.gameMode = gameMode;
    this.playerTypes = playerTypes;
    this.defaultAi = null;
  }

  public InitGamePacket(LinkedList<GameMode> gameMode) {
    this.gameMode = gameMode;
    this.playerTypes = null;
    this.defaultAi = null;
  }

  public InitGamePacket(LinkedList<GameMode> gameMode,PlayerType defaultAi) {
    this.gameMode = gameMode;
    this.playerTypes = null;
    this.defaultAi = defaultAi;
  }

  public LinkedList<GameMode> getGameMode() {
    return gameMode;
  }

  public LinkedList<PlayerType> getPlayerTypes() {
    return playerTypes;
  }

  public PlayerType getDefaultAi() {
    return defaultAi;
  }

}
