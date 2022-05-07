package net.packet;

/**
 * Packet send by client to attempt Login
 *
 * @author tbuscher
 */
public class LoginRequestPacket extends Packet {

  private String username;
  private String passwordHash;


  /**
   * Constructor
   *
   * @param username     of account
   * @param passwordHash user thinks belongs to account
   */
  public LoginRequestPacket(String username, String passwordHash) {
    this.username = username;
    this.passwordHash = passwordHash;
  }

  /**
   * Getter
   *
   * @return username
   */
  public String getUsername() {
    return username;
  }

  /**
   * Getter
   *
   * @return passwordHash
   */
  public String getPasswordHash() {
    return passwordHash;
  }
}
