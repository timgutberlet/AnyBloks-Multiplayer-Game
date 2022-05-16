package net.packet.account;

import game.model.player.PlayerType;
import net.packet.abstr.Packet;

/**
 * Packet send by client to attempt Login.
 *
 * @author tbuscher
 */
public class LoginRequestPacket extends Packet {

  private String username;
  private String passwordHash;
  private PlayerType playerType;

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
  public LoginRequestPacket(String username, String passwordHash, PlayerType playerType) {
    this.username = username;
    this.passwordHash = passwordHash;
    this.playerType = playerType;
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

  /**
   * Getter.
   *
   * @return
   */
  public PlayerType getPlayerType() {
    return playerType;
  }
}
