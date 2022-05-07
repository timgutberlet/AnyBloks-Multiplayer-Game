package net.packet;

/**
 * Packet sent by server after receiving LoginRequestPacket If ErrorMessage is empty (="") the login
 * was successful
 *
 * @author tbuscher
 */
public class LoginResponsePacket extends Packet{

  String errorMessage;

  /**
   * Constructor, leave errorMessage = "" if no error occurred
   *
   * @param errorMessage
   */
  public LoginResponsePacket(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  /**
   * Getter
   *
   * @return errorMessage
   */
  public String getErrorMessage() {
    return errorMessage;
  }

}
