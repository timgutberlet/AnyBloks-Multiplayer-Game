package net.packet.account;

import net.packet.abstr.Packet;

/**
 * Packet sent to server in case account is supposed to be changed
 *
 * @author tbuscher
 */
public class UpdateAccountRequestPacket extends Packet {

  private boolean delete;
  private String passwordHash;
  private String username;

  /**
   * Constructor
   *
   * @param delete
   * @param passwordHash
   * @param username
   */
  public UpdateAccountRequestPacket(boolean delete, String passwordHash, String username) {
    this.delete = delete;
    this.passwordHash = passwordHash;
    this.username = username;
  }

  /**
   * Getter
   */
  public boolean getDelete(){
    return delete;
  }

  /**
   * Getter
   */
  public String getPasswordHash(){
    return passwordHash;
  }

  /**
   * Getter
   */
  public String getUsername() {
    return username;
  }
}
