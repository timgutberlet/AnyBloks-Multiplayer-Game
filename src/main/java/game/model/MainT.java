
package game.model;

import game.model.gamemodes.GMClassic;
import game.model.player.Player;
import game.model.player.PlayerType;


public class MainT {

	/**
	 * a simple test class that shows how the domain works at the moment
	 *
	 * @param args
	 * @author tgeilen
	 */
	public static void main(String[] args) {

		GameSession gameSession = new GameSession();

		gameSession.addPlayer(new Player("BOT1", PlayerType.AI_EASY));
		gameSession.addPlayer(new Player("BOT2", PlayerType.AI_EASY));
		gameSession.addPlayer(new Player("BOT3", PlayerType.AI_EASY));
		gameSession.addPlayer(new Player("BOT4", PlayerType.AI_EASY));

		Game game = gameSession.startGame(new GMClassic());

		while (game.getGameState().isStateRunning()) {
			Debug.printMessage(gameSession.toString());
			//game.makeMove();
		}

		gameSession.stopSession();
	}
}
