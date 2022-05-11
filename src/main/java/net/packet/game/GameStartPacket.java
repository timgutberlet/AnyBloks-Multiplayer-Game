package net.packet.game;

import game.model.GameState;
import game.model.gamemodes.GameMode;
import net.packet.abstr.Packet;

/**
 * @author tgeilen
 * @Date 10.05.22
 */
public class GameStartPacket extends Packet {

	private final GameMode gameMode;

	public GameStartPacket() {
		this.gameMode = null;
	}

	public GameStartPacket(GameMode gameMode) {
		this.gameMode = gameMode;

	}

	public GameMode getGameMode() {
		return gameMode;
	}
}
