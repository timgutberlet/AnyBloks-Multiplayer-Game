package game.model;

import game.model.chat.Chat;
import game.model.chat.ChatMessage;
import game.model.gamemodes.GameMode;
import game.model.player.Player;
import game.model.player.PlayerType;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.CreateAccountRequestPacket;
import net.packet.account.LoginRequestPacket;
import net.packet.chat.ChatMessagePacket;
import net.packet.game.InitSessionPacket;
import net.server.ClientHandler;
import net.server.HashingHandler;
import net.server.HostServer;
import net.server.InboundServerHandler;
import net.server.OutboundServerHandler;
import net.transmission.EndpointClient;

/**
 * a session is the central place taking care of players joining, starting a game, selecting
 * gamemode, chat, etc...
 *
 * @author tgeilen
 */
public class GameSession {

	private static final HostServer hostServer = new HostServer();
	public InboundServerHandler inboundServerHandler;
	public OutboundServerHandler outboundServerHandler;

	public ClientHandler clientHandler;

	private final Chat chat;
	private ArrayList<Player> playerList;
	private Player hostPlayer;
	private Game game;

	private LinkedList<GameMode> gameList;

	private PlayerType defaultAI;

	private int numOfBots = 0;

	private Player localPlayer;

	private final HashMap<String, Integer> scoreboard = new HashMap<>();

	public HashMap<String, Integer> gameSessionScoreboard = new HashMap<>();

	public static ArrayList<String> currentGameIds = new ArrayList<>();

	private Boolean localPlayerTurn = false;

	private Boolean updatingGameState = false;

	/**
	 * used to change the view to InGameController.
	 *
	 */
	private Boolean gameStarted = false;

