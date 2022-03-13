import GameLogic.Player;
import GameLogic.Session;

/**
 * @author tgeilen
 * @Date 13.03.22
 */
public class Main {

	public static void main(String[] args){


		Player p1 = new Player("Player1");
		Session session = new Session(p1);
		

		Player p2 = new Player("Player2");
		session.addPlayer(p2);

		p1.addChatMessage("Hallo Welt");
		p2.addChatMessage("Hello World");

		System.out.println(session.getChat().toString());

		session.startGame("hello");

	}

}
