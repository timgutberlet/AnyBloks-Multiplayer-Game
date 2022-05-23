package net.transmission;

//import game.controller.JoinGameLobbyController;
//import game.controller.LocalGameLobbyController;

import game.controller.HostLobbyUiController;
import game.controller.JoinAuthController;
import game.controller.JoinLobbyUiController;
import game.controller.LocalLobbyUiController;
import game.controller.TutorialUiController;
import game.model.Debug;
import game.model.GameSession;
import game.model.player.Player;
import java.io.IOException;
import javax.websocket.ClientEndpoint;
import javax.websocket.EncodeException;
import javax.websocket.OnError;
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

  private static final Logger LOG = LoggerFactory.getLogger(EndpointClient.class);
  private Session server;
  private GameSession gameSession;
  private ClientHandler clientHandler;
  private Player player;

	/**
	 * used to add the bots, therefore initLocalGame() not needed.
	 */

  public EndpointClient(Player player) {
    super();
    this.player = player;
    this.gameSession = new GameSession();
    this.gameSession.setLocalPlayer(player);
    this.player.setGameSession(this.gameSession);
    this.clientHandler = new ClientHandler(this);
    this.gameSession.setClientHandler(this.clientHandler);
  }

	/**
	 * shows local lobby ui controller and initialises the local game for the given player.
	 * @param localLobbyUiController lobby controller
	 * @param player given player
	 */
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

	/**
	 * shows the tutorial ui controller and initializes the tutorial game.
	 * @param localLobbyUiController lobby controller
	 * @param player player
	 */
	public EndpointClient(TutorialUiController localLobbyUiController, Player player) {
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

	/**
	 * shows host lobby ui controller and initialises the game for the given player.
	 * @param localLobbyUiController lobby controller
	 * @param player given player
	 */
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

	/**
	 * shows join lobby ui controller and initialises the game for the given player.
	 * @param joinLobbyUiController lobby controller
	 * @param player given player
	 * @param ip ip address where to join
	 */
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

	/**
	 * shows join authenticated lobby ui controller, where the lobby of the game is shown.
	 * @param joinAuthController controller
	 * @param player player
	 * @param ip ip address
	 * @param token authentication token
	 */
	public EndpointClient(JoinAuthController joinAuthController, Player player, String ip, String token) {
		super();
		this.player = player;
		this.gameSession = new GameSession(player);
		Debug.printMessage(this, "GameSession EndpointClient" + this.gameSession);
		this.gameSession.setAuthToken(token);

		this.gameSession.setLocalPlayer(player);
		this.player.setGameSession(this.gameSession);
		this.clientHandler = new ClientHandler(this);
		Debug.printMessage(this, "EndpointClient created from GUI 1");
		this.clientHandler.initLocalGame(player, ip, token);

		Debug.printMessage(this, "EndpointClient created from GUI 2");
	}

	public EndpointClient() {

	}



	/**
	 * Method used to connect to server.
	 *
	 * @param ses Session in use
	 * @throws IOException     is thrown
	 * @throws EncodeException is thrown
	 */
	@OnOpen
	public void onOpen(final Session ses)
			throws IOException, EncodeException {

		this.server = ses;
		ses.setMaxBinaryMessageBufferSize(1024 * 1024 * 20);
		ses.setMaxTextMessageBufferSize(1024 * 1024 * 20);

	}

	/**
	 * Handles if an error occurs.
	 * @param t error
	 * @param ses session
	 */
	@OnError
	public void onError(Throwable t, final Session ses){
		Debug.printMessage("Hi from ClientSideOnError()");
		t.printStackTrace();
		gameSession.setGotKicked(true);
	}

	/**
	 * handles incoming packets.
	 * @param packet packet
	 * @param ses session
	 */
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
        Debug.printMessage(this, "REQUEST TURN receiveD");
        this.clientHandler.makeTurn(packet);
        break;

      case GAME_UPDATE_PACKET:
        this.clientHandler.updateGame(packet);
        break;

      case GAME_WIN_PACKET:
        this.clientHandler.endGame(packet);
        break;

			case HOST_QUIT_PACKET:
				this.clientHandler.handleHostQuit();
				break;

      case ILLEGAL_TURN_PACKET:
        this.clientHandler.makeTurn(packet);
        break;

      case CHECK_CONNECTION_PACKET:
        Debug.printMessage("CHECK CON RECEIVED");
        break;

      case PLAYER_LIST_PACKET:
        this.clientHandler.updatePlayerList(packet);
				break;

			case LOBBY_SCORE_BOARD_PACKET:
				this.clientHandler.handleLobbyScoreBoardPacket(packet);
				break;

			case LOGIN_RESPONSE_PACKET:
					this.clientHandler.denyLogin(packet);

      //
    }

  }



	/**
	 * function to easily send packets to server from bot
	 *
	 * @param wrappedPacket
	 * @author tgeilen
	 */
	public void sendToServer(WrappedPacket wrappedPacket, String botUsername) {

		wrappedPacket.setToken("");
		wrappedPacket.setUsername(botUsername);

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
  /**
   * function to easily send packets to server
   *
   * @param wrappedPacket
   * @author tgeilen
   */
  public void sendToServer(WrappedPacket wrappedPacket) {

		wrappedPacket.setToken(this.gameSession.getAuthToken());
		wrappedPacket.setUsername(this.gameSession.getLocalPlayer().getUsername());

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

	/**
	 * Getter for session.
	 *
	 * @return session.
	 */
	public Session getSession(){
		return server;
	}
}


