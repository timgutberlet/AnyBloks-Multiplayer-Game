package net.packet.game;

import java.util.HashMap;
import net.packet.abstr.Packet;

/**
 * Packet sent by server to all players specifying the order of all players.
 *
 * @author tbuscher
 */
public class PlayerOrderPacket extends Packet {

  HashMap<String, Integer> playerOrder;

  public PlayerOrderPacket() {
  }

  /**
   * constructor.
   *
   * @param playerOrder playerOrder
   */
  public PlayerOrderPacket(HashMap<String, Integer> playerOrder) {
    this.playerOrder = playerOrder;
  }


  /**
   * Getter.
   *
   * @return playerOrder
   */
  public HashMap<String, Integer> getPlayerOrder() {
    return playerOrder;
  }
}
