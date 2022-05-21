package net.packet.account;

import net.packet.abstr.Packet;

/**
 * @author tbuscher
 */
public class DeleteAccountRequestPacket extends Packet {

  private String username;
  private String passwordHash;

  /**
   * default constructor for Jackson
   */
  public DeleteAccountRequestPacket() {
    this.passwordHash = "";
    this.username = "";
  }

  /**
   * Constructor to used.
   *
   * @param username     as String
   * @param passwordHash of user as String (sha256 hashed)
   */
  public DeleteAccountRequestPacket(String username, String passwordHash) {
    this.username = username;
    this.passwordHash = passwordHash;
  }

  /**
   * Getter.
   *
   * @return username as String
   */
  public String getUsername() {
    return username;
  }

  /**
   * Setter.
   *
   * @param username to set
   */
  public void setUsername(String username) {
    this.username = username;
  }

  /**
   * Getter.
   *
   * @return passwordHash as String
   */
  public String getPasswordHash() {
    return passwordHash;
  }

  /**
   * Setter.
   *
   * @param passwordHash of user
   */
  public void setPasswordHash(String passwordHash) {
    this.passwordHash = passwordHash;
  }
}
