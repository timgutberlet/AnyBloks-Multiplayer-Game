package net.packet.game;

import net.packet.abstr.Packet;

/**
 * @author tgeilen
 * @Date 23.05.22
 */
public class PlayerQuitPacket extends Packet {

  private final String username;

  /**
   * empty constructor needed for jackson
   */
  public PlayerQuitPacket() {
    this.username = "";
  }

  public PlayerQuitPacket(String username) {
    this.username = username;
  }

  public String getUsername() {
    return username;
  }
}
