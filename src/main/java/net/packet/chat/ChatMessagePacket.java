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
   * @param chatMessage chatMessage to be send
   */
  public ChatMessagePacket(ChatMessage chatMessage) {
    this.chatMessage = chatMessage;
  }

  public ChatMessage getChatMessage() {
    return this.chatMessage;
  }
}