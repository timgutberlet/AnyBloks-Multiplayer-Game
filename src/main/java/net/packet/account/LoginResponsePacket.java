package net.packet.account;

import net.packet.abstr.Packet;

/**
 * Packet sent by server after receiving LoginRequestPacket If ErrorMessage is empty (="") the login
 * was successful.
 *
 * @author tbuscher
 */
public class LoginResponsePacket extends Packet {

  String errorMessage;
  String loginStatus;

  /**
   * Constructor.
   *
   * @param errorMessage in case of failure
   */
  public LoginResponsePacket(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  /**
   * Constructor.
   *
   * @param errorMessage in case of failure
   * @param loginStatus to which the errorMessage is supposed to be displayed
   */
  public LoginResponsePacket(String errorMessage, String loginStatus) {
    this.errorMessage = errorMessage;
    this.loginStatus = loginStatus;
  }
  /**
   * Getter.
   *
   * @return errorMessage
   */
  public String getErrorMessage() {
    return errorMessage;
  }

  public String getLoginStatus() {
    return loginStatus;
  }
}
