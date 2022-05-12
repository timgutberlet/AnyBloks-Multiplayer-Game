package net.packet.chat;

import game.model.chat.ChatMessage;
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

  private final ChatMessage chatMessage;

  /**
   * default constructor for jackson.
   */
  public ChatMessagePacket() {
    this.chatMessage = null;
  }

  /**
   * Create new ChatMessagePacket.
   *
   * @param text     text of the message
   * @param username of sender
   */
  public ChatMessagePacket(String text, String username) {
    this.chatMessage = new ChatMessage(username, text);
  }

  public ChatMessage getChatMessage() {
    return this.chatMessage;
  }
}