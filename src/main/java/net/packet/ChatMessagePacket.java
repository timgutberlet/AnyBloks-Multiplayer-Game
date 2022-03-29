package net.packet;

/**
 * Packet for sending a chat message into a chatroom
 *
 * @author tbuscher
 */
public class ChatMessagePacket extends Packet {

  /**
   * To implement Serializable
   */
  private static final long serialVersionUID = 1L;


  /**
   * Contains the text of the message
   */
  private final String text;

  /**
   * Create new ChatMessagePacket
   *
   * @param text text of the message
   */
  public ChatMessagePacket(String text) {
    super(PacketType.CHAT_MESSAGE_PACKET);
    this.text = text;
  }

  /**
   * public getter for text of message
   *
   * @return text of the message
   */
  public String getText() {
    return this.text;
  }

}
