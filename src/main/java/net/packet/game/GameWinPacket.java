package net.packet.game;

import game.model.player.Player;
import net.packet.abstr.Packet;

/**
 * @author tgeilen
 * @Date 10.05.22
 */
public class GameWinPacket extends Packet {

	private final String username;

	public GameWinPacket(Player player){
		this.username = player.getName();
	}

}
