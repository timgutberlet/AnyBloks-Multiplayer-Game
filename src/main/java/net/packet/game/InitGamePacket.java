package net.packet.game;

import game.model.gamemodes.GameMode;
import net.packet.abstr.Packet;

/**
 * @author tgeilen
 * @Date 10.05.22
 */
public class InitGamePacket extends Packet {

  private final GameMode gameMode;

  public InitGamePacket() {
    this.gameMode = null;
  }

  public InitGamePacket(GameMode gameMode) {
    this.gameMode = gameMode;
  }

  public GameMode getGameMode() {
    return gameMode;
  }
}
