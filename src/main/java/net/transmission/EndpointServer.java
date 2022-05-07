package net.transmission;


import game.model.Debug;
import game.model.GameSession;
import game.model.player.Player;
import game.model.player.PlayerType;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import net.packet.LoginResponsePacket;
import net.packet.PacketType;
import net.packet.WrappedPacket;
import net.server.ServerHandler;


/**
 * Endpoint for the servers websocket.
 *
 * @author tbuscher
 */

@ServerEndpoint(value = "/packet", encoders = {PacketEncoder.class}, decoders = {
    PacketDecoder.class})
public class EndpointServer {
  private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());

  // Creating HashSet for all Sessions
//  private static final HashMap<String, Session> allSessions = (HashMap<String, Session>) Collections.synchronizedMap(
//      new HashMap<String, Session>());

  private ServerHandler serverHandler;
  private GameSession gameSession;

  /**
   * Add a new session to the active ones.
   *
   * @param ses Session to be added
   */
  @OnOpen
  public void onOpen(final Session ses) {

  }

  /**
   * Remove a session.
   *
   * @param ses from Set
   */
  @OnClose
  public void onClose(final Session ses) {
  }

  /**
   * Broadcast a message to all clients that have a session.
   *
   * @param packet message to be sent out
   * @throws IOException     is thrown
   * @throws EncodeException is thrown
   */
  @OnMessage
//  public void onMessage(final WrappedPacket packet) throws IOException, EncodeException {
  public void onMessage(final WrappedPacket packet, final Session client)
      throws IOException, EncodeException {
    System.out.println(packet);
    System.out.println("___FIND___");
    System.out.println(packet.getPacketType());
    PacketType type = packet.getPacketType();
    switch (type) {
      case INIT_PACKET: {
        gameSession = new GameSession();
        serverHandler = new ServerHandler(gameSession);
      }
      case CREATE_ACCOUNT_REQUEST_PACKET:
        Debug.printMessage("Received Create Account REQUEST PACKET ");
        break;
      case LOGIN_REQUEST_PACKET:
        String[] response = this.serverHandler.verifyLogin(packet);
        if (response[0].equals("true")) {
          this.
              gameSession.addPlayer(new Player(response[1], PlayerType.REMOTE_PLAYER));
          //allSessions.put(response[1], client);
        } else {
          client.getBasicRemote().sendObject(
              new LoginResponsePacket("Credentials could not be verified"));
        }
        break;
      case CHAT_MESSAGE_PACKET:
//        for (Map.Entry<String, Session> clientEntry : allSessions.entrySet()) {
//          clientEntry.getValue().getBasicRemote().sendObject(packet);
//        }
        for (final Session session : sessions) {
          session.getBasicRemote().sendObject(packet);
        }
        serverHandler.saveChatMessage(packet);
        break;
      case PLAYER_ORDER_PACKET:
//        for (Map.Entry<String, Session> clientEntry : allSessions.entrySet()) {
//          clientEntry.getValue().getBasicRemote().sendObject(packet);
//        }
        break;
      case GAME_UPDATE_PACKET:
        //TODO: this packet should never be received by server, only by client! Remove it
//        for (Map.Entry<String, Session> clientEntry : allSessions.entrySet()) {
//          clientEntry.getValue().getBasicRemote().sendObject(packet);
//        }
        for (final Session session : sessions) {
          session.getBasicRemote().sendObject(packet);
        }
      default:
        System.out.println("Received a packet of type: " + type);

    }
  }


}
