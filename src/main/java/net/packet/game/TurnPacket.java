package net.packet.game;

import game.model.Turn;
import net.packet.abstr.Packet;

/**
 * packet that is sent from a player to server containing the information of the made turn
 *
 * @author tgeilen
 * @Date 10.05.22
 */
public class TurnPacket extends Packet {

	final String username;
	final Turn turn;

	public TurnPacket (String username, Turn turn){
		this.username = username;
		this.turn = turn;
	}

}
