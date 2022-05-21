package net.packet.chat;

import game.model.chat.Chat;
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

  private final Chat chat;

  /**
   * default constructor for jackson.
   */
  public ChatMessagePacket() {
    this.chat = null;
  }

  /**
   * Create new ChatMessagePacket.
   *
   * @param chat chatMessage to be send
   */
  public ChatMessagePacket(Chat chat) {
    this.chat = chat;
  }

  public Chat getChat() {
    return this.chat;
  }
}