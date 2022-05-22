package net.packet.game;

import net.packet.abstr.Packet;

/**
 * This packet signals to all players, that they should return to the lobby.
 * It is sent by the
 * server to all clients but the host therefore the game stops. -> return to lobby
 *
 * @author tbuscher
 */
public class HostQuitPacket extends Packet {

}
