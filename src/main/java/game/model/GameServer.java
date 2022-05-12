package game.model;

import game.model.player.Player;
import net.server.InboundServerHandler;

/**
 * this class runs on the server and controls the game
 *
 * @author tgeilen
 * @Date 10.05.22
 */
public class GameServer {

	private GameSession gameSession;
	private GameState gameState;
	private InboundServerHandler inboundServerHandler;


	public GameServer() {

	}

	public GameServer(GameSession gameSession, InboundServerHandler inboundServerHandler) {
		this.gameSession = gameSession;
		this.inboundServerHandler = inboundServerHandler;
	}

	public void updateGameState(GameState gameState) {
		this.gameState = gameState;
	}

	public Player getCurrentPlayer() {
		return this.gameState.getPlayerCurrent();
	}

	public boolean playTurn(Turn turn) {
		return this.gameState.playTurn(turn);
	}




	public void playGame() {

	}

}