package net.packet.account;

import net.packet.abstr.Packet;

/**
 * Packet send by client to attempt Login.
 *
 * @author tbuscher
 */
public class LoginRequestPacket extends Packet {

  private String username;
  private String passwordHash;

  /**
   * empty constructor for jackson.
   *
   * @author tgeilen
   */
  public LoginRequestPacket() {

  }

  /**
   * Constructor.
   *
   * @param username     of account
   * @param passwordHash user thinks belongs to account
   */
  public LoginRequestPacket(String username, String passwordHash) {
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
