import Game.Player;
import Game.Session;

/**
 * @author tgeilen
 * @Date 13.03.22
 */
public class Main {

	public static void main(String[] args){

		Session session = new Session();
		Player p1 = new Player("Player1");
		session.addPlayer(p1);

		Player p2 = new Player("Player2");
		session.addPlayer(p2);

		p1.addChatMessage("Hallo Welt");
		p2.addChatMessage("Hello World");

		System.out.println(session.getChat().toString());

		session.startGame("hello");

	}

}
