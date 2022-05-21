package net.packet.account;

import net.packet.abstr.Packet;

/**
 * Packet sent by the client to the RESTFUL server in order to receive a token.
 *
 * @author tbuscher
 */
public class RestfulLoginPacket extends Packet {

  String username;
  String passwordHash;

  /**
   * Empty constructor for Jackson Encoding.
   */
  public RestfulLoginPacket() {
    this.username = "";
    this.passwordHash = "";
  }

  /**
   * Constructor.
   *
   * @param username     as String
   * @param passwordHash as String
   */
  public RestfulLoginPacket(String username, String passwordHash) {
    this.username = username;
    this.passwordHash = passwordHash;
  }

  /**
   * Getter.
   *
   * @return username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Getter.
   *
   * @return passwordHash
   */
  public String getPasswordHash() {
    return passwordHash;
  }
}
