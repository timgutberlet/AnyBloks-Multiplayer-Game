package net.server;

import game.model.Debug;
import game.model.GameSession;
import game.model.GameState;
import game.model.gamemodes.GameMode;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.game.GameStartPacket;
import net.packet.game.GameUpdatePacket;
import net.packet.game.GameWinPacket;
import net.packet.game.RequestTurnPacket;
import net.transmission.EndpointServer;

/**
 * Provides functions to ServerEndpoint.
 *
 * @author tgeilen
 */
public class OutboundServerHandler {

	private EndpointServer server;
	private GameSession gameSession;


	/**
	 * Constructor
	 */
	public OutboundServerHandler(EndpointServer server, GameSession gameSession) {

		this.server = server;
		this.gameSession = gameSession;
		this.gameSession.setOutboundServerHandler(this);

		Debug.printMessage(this,"OutboundServerHandler created");

	}

	/**
	 * send a REQUEST_TURN_PACKET to a client
	 *
	 * @param username
	 * @author tgeilen
	 */
	public void requestTurn(String username) {
		Debug.printMessage(this,username + " will be requested to make a turn");
		GameState gameState = this.gameSession.getGame().getGameState();
		Debug.printMessage(this,gameState.toString());
		RequestTurnPacket requestTurnPacket = new RequestTurnPacket(username, gameState);
		WrappedPacket wrappedPacket = new WrappedPacket(PacketType.REQUEST_TURN_PACKET,
				requestTurnPacket);

		this.server.sendMessage(wrappedPacket, username);
		Debug.printMessage(this,"Requested turn from "+username);
	}


	/**
	 * broadcast GAME_START_PACKET to all clients
	 *
	 * @param gameMode
	 * @tgeilen
	 */
	public void broadcastGameStart(GameMode gameMode) {
		GameStartPacket gameStartPacket = new GameStartPacket(gameMode);
		WrappedPacket wrappedPacket = new WrappedPacket(PacketType.GAME_START_PACKET, gameStartPacket);

		this.server.broadcastMessage(wrappedPacket);
	}

	/**
	 * broadcast GAME_UPDATE_PACKET to all clients
	 *
	 * @author tgeilen
	 */
	public void broadcastGameUpdate() {
		GameState gameState = this.gameSession.getGame().getGameState();

		GameUpdatePacket gameUpdatePacket = new GameUpdatePacket(gameState);
		WrappedPacket wrappedPacket = new WrappedPacket(PacketType.GAME_UPDATE_PACKET,
				gameUpdatePacket);

		this.server.broadcastMessage(wrappedPacket);
	}

	/**
	 * broadcast GAME_WIN_PACKET to all clients
	 *
	 * @param usernameWinner
	 * @author tgeilen
	 */
	public void broadcastGameWin(String usernameWinner) {

		GameWinPacket gameWinPacket = new GameWinPacket(usernameWinner);
		WrappedPacket wrappedPacket = new WrappedPacket(PacketType.GAME_WIN_PACKET, gameWinPacket);

		this.server.broadcastMessage(wrappedPacket);
	}

	public void broadcastChatMessage(WrappedPacket wrappedPacket) {
		this.server.broadcastMessage(wrappedPacket);
	}


}
