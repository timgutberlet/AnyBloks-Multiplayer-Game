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
  private String token;
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
   * @param username of account
   * @param token    user thinks belongs to account
   */
  public LoginRequestPacket(String username, String token, PlayerType playerType) {
    this.username = username;
    this.token = token;
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
  public String getToken() {
    return token;
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
