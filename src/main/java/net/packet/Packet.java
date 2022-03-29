package net.packet;

import java.io.Serializable;

/**
 * To be used for all communication between server & clients, for a list see PacketType
 *
 * @author tbuscher
 */
public abstract class Packet implements Serializable {

  /**
   * To implement Serializable
   */
  private static final long serialVersionUID = 1L;

  /**
   * Holds type of the packet (one of the types of the Enum Packet Type)
   */
  private final PacketType type;

  /**
   * Constructor for packets
   *
   * @param type type of packet to be created
   */
  public Packet(PacketType type) {
    this.type = type;
  }

  /**
   * affords access to PacketType of a packet
   *
   * @return PacketType type of packet
   */
  public PacketType getPacketType() {
    return this.type;
  }
}
