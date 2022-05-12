package net.server;

import game.model.Debug;
import game.model.GameSession;
import game.model.GameState;
import game.model.Turn;
import game.model.player.Player;
import javax.websocket.Session;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.LoginRequestPacket;
import net.packet.game.IllegalTurnPacket;
import net.packet.game.InitGamePacket;
import net.packet.game.RequestTurnPacket;
import net.packet.game.TurnPacket;
import net.transmission.EndpointServer;

/**
 * Provides functions to ServerEndpoint
 *
 * @author tbuscher
 */
public class InboundServerHandler {

	private EndpointServer server;
	private static GameSession gameSession;

	/**
	 * constructor for jackson
	 */
	public InboundServerHandler(){}

	/**
	 * Constructor
	 */
	public InboundServerHandler(EndpointServer server, GameSession gameSession) {

		this.server = server;
		if(this.gameSession == null) {
			this.gameSession = gameSession;
			this.gameSession.setInboundServerHandler(this);
		}

		Debug.printMessage(this,"InboundServerHandler created");


	}


	/**
	 * Verifies whether Login is correct according to Database
	 *
	 * @param wrappedPacket wrapped LoginRequestPacket
	 * @return String[] [0] "true"/"false" [1] username
	 */
	public String[] verifyLogin(WrappedPacket wrappedPacket, Session session) {

		Debug.printMessage(this, "LOGIN_REQUEST_PACKET recieved in Handler");
		LoginRequestPacket loginPacket = (LoginRequestPacket) wrappedPacket.getPacket();
		String username = loginPacket.getUsername();
		String passwordHash = loginPacket.getPasswordHash();

		Debug.printMessage(this, username + " " + passwordHash);

		//TODO add logic with database here
		String[] toReturn = {"true", username};

		this.server.addUsernameSession(username, session);
		Debug.printMessage(this,
				"New Length of KeySet: " + this.server.getUsername2Session().keySet().size());
		return toReturn;
	}

	public void startGame(WrappedPacket wrappedPacket) {

		InitGamePacket initGamePacket = (InitGamePacket) wrappedPacket.getPacket();

		this.server.getOutboundServerHandler().broadcastGameStart(initGamePacket.getGameMode());

		this.gameSession.startGameServer(initGamePacket.getGameMode());



	}

	/**
	 * requests a player to make a turn
	 *
	 * @author tgeilen
	 */
	public void requestTurn(Player player) {
		GameState gameState = this.gameSession.getGame().getGameState();
		RequestTurnPacket requestTurnPacket = new RequestTurnPacket(player.getUsername(), gameState);
		WrappedPacket wrappedPacket = new WrappedPacket(PacketType.REQUEST_TURN_PACKET,
				requestTurnPacket);
		this.server.sendMessage(wrappedPacket, player.getUsername());
		Debug.printMessage(this,"Requested turn from " + player.getUsername());

	}

	/**
	 * recieve a turn from a remote player and forward it to game logic
	 *
	 * @param packet
	 * @author tgeilen
	 */
	public void recieveTurn(Session client, WrappedPacket packet) {
		Debug.printMessage(this,"Recieved turn");
		TurnPacket turnPacket = (TurnPacket) packet.getPacket();
		Turn turn = turnPacket.getTurn();
		if (this.gameSession.getGame().checkTurn(turn)) {
			this.gameSession.getGame().makeMove(turn);
		} else {
			IllegalTurnPacket illegalTurnPacket = new IllegalTurnPacket();
			WrappedPacket wrappedPacket = new WrappedPacket(PacketType.ILLEGAL_TURN_PACKET,
					illegalTurnPacket);
			this.server.sendMessage(wrappedPacket, client);
		}

	}

	public EndpointServer getServer() {
		return server;
	}
}
