package net.packet.game;

import net.packet.abstr.Packet;

/**
 * a packet that gets send when a player is kicked.
 *
 * @author tgeilen
 * @Date 23.05.22
 */
public class PlayerKickPacket extends Packet {

  private final String username;

  /**
   * empty constructor for jackson.
   */
  public PlayerKickPacket() {
    this.username = "";
  }

  /**
   * constructor.
   */
  public PlayerKickPacket(String username) {
    this.username = username;
  }

  /**
   * getter.
   *
   * @return username
   */
  public String getUsername() {
    return username;
  }
}
