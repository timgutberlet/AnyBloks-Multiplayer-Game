
package game.model;

import game.model.gamemodes.GMClassic;
import game.model.player.Player;
import game.model.player.PlayerType;


public class MainT {

	/**
	 * a simple test class that shows how the domain works at the moment
	 * @param args
	 * @author tgeilen
	 */
  public static void main(String[] args) {

		Session session = new Session();

		session.addPlayer(new Player("BOT1", PlayerType.AI_EASY));
		session.addPlayer(new Player("BOT2", PlayerType.AI_EASY));
		session.addPlayer(new Player("BOT3", PlayerType.AI_EASY));
		session.addPlayer(new Player("BOT4", PlayerType.AI_EASY));


		Game game = session.startGame(new GMClassic());

		while (game.getGameState().isStateRunning()) {
			Debug.printMessage(session.toString());
			game.makeMove();

		}
	}
}
