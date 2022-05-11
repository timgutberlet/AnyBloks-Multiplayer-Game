package net.server;

import game.model.GameState;
import game.model.Turn;
import game.model.gamemodes.GameMode;
import game.model.player.Player;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.chat.ChatMessagePacket;
import net.packet.game.GameStartPacket;
import net.packet.game.GameUpdatePacket;
import net.packet.game.GameWinPacket;
import net.packet.game.RequestTurnPacket;
import net.packet.game.TurnPacket;
import net.transmission.EndpointClient;

/**
 * provides functions to EndpointClient
 *
 * @author tgeilen
 * @Date 10.05.22
 */
public class ClientHandler {

	private EndpointClient client;
	private Player player;

	public ClientHandler(EndpointClient client) {
		this.client = client;
		this.player = this.client.getPlayer();
	}

	/**
	 * functions that starts a game on the client side
	 *
	 * @param wrappedPacket
	 * @author tgeilen
	 */
	public void startGame(WrappedPacket wrappedPacket) {
		GameStartPacket gameStartPacket = (GameStartPacket) wrappedPacket.getPacket();
		GameMode gamemode = gameStartPacket.getGameMode();
		this.client.getGameSession().startGame(gamemode);
	}

	/**
	 * function that calls the client to make a turn and sends it to serve
	 *
	 * @param wrappedPacket
	 * @author tgeilen
	 */
	public void makeTurn(WrappedPacket wrappedPacket) {
		RequestTurnPacket requestTurnPacket = (RequestTurnPacket) wrappedPacket.getPacket();
		GameState gameState = requestTurnPacket.getGameState();

		if (this.player.getName().equals(requestTurnPacket.getName())) {

			Turn turn = this.player.makeTurn(gameState);

			TurnPacket turnPacket = new TurnPacket(player.getName(), turn);
			WrappedPacket wrPacket = new WrappedPacket(PacketType.TURN_PACKET, turnPacket);
			this.client.sendToServer(wrPacket);
			;
		}
	}

	/**
	 * updates the gameState for the local client
	 *
	 * @param wrappedPacket
	 */
	public void updateGame(WrappedPacket wrappedPacket) {
		GameUpdatePacket gameUpdatePacket = (GameUpdatePacket) wrappedPacket.getPacket();
		GameState gameState = gameUpdatePacket.getGameState();

		this.client.getGameSession().updateGame(gameState);
	}

	/**
	 * end a local game
	 *
	 * @param wrappedPacket
	 * @author tgeilen
	 */
	public void endGame(WrappedPacket wrappedPacket) {
		GameWinPacket gameWinPacket = (GameWinPacket) wrappedPacket.getPacket();
		String winner = gameWinPacket.getUsername();
		this.client.getGameSession().endGame(winner);
	}

	/**
	 * save a ChatMessage in the chat
	 */
	public void saveChatMessage(WrappedPacket wrappedPacket) {
		ChatMessagePacket chatMessagePacket = (ChatMessagePacket) wrappedPacket.getPacket();
		this.client.getGameSession().addChatMessage(chatMessagePacket.getChatMessage());
	}

}
