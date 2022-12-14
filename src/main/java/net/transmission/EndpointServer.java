package net.transmission;


import game.model.Debug;
import game.model.GameSession;
import game.model.chat.Chat;
import game.model.player.Player;
import game.model.player.PlayerType;
import game.scores.GameScoreBoard;
import game.scores.GameSessionScoreBoard;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.concurrent.TimeUnit;
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
  private static GameSession gameSession;
  private InboundServerHandler inboundServerHandler;
  private OutboundServerHandler outboundServerHandler;
  private DbServer dbServer = null;

  public static GameSession getGameSession() {
    return gameSession;
  }

  public static Set<Session> getSessions() {
    return sessions;
  }

  /**
   * This method resets any saved variables of the endpoint. This way it is ready to restart.
   */
  public void resetEndpointServer() {
    username2Session.clear();
    sessions.clear();
    gameSession = new GameSession();

    //Manually resetting the gameSession,
    //to avoid getting old date due to garbage collector being too slow
    gameSession.getPlayerList().clear();
    GameSession.currentGameIds.clear();
    gameSession.getScoreboard().clear();
    gameSession.getGameSessionScoreboard().clear();
    gameSession.setChat(new Chat());
    //gameSession.setHostPlayer(null);
    gameSession.setGameSessionScoreBoard(new GameSessionScoreBoard());
    gameSession.setGameList(new LinkedList<>());
    gameSession.setDefaultAi(PlayerType.AI_EASY);
    //gameSession.setLocalPlayer(null);
    gameSession.setAiPlayers(null);
    gameSession.setGameScoreBoard(new GameScoreBoard());

    inboundServerHandler = new InboundServerHandler();
    outboundServerHandler = new OutboundServerHandler(this, gameSession);
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
    if (gameSession == null) {
      gameSession = new GameSession();
    }

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
    Debug.printMessage("THIS SESSION WAS CLOSED BY THE SERVER TOBI");
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
  public void onMessage(final WrappedPacket packet, final Session client)
      throws IOException, EncodeException {

    Debug.printMessage("Server gamesession: " + gameSession);

    PacketType type = packet.getPacketType();

    Debug.printMessage(this, type.name());
    Debug.printMessage(this, "Endpoint received message");
    //LOG.info("A packet has been sent here by a client, it is of the type: {} send by {}",
    //    packet.getPacketType().toString(), client.getId());

    String username = packet.getUsername();
    String authToken = packet.getToken();
    //Validate the token sent with the packet, only if it is valid, the request is handled
    Debug.printMessage(username);
    Debug.printMessage(authToken);
    Debug.printMessage(
        dbServer.testAuthToken(username, authToken) + " In endpoint server verfiy login");
    if (dbServer.testAuthToken(username, authToken)) {
      Debug.printMessage("Passed if testAuth");
      switch (type) {
        case INIT_SESSION_PACKET: {

          if (gameSession == null) {
            gameSession = new GameSession();
            gameSession.getPlayerList().clear();
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
          Debug.printMessage(this, " LOGIN_REQUEST_PACKET received");

          this.inboundServerHandler.verifyLogin(packet, client);
          break;

        case CHAT_MESSAGE_PACKET:
          //immediately broadcast to all clients
          outboundServerHandler.broadcastChatMessage(packet);
          break;
        case PLAYER_ORDER_PACKET:
          Debug.printMessage(this, "PLAYER_ORDER_PACKET received");
          break;
        case TURN_PACKET:
          inboundServerHandler.receiveTurn(client, packet);
          break;
        case HOST_QUIT_PACKET:
          outboundServerHandler.broadcastHostQuit();
          try {
            TimeUnit.MILLISECONDS.sleep(5000);
          } catch (InterruptedException e) {
            e.printStackTrace();
          }
          inboundServerHandler.getServer().resetEndpointServer();
          break;
        case PLAYER_QUIT_PACKET:
          inboundServerHandler.disconnectClient(client, packet);
          break;

        case PLAYER_KICK_PACKET:
          inboundServerHandler.kickClient(client, packet);
          break;

        default:
          Debug.printMessage("Received a packet of type: " + type);
          break;
      }
    }
  }

  /**
   * function to easyily send a packet to a client based on username.
   *
   * @param wrappedPacket wrappedPacket to be sent
   * @param username      username to be sent to
   */
  public void sendMessage(WrappedPacket wrappedPacket, String username) {

    Debug.printMessage(this, this.getUsername2Session().keySet().toString());

    try {
      Session client = username2Session.get(username);
      this.sendMessage(wrappedPacket, client);
    } catch (Exception e) {
      Debug.printMessage(this, "Message could not be sent \nReplacing user with AI");
      gameSession.changePlayer2Ai(username);
      //e.printStackTrace();
    }
  }

  /**
   * function to easily send a packet to client based on session.
   *
   * @param wrappedPacket wrappedPacket that is supposed to be sent
   * @param client        Session where a packet is supposed to be sent
   */
  public void sendMessage(WrappedPacket wrappedPacket, Session client) {

    Debug.printMessage(this, this.getUsername2Session().keySet().toString());

    try {
      client.getBasicRemote().sendObject(wrappedPacket);
    } catch (Exception e) {
      Debug.printMessage(this, "Message could not be sent \nReplacing user with AI");
      for (String username : this.getUsername2Session().keySet()) {
        if (client.equals(this.getUsername2Session().get(username))) {
          //gameSession.changePlayer2Ai(username);
          Debug.printMessage("");
        }
      }

      //e.printStackTrace();
    }
  }

  /**
   * broadcasts a message to all players.
   *
   * @param wrappedPacket message packet.
   */
  public void broadcastMessage(WrappedPacket wrappedPacket) {
    Session client;

    for (String username : this.getUsername2Session().keySet()) {
      client = this.getUsername2Session().get(username);
      try {
        client.getBasicRemote().sendObject(wrappedPacket);
      } catch (Exception e) {
        Debug.printMessage(this, "Message could not be sent \nReplacing user with AI");
        //gameSession.changePlayer2Ai(username);
        e.printStackTrace();
      }
    }
  }

  /**
   * adds username and a client session to the hash map username2session.
   *
   * @param username of connected user
   * @param client   Session to be added
   */
  public void addUsernameSession(String username, Session client) {
    username2Session.put(username, client);
  }

  /**
   * Remove a username from Username2Session and cause an Error on the connection.
   */
  public void removePlayer(String username) {
    //This causes an Error on the connection within 1 millisecond
    username2Session.get(username).setMaxIdleTimeout(1);
    try {
      TimeUnit.MILLISECONDS.sleep(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    //After the session has crashed, the user will be replaced by an AI automatically
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

  /**
   * Remove player from gameSession.
   *
   * @param username String to be removed
   */
  public void dropUser(String username) {
    Debug.printMessage("The user: " + username + " has been kicked");
    Player playertoKick = null;
    for (Player p : gameSession.getPlayerList()) {
      if (p.getUsername().equals(username)) {
        playertoKick = p;
      }
    }

    gameSession.getPlayerList().remove(playertoKick);

    Session sesToKick = username2Session.get(username);
    username2Session.remove(username);

    try {
      sesToKick.setMaxIdleTimeout(1);
    } catch (Exception e) {
      Debug.printMessage("A users session has been terminated");
      Debug.printMessage("A users session has been terminated");
      e.printStackTrace();
    }

  }

  /**
   * handles an occurring error.
   *
   * @param ses session in which the error occurred
   * @param t   error inheriting from Throwable
   */
  @OnError
  public void onError(Session ses, Throwable t) {
    Debug.printMessage("There has been a problem in the ServerEndpoint");

    t.printStackTrace();
  }
}
