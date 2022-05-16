package net.packet.game;

import game.model.gamemodes.GameMode;
import java.util.ArrayList;
import java.util.LinkedList;
import net.packet.abstr.Packet;

/**
 * @author tgeilen
 * @Date 10.05.22
 */
public class InitGamePacket extends Packet {

  private final LinkedList<GameMode> gameMode;

  public InitGamePacket() {
    this.gameMode = null;
  }

  public InitGamePacket(LinkedList<GameMode> gameMode) {
    this.gameMode = gameMode;
  }

  public LinkedList<GameMode> getGameMode() {
    return gameMode;
  }
}
