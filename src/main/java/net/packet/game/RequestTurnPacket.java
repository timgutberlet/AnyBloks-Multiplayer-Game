package net.packet.game;

import net.packet.abstr.Packet;

/**
 * packet to tell a player that a turn needs to be made
 *
 * @author tgeilen
 * @Date 10.05.22
 */
public class RequestTurnPacket extends Packet {

	final String username;

	/**
	 * constructor for packet
	 * @param username
	 */
	public RequestTurnPacket(String username){
		this.username = username;
	}

}
