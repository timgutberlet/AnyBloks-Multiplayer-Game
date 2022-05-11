package net.packet.game;

import game.model.GameState;
import net.packet.abstr.Packet;

/**
 * Packet to send GameState and allow Players to determine they have to make a move
 *
 * @author tbuscher
 */
public class GameUpdatePacket extends Packet {

	private GameState gameState;


	/**
	 * Constructor
	 *
	 * @param gameState gs
	 */
	public GameUpdatePacket(GameState gameState) {
		this.gameState = gameState;


	}

	/**
	 * Constructor
	 */
	public GameUpdatePacket() {

	}

	/**
	 * Getter
	 */


	/**
	 * Getter
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * Setter
	 */
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}

}
