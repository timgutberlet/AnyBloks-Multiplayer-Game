package Game;

import Chat.Chat;
import java.util.ArrayList;

/**
 * @author tgeilen
 * @Date 13.03.22
 */
public class Session {

	private Chat chat;
	private ArrayList<Player> playerList;
	private Game game;

	public Session(){
		this.chat = new Chat();
		this.playerList = new ArrayList<Player>();
	}

	public void addPlayer(Player player){
		this.playerList.add(player);
		player.joinSession(this);
	}


	public void startGame(String gameType){
		this.game = new Game(this.playerList);

	}

	public Chat getChat(){
		return this.chat;
	}

	public Game getGame(){
		return this.game;
	}

}
