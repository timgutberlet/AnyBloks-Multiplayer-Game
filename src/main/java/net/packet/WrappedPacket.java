package net.packet;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)

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
   * @param type   of packet
   * @param packet to be sent
   */
  public WrappedPacket(PacketType type, Packet packet) {
    this.type = type;
    this.packet = packet;
  }

  /**
   * Constructor
   */
  public WrappedPacket() {
  }

  ;

  /**
   * Getter
   *
   * @return packet packet
   */
  public Packet getPacket() {
    return packet;
  }

  /**
   * Setter
   */
  public void setPacket(Packet packet) {
    this.packet = packet;
  }

  /**
   * Getter
   *
   * @return PacketType type
   */
  public PacketType getPacketType() {
    return type;
  }

  /**
   * Setter
   */
  public void setPacketType(PacketType type) {
    this.type = type;
  }
}
