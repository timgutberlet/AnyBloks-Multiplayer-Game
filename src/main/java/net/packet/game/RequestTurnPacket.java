package net.packet.game;

import game.model.GameState;
import net.packet.abstr.Packet;

/**
 * packet to tell a player that a turn needs to be made
 *
 * @author tgeilen
 * @Date 10.05.22
 */
public class RequestTurnPacket extends Packet {

	final String username;
	final GameState gameState;

	/**
	 * empty constructor for jackson
	 *
	 * @author tgeilen
	 */
	public RequestTurnPacket() {
		this.username = null;
		this.gameState = null;
	}

	/**
	 * contructor for packet
	 *
	 * @param username
	 * @param gameState
	 * @author tgeilen
	 */
	public RequestTurnPacket(String username, GameState gameState) {
		this.username = username;
		this.gameState = gameState;
	}

	public GameState getGameState() {
		return gameState;
	}

	public String getName() {
		return username;
	}
}
