package net.packet.game;

import game.model.player.Player;
import net.packet.abstr.Packet;

/**
 * @author tgeilen
 * @Date 10.05.22
 */
public class GameWinPacket extends Packet {

	private final String username;

	public GameWinPacket() {
		this.username = null;
	}

	public GameWinPacket(String username) {
		this.username = username;
	}

	public String getUsername() {
		return username;
	}
}
