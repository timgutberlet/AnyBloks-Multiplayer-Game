package net.packet;

/**
 * Packet for sending a chat message into a chatroom.
 *
 * @author tbuscher
 */
public class ChatMessagePacket extends Packet {


  /**
   * Contains the text of the message.
   */
  private final String text;

  /**
   * Create new ChatMessagePacket.
   *
   * @param text text of the message
   */
  public ChatMessagePacket(String text) {
    this.text = text;
  }

  /**
   * public getter for text of message.
   *
   * @return text of the message
   */
  public String getText() {
    return this.text;
  }

}
