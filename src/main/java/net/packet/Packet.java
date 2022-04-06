package net.packet;

import java.io.Serializable;

/**
 * To be used for all communication between server & clients, for a list see PacketType.
 *
 * @author tbuscher
 */
public class Packet implements Serializable {

  /**
   * To implement Serializable.
   */
  private static final long serialVersionUID = 1L;

  /**
   * Holds type of the packet (one of the types of the Enum Packet Type).
   */
  private final PacketType type;

  /**
   * Holds the original packet
   */
  private Object originalPacket;

  /**
   * Constructor for packets.
   *
   * @param type           type of packet to be created
   * @param originalPacket the packet of a certain type, that can be cast back later
   */
  public Packet(PacketType type, Object originalPacket) {
    this.type = type;
    this.originalPacket = originalPacket;
  }

  /**
   * Constructor for packets.
   *
   * @param type type of packet to be created
   */
  public Packet(PacketType type) {
    this.type = type;
  }

  /**
   * affords access to PacketType of a packet. TODO: remove. this is only temporary until other
   * packets are adapted (no longer extend this).
   *
   * @return PacketType type of packet
   */
  public PacketType getPacketType() {
    return this.type;
  }

  /**
   * affords access to the original Packet cast into an Object. get the Packet of a type by using
   * this and then casting back.
   *
   * @return Object originalPacket cast to Object
   */
  public Object getOriginalPacket() {
    return this.originalPacket;
  }

}
