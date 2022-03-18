package net.packet;

/**
 * @author tbuscher
 */
public class CreateAccountResponsePacket extends Packet {

  /**
   * To implement Serializable
   */
  private static final long serialVersionUID = 1L;

  /**
   * flag whether request was successful
   */
  private final boolean accountCreated;

  /**
   * Error Message explained any errors encountered while processing CreateAccountRequestPacket
   */
  private final String errorMessage;

  /**
   * Packet sent by server in response to CreateAccountRequestPacket
   *
   * @param accountCreated flag whether request was handled successfully
   * @param errorMessage problem(s) that occured
   */
  public CreateAccountResponsePacket(boolean accountCreated, String errorMessage){
    super(PacketType.CREATE_ACCOUNT_RESPONSE_PACKET);
    this.accountCreated = accountCreated;
    this.errorMessage = errorMessage;
  }

  /**
   * public getter for accountCreated
   *
   * @return accountCreated
   */
  public boolean getAccountCreated(){
    return this.accountCreated;
  }

  /**
   * public getter for errorMessage
   *
   * @return errorMessage
   */
  public String getErrorMessage(){
    return this.errorMessage;
  }

}
