package net.packet.game;

import java.util.HashMap;
import net.packet.abstr.Packet;

/**
 * Packet sent by server to all players specifying the order of all players
 *
 * @author tbuscher
 */
public class PlayerOrderPacket extends Packet {
  HashMap<String, Integer> playerOrder;

  /**
   * Constructor
   *
   * @param playerOrder
   */
  public PlayerOrderPacket(HashMap<String, Integer> playerOrder){
    this.playerOrder = playerOrder;
  }

  /**
   * Getter
   */
  public HashMap<String, Integer> getPlayerOrder() {
    return playerOrder;
  }
}
