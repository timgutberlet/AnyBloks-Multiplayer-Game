package net.packet.account;

import net.packet.abstr.Packet;

/**
 * Packet send by client to try and create an account.
 *
 * @author tbuscher
 */
public class CreateAccountRequestPacket extends Packet {

  /**
   * Desired username.
   */
  private final String username;

  /**
   * HASHED password to set.
   */
  private final String passwordHash;

  /**
   * Packet for Creating an Account by a user.
   *
   * @param username     desired username
   * @param passwordHash HASHED password to set
   */
  public CreateAccountRequestPacket(String username, String passwordHash) {
    this.username = username;
    this.passwordHash = passwordHash;
  }

  /**
   * default constructor.
   */
  public CreateAccountRequestPacket() {
    username = "";
    passwordHash = "";
  }

  /**
   * public getter for desired username.
   *
   * @return desired username
   */
  public String getUsername() {
    return this.username;
  }

  /**
   * public getter for hashed password.
   *
   * @return hashed password
   */
  public String getPasswordHash() {
    return this.passwordHash;
  }

}
