package net.packet;

/**
 * All possible types of packets for communication between server & clients.
 *
 * @author tbuscher
 */
public enum PacketType {
  CHAT_MESSAGE_PACKET, CREATE_ACCOUNT_REQUEST_PACKET, CREATE_ACCOUNT_RESPONSE_PACKET,
  LOGIN_REQUEST_PACKET, LOGIN_RESPONSE_PACKET, INIT_PACKET, PLAYER_ORDER_PACKET, UPDATE_ACCOUNT_REQUEST_PACKET,

}
