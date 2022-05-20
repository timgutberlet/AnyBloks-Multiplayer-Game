package game.model.chat;

import game.model.Debug;
import game.model.player.Player;
import java.util.ArrayList;

/**
 * @author tgeilen
 * @Date 21.03.22
 */
public class Chat {

  private final ArrayList<ChatMessage> chatMessages;
  private boolean running;

  public Chat() {
    this.chatMessages = new ArrayList<ChatMessage>();

  }

  /**
   * the chat runs in parallel to the main game, therefore it lives in its own thread.
   *
   * @author tgeilen
   */
  public void run() {
    this.running = true;
    System.out.println(Thread.currentThread().getId() + ": Message Thread started");

    //while(this.running){
    //  if(false) { //recieve message  from server
    //System.out.println(message);
    //  }
    // }
  }

  /**
   * as there is no elegant way to kill a thread, a simple boolean variable is used.
   *
   * @author tgeilen
   */
  public void stopThread() {
    this.running = false;
  }

  /**
   * function to add a message to the chat.
   * <p>
   * for now it debugs to the console
   *
   * @param player
   * @param message
   * @author tgeilen
   */
  public void addMessage(Player player, String message) {
    ChatMessage msg = new ChatMessage(player.getUsername(), message);
    chatMessages.add(msg);
    Debug.printMessage("[CHAT] " + player.getUsername() + ": " + message);
  }

  /**
   * adds a given chat message.
   * @param chatMessage given chat message
   */
  public void addMessage(ChatMessage chatMessage) {
    chatMessages.add(chatMessage);
  }

  /**
   * returns all the chat messages.
   * @return returns a list with all chatMessages
   */
  public ArrayList<ChatMessage> getChatMessages() {
    return chatMessages;
  }

  /**
   * String representation of the chat.
   * @return string representation of the chat
   */
  @Override
  public String toString() {
    String result = "";
    for (ChatMessage msg : this.chatMessages) {
      result += msg.getUsername() + ": ";
      result += msg.getMessage() + "\n";
    }
    return result;
  }

}
