package game.model.chat;

import game.model.Debug;
import game.model.Player;
import java.util.ArrayList;

/**
 * @author tgeilen
 * @Date 21.03.22
 */
public class Chat extends Thread {

  private final ArrayList<ChatMessage> chatMessages;
  private boolean running;

  public Chat() {
    this.chatMessages = new ArrayList<ChatMessage>();

  }

  /**
   * the chat runs in parallel to the main game, therefore it lives in its own thread
   * @author tgeilen
   */
  public void run(){
    this.running = true;
    System.out.println(Thread.currentThread().getId() + ": Message Thread started");

    //while(this.running){
    //  if(false) { //recieve message  from server
        //System.out.println(message);
    //  }
   // }
  }

  /**
   * as there is no elegant way to kill a thread, a simple boolean variable is used
   * @author tgeilen
   */
  public void stopThread(){
    this.running = false;
  }

  /**
   * function to add a message to the chat
   *
   * for now it debugs to the console
   *
   * @param player
   * @param message
   * @author tgeilen
   */
  public void addMessage(Player player, String message) {
    ChatMessage msg = new ChatMessage(player, message);
    chatMessages.add(msg);
    Debug.printMessage("[CHAT] " + player.getName() + ": "+  message);
  }

  public ArrayList<ChatMessage> getChat() {
    return chatMessages;
  }

  @Override
  public String toString() {
    String result = "";
    for (ChatMessage msg : this.chatMessages) {
      result += msg.getPlayer().getName() + ": ";
      result += msg.getMessage() + "\n";
    }
    return result;
  }

}
