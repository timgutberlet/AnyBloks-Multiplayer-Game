package net.packet.game;

import net.packet.abstr.Packet;

/**
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
   * getter
   *
   * @return
   */
  public String getUsername() {
    return username;
  }
}
