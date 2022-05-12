package net.transmission;


import game.model.Debug;
import game.model.GameSession;
import game.model.player.Player;
import game.model.player.PlayerType;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.LoginResponsePacket;
import net.server.InboundServerHandler;
import net.server.OutboundServerHandler;


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
  private static GameSession gameSession;
  private static HashMap<String, Session> username2Session = new HashMap<String, Session>();
  private InboundServerHandler inboundServerHandler;
  private OutboundServerHandler outboundServerHandler;

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

    this.gameSession = new GameSession();
    this.inboundServerHandler = new InboundServerHandler(this, gameSession);
    this.outboundServerHandler = new OutboundServerHandler(this, gameSession);

  }

  /**
   * Remove a session.
   *
   * @param ses from Set
   */
  @OnClose
  public void onClose(final Session ses) {
    sessions.remove(ses);
  }

  /**
   * Broadcast a message to all clients that have a session.
   *
   * @param packet message to be sent out
   * @throws IOException     is thrown
   * @throws EncodeException is thrown
   */


  /**
   * Called whenever a message is received.
   *
   * @param packet received
   * @param client the pocket came from
   * @throws IOException
   * @throws EncodeException
   */
  @OnMessage
//  public void onMessage(final WrappedPacket packet) throws IOException, EncodeException {
  public void onMessage(final WrappedPacket packet, final Session client)
      throws IOException, EncodeException {
    System.out.println(packet);
    System.out.println("___FIND___");
    System.out.println(packet.getPacketType());
    PacketType type = packet.getPacketType();
    Debug.printMessage(this, type.name());
    System.out.println("Endpoint recieved message");

    switch (type) {
      case INIT_SESSION_PACKET: {
        if (this.gameSession == null) {
          this.gameSession = new GameSession();
        }
        if (this.inboundServerHandler == null) {
          this.inboundServerHandler = new InboundServerHandler(this, gameSession);
        }

      }
      case INIT_GAME_PACKET: {
        this.inboundServerHandler.startGame(packet);
        break;
      }
      case CREATE_ACCOUNT_REQUEST_PACKET:
        Debug.printMessage(this, "Received Create Account REQUEST PACKET ");
        break;

      case LOGIN_REQUEST_PACKET:
        Debug.printMessage(this, " LOGIN_REQUEST_PACKET recieved");
        String[] response = this.inboundServerHandler.verifyLogin(packet, client);
        if (response[0].equals("true")) {
          this.gameSession.addPlayer(new Player(response[1], PlayerType.REMOTE_PLAYER));
          //allSessions.put(response[1], client);
        } else {
          client.getBasicRemote().sendObject(
              new LoginResponsePacket("Credentials could not be verified"));
        }
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

  /**
   * function to easyily send a packet to a client based on username.
   *
   * @param wrappedPacket
   * @param username
   */
  public void sendMessage(WrappedPacket wrappedPacket, String username) {
    try {
      Session client = this.username2Session.get(username);
      client.getBasicRemote().sendObject(wrappedPacket);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (EncodeException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * function to easily send a packet to client based on session.
   *
   * @param wrappedPacket
   * @param client
   */
  public void sendMessage(WrappedPacket wrappedPacket, Session client) {
    try {
      client.getBasicRemote().sendObject(wrappedPacket);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (EncodeException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void broadcastMessage(WrappedPacket wrappedPacket) {
    Session client;
    for (String username : this.getUsername2Session().keySet()) {
      client = this.getUsername2Session().get(username);
      try {
        client.getBasicRemote().sendObject(wrappedPacket);
      } catch (IOException e) {
        e.printStackTrace();
      } catch (EncodeException e) {
        e.printStackTrace();
      }
    }
  }

  public void addUsernameSession(String username, Session client) {
    this.username2Session.put(username, client);
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
}
