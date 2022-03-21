package game.model.chat;

import game.model.Player;
import java.util.ArrayList;

/**
 * @author tgeilen
 * @Date 21.03.22
 */
public class Chat {

	private ArrayList<ChatMessage> chatMessages;

	public Chat(){
		this.chatMessages = new ArrayList<ChatMessage>();

	}

	public void addMessage(Player player, String message){
		ChatMessage msg = new ChatMessage(player, message);
		chatMessages.add(msg);
	}

	public ArrayList<ChatMessage> getChat(){
		return chatMessages;
	}

	@Override
	public String toString() {
		String result = "";
		for(ChatMessage msg : this.chatMessages){
			result +=  msg.getPlayer().getName() + ": ";
			result +=  msg.getMessage() + "\n";
		}
		return result;
	}

}
