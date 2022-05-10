package net.packet.chat;

import net.packet.abstr.Packet;

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
  private final String sender;

  /**
   * Create new ChatMessagePacket.
   *
   * @param text text of the message
   * @param username of sender
   */
  public ChatMessagePacket(String text, String username) {
    this.text = text;
    this.sender = username;
  }

  /**
   * public getter for text of message.
   *
   * @return text of the message
   */
  public String getText() {
    return this.text;
  }

  /**
   * Getter
   */
  public String getSender() {
    return sender;
  }
}
