package net.packet.account;

import net.packet.abstr.Packet;

/**
 * Packet sent to server in case account is supposed to be changed. Note that there is NO
 * UpdateAccountResponsePacket, as Restful Server sets the status to 200 / 403 and responds that way
 * without sending a packet back
 *
 * @author tbuscher
 */
public class UpdateAccountRequestPacket extends Packet {

  private final String passwordHash;
  private final String username;
  private final String updatedPasswordHash;

  /**
   * Constructor.
   *
   * @param passwordHash        of account
   * @param username            of account
   * @param updatedPasswordHash user wants to use from now on
   */
  public UpdateAccountRequestPacket(String passwordHash, String username,
      String updatedPasswordHash) {
    this.passwordHash = passwordHash;
    this.username = username;
    this.updatedPasswordHash = updatedPasswordHash;
  }

  /**
   * Default Constructor for Jackson.
   */
  public UpdateAccountRequestPacket() {
    this.passwordHash = "";
    this.updatedPasswordHash = "";
    this.username = "";
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

  /**
   * Getter.
   */
  public String getUpdatedPasswordHash() {
    return updatedPasswordHash;
  }
}
