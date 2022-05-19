package net.transmission;

//import game.controller.JoinGameLobbyController;
//import game.controller.LocalGameLobbyController;
import game.controller.HostLobbyUiController;
import game.controller.JoinLobbyUiController;
import game.controller.LocalLobbyUiController;
import game.model.Debug;
import game.model.GameSession;
import game.model.player.Player;
import java.io.IOException;
import javax.websocket.ClientEndpoint;
import javax.websocket.EncodeException;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.server.ClientHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Endpoint for clients.
 *
 * @author tbuscher
 */

@ClientEndpoint(encoders = {PacketEncoder.class}, decoders = {PacketDecoder.class})

public class EndpointClient {

	private Session server;
	private GameSession gameSession;
	private ClientHandler clientHandler;
	private Player player;

	public EndpointClient(Player player) {
		super();
		this.player = player;
		this.gameSession = new GameSession();
		this.gameSession.setLocalPlayer(player);
		this.player.setGameSession(this.gameSession);
		this.clientHandler = new ClientHandler(this);
	}

	public EndpointClient(LocalLobbyUiController localLobbyUiController, Player player) {
		super();
		this.player = player;
		this.gameSession = new GameSession(player);
		Debug.printMessage(this, "GameSession EndpointClient" + this.gameSession);
		this.gameSession.setLocalPlayer(player);
		this.player.setGameSession(this.gameSession);
		this.clientHandler = new ClientHandler(this);
		Debug.printMessage(this, "EndpointClient created from GUI 1");
		this.clientHandler.initLocalGame(player);

		Debug.printMessage(this, "EndpointClient created from GUI 2");
	}
	public EndpointClient(HostLobbyUiController localLobbyUiController, Player player) {
		super();
		this.player = player;
		this.gameSession = new GameSession(player);
		Debug.printMessage(this, "GameSession EndpointClient" + this.gameSession);
		this.gameSession.setLocalPlayer(player);
		this.player.setGameSession(this.gameSession);
		this.clientHandler = new ClientHandler(this);
		Debug.printMessage(this, "EndpointClient created from GUI 1");
		this.clientHandler.initLocalGame(player);

		Debug.printMessage(this, "EndpointClient created from GUI 2");
	}

	public EndpointClient(JoinLobbyUiController joinLobbyUiController, Player player, String ip) {
		super();
		this.player = player;
		this.gameSession = new GameSession(player);
		Debug.printMessage(this, "GameSession EndpointClient" + this.gameSession);
		this.gameSession.setLocalPlayer(player);
		this.player.setGameSession(this.gameSession);
		this.clientHandler = new ClientHandler(this);
		Debug.printMessage(this, "EndpointClient created from GUI 1");
		this.clientHandler.initLocalGame(player, ip);

		Debug.printMessage(this, "EndpointClient created from GUI 2");
	}

	public EndpointClient() {

	}

	private static final Logger LOG = LoggerFactory.getLogger(EndpointClient.class);

	/**
	 * Method used to connect to server. TODO : evaluate createAccount / Login
	 *
	 * @param ses Session in use
	 * @throws IOException     is thrown
	 * @throws EncodeException is thrown
	 */
	@OnOpen
	public void onOpen(final Session ses)
			throws IOException, EncodeException {
//        ses.getBasicRemote().sendObject(new CreateAccountRequestPacket("testuser", "testPW"));

/*
   ses.getBasicRemote().sendObject(new WrappedPacket(PacketType.CREATE_ACCOUNT_REQUEST_PACKET,
      new CreateAccountRequestPacket("testuser", "testPW")));
   Debug.printMessage(this,"CREATE_ACCOUNT_REQUEST sent to " + ses.getId());
*/
		this.server = ses;
		//this.clientHandler = new ClientHandler(this);
		//this.gameSession = new GameSession();
		//this.player.setGameSession(this.gameSession);
		ses.setMaxBinaryMessageBufferSize(1024 * 1024 * 20);
		ses.setMaxTextMessageBufferSize(1024 * 1024 * 20);


	}

  @OnMessage
  public void onMessage(final WrappedPacket packet, Session ses) {
    LOG.info(this.player.getUsername()
            + ": A packet has been sent here by the server, it is of the type: {} send by {}",
        packet.getPacketType().toString(), ses.getId());
    PacketType type = packet.getPacketType();
    switch (type) {
      case PLAYER_ORDER_PACKET:
        break;

      case CHAT_MESSAGE_PACKET:
        this.clientHandler.saveChatMessage(packet);
        break;

      case GAME_START_PACKET:
        this.clientHandler.startGame(packet);
        Debug.printMessage("GameStart called");
        break;

      case REQUEST_TURN_PACKET:
        Debug.printMessage(this, "REQUEST TURN RECIEVED");
        this.clientHandler.makeTurn(packet);
        break;

      case GAME_UPDATE_PACKET:
        this.clientHandler.updateGame(packet);
        break;

      case GAME_WIN_PACKET:
        this.clientHandler.endGame(packet);
        break;

      case ILLEGAL_TURN_PACKET:
        this.clientHandler.makeTurn(packet);
        break;

      case CHECK_CONNECTION_PACKET:
        Debug.printMessage("CHECK CON RECEIVED");
        break;

      case PLAYER_LIST_PACKET:
        this.clientHandler.updatePlayerList(packet);


      //
    }

  }

  /**
   * function to easily send packets to server
   *
   * @param wrappedPacket
   * @author tgeilen
   */
  public void sendToServer(WrappedPacket wrappedPacket) {
    Debug.printMessage(this, "I am sending something to the server...");
    try {
      this.server.getBasicRemote().sendObject(wrappedPacket);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (EncodeException e) {
      e.printStackTrace();
    }
    Debug.printMessage(this, "I have sent something to the server...");
  }

  public GameSession getGameSession() {
    return gameSession;
  }

  public Player getPlayer() {
    return player;
  }

	public ClientHandler getClientHandler() {
		return this.clientHandler;
	}
}


