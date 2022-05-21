package net.transmission;


import game.model.Debug;
import game.model.GameSession;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.server.DbServer;
import net.server.InboundServerHandler;
import net.server.OutboundServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Endpoint for the servers websocket.
 *
 * @author tbuscher
 */

@ServerEndpoint(value = "/packet", encoders = {PacketEncoder.class}, decoders = {
    PacketDecoder.class})
public class EndpointServer {

  private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
  private static final Logger LOG = LoggerFactory.getLogger(EndpointServer.class);
  private static final HashMap<String, Session> username2Session = new HashMap<String, Session>();
  // Creating HashSet for all Sessions
//  private static final HashMap<String, Session> allSessions = (HashMap<String, Session>) Collections.synchronizedMap(
//      new HashMap<String, Session>());
  private static GameSession gameSession;
  private InboundServerHandler inboundServerHandler;
  private OutboundServerHandler outboundServerHandler;
  private DbServer dbServer = null;

  public static GameSession getGameSession() {
    return gameSession;
  }

  /**
   * Add a new session to the active ones.
   *
   * @param ses Session to be added
   */
  @OnOpen
  public void onOpen(final Session ses) {
    sessions.add(ses);
    ses.setMaxBinaryMessageBufferSize(1024 * 1024 * 20);
    ses.setMaxTextMessageBufferSize(1024 * 1024 * 20);

    gameSession = new GameSession();
    this.inboundServerHandler = new InboundServerHandler(this, gameSession);
    this.outboundServerHandler = new OutboundServerHandler(this, gameSession);
    try {
      this.dbServer = DbServer.getInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Remove a session.
   *
   * @param ses from Set
   */
  @OnClose
  public void onClose(final Session ses) {
    System.out.println("THIS SESSION WAS CLOSED BY THE SERVER TOBI");
    sessions.remove(ses);

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

    PacketType type = packet.getPacketType();

    // Todo: remove
    Debug.printMessage(this, type.name());
    System.out.println("Endpoint recieved message");
    LOG.info("A packet has been sent here by a client, it is of the type: {} send by {}",
        packet.getPacketType().toString(), client.getId());

    String username = packet.getUsername();
    String authToken = packet.getToken();
    //Validate the token sent with the packet, only if it is valid, the request is handled
    System.out.println(username);
    System.out.println(authToken);
    System.out.println(
        dbServer.testAuthToken(username, authToken) + " In endpoint server verfiy login");
    if (dbServer.testAuthToken(username, authToken)) {
      System.out.println("Passed if testAuth");
      switch (type) {
        case INIT_SESSION_PACKET: {

          if (gameSession == null) {
            gameSession = new GameSession();
          }
          if (this.inboundServerHandler == null) {
            this.inboundServerHandler = new InboundServerHandler(this, gameSession);
          }

        }
        break;
        case INIT_GAME_PACKET: {
          this.inboundServerHandler.startGame(packet);
          break;
        }
        case CREATE_ACCOUNT_REQUEST_PACKET:
          Debug.printMessage(this, "Received Create Account REQUEST PACKET ");
          String answer = this.inboundServerHandler.createAccount(packet);
          this.outboundServerHandler.accountRequestResponse(client, answer);
          break;

        case LOGIN_REQUEST_PACKET:
          Debug.printMessage(this, " LOGIN_REQUEST_PACKET recieved");
          //TODO use this function in the final product
          this.inboundServerHandler.verifyLogin(packet, client);
          break;

        case CHAT_MESSAGE_PACKET:
          //immediately broadcast to all clients
          outboundServerHandler.broadcastChatMessage(packet);
          break;
        case PLAYER_ORDER_PACKET:
          Debug.printMessage(this, "PLAYER_ORDER_PACKET recieved");
//        for (Map.Entry<String, Session> clientEntry : allSessions.entrySet()) {
//          clientEntry.getValue().getBasicRemote().sendObject(packet);
//        }
          break;
        case TURN_PACKET:
          inboundServerHandler.recieveTurn(client, packet);
          break;

        default:
          System.out.println("Received a packet of type: " + type);

      }
    }
  }

  /**
   * function to easyily send a packet to a client based on username
   *
   * @param wrappedPacket
   * @param username
   */
  public void sendMessage(WrappedPacket wrappedPacket, String username) {

    Debug.printMessage(this, this.getUsername2Session().keySet().toString());

    try {
      Session client = username2Session.get(username);
      this.sendMessage(wrappedPacket, client);
    } catch (Exception e) {
      Debug.printMessage(this, "Message could not be sent \nReplacing user with AI");
      gameSession.changePlayer2AI(username);
      //e.printStackTrace();
    }
  }

  /**
   * function to easily send a packet to client based on session
   *
   * @param wrappedPacket
   * @param client
   */
  public void sendMessage(WrappedPacket wrappedPacket, Session client) {

    Debug.printMessage(this, this.getUsername2Session().keySet().toString());

    try {
      client.getBasicRemote().sendObject(wrappedPacket);
    } catch (Exception e) {
      Debug.printMessage(this, "Message could not be sent \nReplacing user with AI");
      for (String username : this.getUsername2Session().keySet()) {
        if (client.equals(this.getUsername2Session().get(username))) {
          gameSession.changePlayer2AI(username);
        }
      }

      //e.printStackTrace();
    }
  }

  public void broadcastMessage(WrappedPacket wrappedPacket) {
    Session client;

    for (String username : this.getUsername2Session().keySet()) {
      client = this.getUsername2Session().get(username);
      try {
        client.getBasicRemote().sendObject(wrappedPacket);
      } catch (Exception e) {
        Debug.printMessage(this, "Message could not be sent \nReplacing user with AI");
        gameSession.changePlayer2AI(username);
        //e.printStackTrace();
      }
    }
  }

  public void addUsernameSession(String username, Session client) {
    username2Session.put(username, client);
  }

  public InboundServerHandler getInboundServerHandler() {
    return inboundServerHandler;
  }

  public OutboundServerHandler getOutboundServerHandler() {
    return outboundServerHandler;
  }

  public HashMap<String, Session> getUsername2Session() {
    return username2Session;
  }

  @OnError
  public void onError(Session ses, Throwable t) {
    System.out.println("HI FROM CRASH");
    t.printStackTrace();
  }
}
