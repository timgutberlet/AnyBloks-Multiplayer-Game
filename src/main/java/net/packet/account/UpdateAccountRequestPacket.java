package net.packet.account;

import net.packet.abstr.Packet;

/**
 * Packet sent to server in case account is supposed to be changed.
 *
 * @author tbuscher
 */
public class UpdateAccountRequestPacket extends Packet {

  private final boolean delete;
  private final String passwordHash;
  private final String username;

  /**
   * Constructor.
   *
   * @param delete       flag to signal user wants to delete account
   * @param passwordHash of account
   * @param username     of account
   */
  public UpdateAccountRequestPacket(boolean delete, String passwordHash, String username) {
    this.delete = delete;
    this.passwordHash = passwordHash;
    this.username = username;
  }

  /**
   * Getter.
   */
  public boolean getDelete() {
    return delete;
  }

  /**
   * Getter.
   */
  public String getPasswordHash() {
    return passwordHash;
  }

  /**
   * Getter.
   */
  public String getUsername() {
    return username;
  }
}
