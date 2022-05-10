package net.packet.abstr;

/**
 * All possible types of packets for communication between server & clients.
 *
 * @author tbuscher
 * @authot tgeilen
 */
public enum PacketType {
  CHAT_MESSAGE_PACKET, CREATE_ACCOUNT_REQUEST_PACKET, CREATE_ACCOUNT_RESPONSE_PACKET,
  LOGIN_REQUEST_PACKET, LOGIN_RESPONSE_PACKET, INIT_SESSION_PACKET, PLAYER_ORDER_PACKET,
  UPDATE_ACCOUNT_REQUEST_PACKET, GAME_UPDATE_PACKET, REQUEST_TURN_PACKET, TURN_PACKET,
  GAME_START_PACKET, GAME_WIN_PACKET, ILLEGAL_TURN_PACKET, INIT_GAME_PACKET

}