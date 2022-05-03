package net.packet;

import java.io.Serializable;

/**
 * The class packets are wrapped in, when sent
 *
 * @author tbuscher
 */
public class WrappedPacket implements Serializable {

  /**
   * To implement Serializable.
   */
  private static final long serialVersionUID = 1L;

  private PacketType type;
  private Packet packet;

  /**
   * Constructor
   *
   * @param type of packet
   * @param packet to be sent
   */
  public  WrappedPacket(PacketType type, Packet packet){
    this.type = type;
    this.packet = packet;
  }

  /**
   * Getter
   *
   * @return packet packet
   */
  public Packet getPacket() {
    return packet;
  }

  /**
   * Getter
   *
   * @return PacketType type
   */
  public PacketType getPacketType() {
    return type;
  }
}