	/**
	 * a Session is created by a Player in the MainMenu.
	 *
	 * @param player
	 * @author tgeilen
	 */
	public GameSession(Player player) {
		//create chatThread and start it
		this.chat = new Chat();
		//this.chat.run();

		this.playerList = new ArrayList<>();
		this.hostPlayer = player;
		this.addPlayer(this.hostPlayer);
		this.localPlayer = this.hostPlayer;

		this.defaultAI = PlayerType.AI_EASY;

		try {
			hostServer.startWebsocket(8080);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

  /**
   * a Session is created.
   *
   * @param
   * @author tgeilen
   */
  public GameSession() {
    //create chatThread and start it
    this.chat = new Chat();
    //this.chat.run();

		this.playerList = new ArrayList<>();

		this.defaultAI = PlayerType.AI_EASY;

		Debug.printMessage(this, "GameSession started");

	}

	/**
	 * a Player can join a Session from the MainMenu.
	 *
	 * @param player player
	 * @author tgeilen
	 * @author tgutberl
	 */
	public void addPlayer(Player player) {
		this.playerList.add(player);
		if (this.playerList.size() == 1) {
			this.localPlayer = player;
		}
		player.setGameSession(this);
	}

	/**
	 * Setter for local player.
	 *
	 * @param localPlayer localPlayer
	 */

	public void setLocalPlayer(Player localPlayer) {
		this.localPlayer = localPlayer;
	}

	/**
	 * Function returning the local player.
	 *
	 * @author tgutberl
	 */
	public Player getLocalPlayer() {
		return this.localPlayer;
	}

	/**
	 * function to set the host of a session.
	 *
	 * @param host host
	 * @author tgeilen
	 */
	public void addHost(Player host) {
		this.playerList.add(host);
		host.setGameSession(this);
	}


	/**
	 * creates and starts a new Game.
	 *
	 * @author tgeilen
	 */
	public Game startGame(GameMode gameMode) {

		this.game = new Game(this, gameMode);
		Debug.printMessage(this, "Game created at client");
		this.game.startGame();
		Debug.printMessage(this, "Game started at client");

		return this.game;
	}

	/**
	 * creates and starts a new Game on the server.
	 *
	 * @author tgeilen
	 */
	public Game startGameServer() {

		//while (this.getPlayerList().size()!=gameMode.getNeededPlayers()){
		GameMode gameMode = this.gameList.pop();

		while (this.getPlayerList().size()
				< gameMode.getNeededPlayers() - 1) {
			this.addBot(this.defaultAI);
		}

		Debug.printMessage("There a now" + this.getPlayerList().size() + " players connected");

		try {
			Debug.printMessage(this, "Waiting for clients to establish connection");
			TimeUnit.SECONDS.sleep(5);
			Debug.printMessage(this, "Starting a new game");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		this.game = new Game(this, gameMode, true);
		this.game.startGame();

		return this.game;
	}


	/**
	 * add the value of the placed poly to the scoreboard.
	 *
	 * @param player player
	 * @param value  score
	 * @author tgeilen
	 */
	public void increaseScore(Player player, int value) {
		Integer currentScore = this.scoreboard.get(player.getUsername());

		if (currentScore != null) {
			this.scoreboard.put(player.getUsername(), currentScore + value);
		} else {
			this.scoreboard.put(player.getUsername(), value);
		}

	}

	/**
	 * function to update GameState of current game.
	 *
	 * @author tgeilen
	 */
	public void updateGame(GameState gameState) {
		this.updatingGameState = true;
		this.game.updateGameState(gameState);
		this.playerList = gameState.getPlayerList();
		for(Player p: playerList){
			if(p.equals(this.localPlayer)){
				Debug.printMessage(this,"Local player currently " + this.localPlayer);
				Debug.printMessage(this,"Local player new " + p);
				this.localPlayer = p;
				Debug.printMessage(this,"Local player changed to " + this.localPlayer);
			}
		}
		this.updatingGameState = false;
		System.out.println(gameState.toString());
	}

	/**
	 * functions that triggered when someone has won the game.
	 *
	 * @author tgeilen
	 */
	public void endGame(String usernameWinner) {
		//TODO add logic
	}

	/**
	 * function to add a new msg and broadcast to all players
	 *
	 * @param msg message
	 */
	public void addChatMessage(String msg) {

		ChatMessage chatMessage = new ChatMessage(this.localPlayer.getUsername(),msg);

		this.clientHandler.broadcastChatMessage(chatMessage);

	}

	/**
	 *
	 * function to save a msg from remote in the local chat
	 *
	 */

	public void saveChatMessage(ChatMessage chatMessage){
		this.chat.addMessage(chatMessage);
	}

	/**
	 * create a new bot player and add to this GameSession.
	 *
	 * @param playerType playerType
	 */
	public void addBot(PlayerType playerType) {
		this.numOfBots++;
		Player bot = new Player("Bot " + this.numOfBots, playerType);
		//bot.start();
		this.addToSession(bot);

		//this.getPlayerList().add(bot);
		Debug.printMessage(this, "Bot " + this.numOfBots + " added to session");

	}

	/**
	 * create a new remote client and connect to server.
	 *
	 * @param player player
	 */
	public void addToSession(Player player) {

		final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		EndpointClient endpointClient = new EndpointClient(player);
		Session session;

		try {
			session = container.connectToServer(endpointClient, URI.create("ws://localhost:8081/packet"));
			//Login
			LoginRequestPacket loginRequestPacket = new LoginRequestPacket(player.getUsername(),
					"1234", player.getType());
			Debug.printMessage("LoginRequestPacket has been sent to the server");
			WrappedPacket wrappedPacket = new WrappedPacket(PacketType.LOGIN_REQUEST_PACKET,
					loginRequestPacket);
			session.getBasicRemote().sendObject(wrappedPacket);


		} catch (DeploymentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}


	}

	/**
	 * Updates the scoreBoard of the lobby after a game has ended.
	 */
	public void updateGameSessionScoreboard() {

		GameState gameState = this.getGame().getGameState();
		for (Player p : this.getPlayerList()) {
			int oldScore = this.gameSessionScoreboard.get(p.getUsername());
			Color c = gameState.getColorFromPlayer(p);
			int score = gameState.getBoard().getScoreOfColor(c);
			int updatedScore = oldScore + score;
			this.gameSessionScoreboard.put(p.getUsername(), updatedScore);
		}
		String gameMode = gameState.getGameMode().getName();
	}

	/**
	 * onnects a new AI Player with the same name as a player who lost connection.
	 *
	 * @param username username
	 */
	public void changePlayer2AI(String username) {
		for (Player player : this.playerList) {
			if (player.getUsername().equals(username)) {
				player.setAI(true);
				player.setType(this.defaultAI);

				final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
				EndpointClient endpointClient = new EndpointClient(player);
				Session session;

				try {
					session = container.connectToServer(endpointClient,
							URI.create("ws://localhost:8081/packet"));

					//Login
					LoginRequestPacket loginRequestPacket = new LoginRequestPacket(player.getUsername(),
							"1234", player.getType());
					Debug.printMessage(
							"LoginRequestPacket has been sent to the server to change remote player to AI");
					WrappedPacket wrappedPacket = new WrappedPacket(PacketType.LOGIN_REQUEST_PACKET,
							loginRequestPacket);
					session.getBasicRemote().sendObject(wrappedPacket);

					Debug.printMessage(this, "Waiting for new AI to connect (with a fixed amount of time)");

					TimeUnit.SECONDS.sleep(5);

					this.outboundServerHandler.sendGameStart(player.getUsername(), this.game.getGameState());

					this.outboundServerHandler.requestTurn(player.getUsername());

				} catch (DeploymentException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}


	}


	public EndpointClient joinLocalGame() {
		final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		Player localPlayer = new Player("LocalPlayer", PlayerType.AI_EASY);
		EndpointClient client = new EndpointClient(localPlayer);

		Session ses;

		try {

			String IPAdress = Inet4Address.getLocalHost().getHostAddress();

			ses = container.connectToServer(client, URI.create("ws://" + IPAdress + ":8081/packet"));

			//Init session
			InitSessionPacket initSessionPacket = new InitSessionPacket();
			WrappedPacket wrappedPacket = new WrappedPacket(PacketType.INIT_SESSION_PACKET,
					initSessionPacket);
			ses.getBasicRemote().sendObject(wrappedPacket);

			//Create Account
			String passwordHash = HashingHandler.sha256encode("123456");
			CreateAccountRequestPacket createAccReq = new CreateAccountRequestPacket(
					localPlayer.getUsername(),
					passwordHash);
			wrappedPacket = new WrappedPacket(PacketType.CREATE_ACCOUNT_REQUEST_PACKET,
					createAccReq);
			//... and send it
			client.sendToServer(wrappedPacket);

			//Sleep so updates can be made in DB
			try {
				TimeUnit.MILLISECONDS.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			//Login
			LoginRequestPacket loginRequestPacket = new LoginRequestPacket(localPlayer.getUsername(),
					"1234", localPlayer.getType());
			Debug.printMessage("LoginRequestPacket has been sent to the server");
			wrappedPacket = new WrappedPacket(PacketType.LOGIN_REQUEST_PACKET,
					loginRequestPacket);
			ses.getBasicRemote().sendObject(wrappedPacket);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (DeploymentException e) {
			e.printStackTrace();
		} catch (EncodeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return client;
	}

	/**
	 * functions thats stops the current session by stopping the hostServer.
	 */
	public void stopSession() {
		hostServer.stop();
	}

	/**
	 * gets the chat of the current session.
	 *
	 * @return Chat chat
	 */
	public Chat getChat() {
		return this.chat;
	}

	/**
	 * gets the game of teh current gamesession.
	 *
	 * @return Game chat
	 */
	public Game getGame() {
		return this.game;
	}

	/**
	 * returns teh current PlayerList.
	 *
	 * @return ArrayList<Player>
	 */
	public ArrayList<Player> getPlayerList() {
		return this.playerList;
	}

	/**
	 * returns the scoreboard of the current session.
	 *
	 * @return HashMap
	 */
	public HashMap<String, Integer> getGameSessionScoreboard() {
		return gameSessionScoreboard;
	}

	/**
	 * returns the scoreboard of the current game.
	 *
	 * @return HashMap
	 */
	public HashMap<String, Integer> getScoreboard() {
		return scoreboard;
	}

	/**
	 * Getter.
	 */


	public void setGame(Game game) {
		this.game = game;
	}


	/**
	 * sets the OutboundServerHandler.
	 *
	 * @param outboundServerHandler
	 */
	public void setOutboundServerHandler(OutboundServerHandler outboundServerHandler) {
		this.outboundServerHandler = outboundServerHandler;
	}

	/**
	 * returns the OutboundServerHanlder of the current gamesession.
	 *
	 * @return OutboundServerhandler
	 */
	public OutboundServerHandler getOutboundServerHandler() {
		return outboundServerHandler;
	}

	/**
	 * returns the InboundServerHanlder of the current gamesession.
	 *
	 * @return InboundServerhandler
	 */
	public InboundServerHandler getInboundServerHandler() {
		return inboundServerHandler;
	}

  /**
   * sets the InboundServerHandler.
   *
   * @param inboundServerHandler
   */

  public void setInboundServerHandler(InboundServerHandler inboundServerHandler) {
    this.inboundServerHandler = inboundServerHandler;
  }

	/**
	 * returns the list of games that will be played in the tournament.
	 *
	 * @return gameList
	 */
	public LinkedList<GameMode> getGameList() {
		return gameList;
	}

	/**
	 * sets the list games that will be played in a tournament
	 *
	 * @param gameList
	 */
	public void setGameList(LinkedList<GameMode> gameList) {
		this.gameList = gameList;
	}

	/**
	 * sets the playerList of the current gamesession
	 *
	 * @param playerList
	 */
	public void setPlayerList(ArrayList<Player> playerList) {
		this.playerList = playerList;
	}

	/**
	 * sets the value of the defaultAI
	 *
	 * @param defaultAI dfault AI type
	 */
	public void setDefaultAI(PlayerType defaultAI) {
		this.defaultAI = defaultAI;
	}

	public void setLocalPlayerTurn(Boolean localPlayerTurn) {
		this.localPlayerTurn = localPlayerTurn;
	}

	public Boolean isLocalPlayerTurn() {
		return localPlayerTurn;
	}

	public Boolean isGameStarted() {
		return gameStarted;
	}

	public void setGameStarted(Boolean gameStarted) {
		this.gameStarted = gameStarted;
	}

	public Boolean isUpdatingGameState() {
		return updatingGameState;
	}

  public void setIp(String ip) {
    this.ip = ip;
  }

	/**
	 * function that helps to output the most relevant information of a session.
	 *
	 * @return String
	 * @author tgeilen
	 */
	@Override
	public String toString() {
		String str = "[SESSION INFO] \n";

		for (Player p : this.playerList) {
			str +=
					p.getUsername() + "  |  " + p.getType() + "  |  " + this.scoreboard.get(p.getUsername())
							+ "\n";
		}

		return str;
	}


}

