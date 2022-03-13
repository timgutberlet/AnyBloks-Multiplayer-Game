package Chat;

import java.sql.Timestamp;
import java.util.Date;
import GameLogic.Player;

/**
 * @author tgeilen
 * @Date 13.03.22
 */
public class ChatMessage {

	private Player player;
	private Timestamp time;
	private String message;


	public ChatMessage(Player player, String message){

		this.player = player;

		Date date = new Date();
		this.time = new Timestamp(date.getTime());

		this.message = message;

	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Timestamp getTime() {
		return time;
	}

	public void setTime(Timestamp time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
