package net.packet.abstr;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)

/**
 * The class packets are wrapped in, when sent.
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
  private String username;
  private String token;

  /**
   * Constructor
   *
   * @param type   of packet
   * @param packet to be sent
   */
  public WrappedPacket(PacketType type, Packet packet) {
    this.type = type;
    this.packet = packet;
    this.username = "";
    this.token = "";
  }

  /**
   * Constructor
   *
   * @param type   of packet
   * @param packet to be sent
   */
  public WrappedPacket(PacketType type, Packet packet, String username, String token) {
    this.type = type;
    this.packet = packet;
    this.username = username;
    this.token = token;
  }

  /**
   * Constructor
   */
  public WrappedPacket() {
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
   * Setter.
   */
  public void setPacket(Packet packet) {
    this.packet = packet;
  }

  /**
   * Getter.
   *
   * @return PacketType type
   */
  public PacketType getPacketType() {
    return type;
  }

  /**
   * Setter.
   *
   * @param type PacketType
   */
  public void setPacketType(PacketType type) {
    this.type = type;
  }

  /**
   * Getter.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Setter.
   *
   * @param username String
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Getter.
   */
  public String getToken() {
    return token;
  }

  /**
   * Setter.
   *
   * @param token String
   */
  public void setToken(String token) {
    this.token = token;
  }
}
