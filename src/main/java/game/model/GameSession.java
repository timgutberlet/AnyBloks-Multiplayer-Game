package game.model;

import game.model.chat.Chat;
import game.model.chat.ChatMessage;
import game.model.gamemodes.GameMode;
import game.model.player.Player;
import game.model.player.PlayerType;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.LoginRequestPacket;
import net.server.ClientHandler;
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

	private Chat chat;
	private ArrayList<Player> playerList;
	private Player hostPlayer;
	private Game game;
	private GameServer gameServer;

	private LinkedList<GameMode> gameList;

	private PlayerType defaultAI;

	private int numOfBots = 0;

	private Player localPlayer;

	private final HashMap<String, Integer> scoreboard = new HashMap<String, Integer>();

	/**
	 * a Session is created by a Player in the MainMenu
	 *
	 * @param player
	 * @author tgeilen
	 */
	public GameSession(Player player) {
		//create chatThread and start it
		this.chat = new Chat();
		this.chat.run();

		this.playerList = new ArrayList<Player>();
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
	 * a Session is created
	 *
	 * @param
	 * @author tgeilen
	 */
	public GameSession() {
		//create chatThread and start it
		this.chat = new Chat();
		this.chat.run();

		this.playerList = new ArrayList<Player>();

		this.defaultAI = PlayerType.AI_EASY;

		Debug.printMessage(this, "GameSession started");

		try {
			//hostServer.startWebsocket(8080);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * a Player can join a Session from the MainMenu
	 *
	 * @param player
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
	 * setter for localPlayer
	 */

	public void setLocalPlayer(Player localPlayer) {
		this.localPlayer = localPlayer;
	}

	/**
	 * Function returning the local player
	 *
	 * @author tgutberl
	 */
	public Player getLocalPlayer() {
		return this.localPlayer;
	}

	/**
	 * function to set the host of a session
	 *
	 * @param host
	 * @author tgeilen
	 */
	public void addHost(Player host) {
		this.playerList.add(host);
		host.setGameSession(this);
	}


	/**
	 * creates and starts a new Game
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
	 * creates and starts a new Game on the server
	 *
	 * @author tgeilen
	 */
	public Game startGameServer() {

		//while (this.getPlayerList().size()!=gameMode.getNeededPlayers()){
		GameMode gameMode = this.gameList.pop();

		while (this.getPlayerList().size()
				<gameMode.getNeededPlayers()-1) { //TODO make dependdenc on gamemode
			this.addBot(PlayerType.AI_EASY);
		}

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
	 * add the value of the placed poly to the scoreboard
	 *
	 * @param player
	 * @param value
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
	 * function to update GameState of current game
	 *
	 * @author tgeilen
	 */
	public void updateGame(GameState gameState) {
		this.game.updateGameState(gameState);
		this.playerList = gameState.getPlayerList();
	}

	/**
	 * functions that triggered when someone has won the game
	 *
	 * @author tgeilen
	 */
	public void endGame(String usernameWinner) {
		//TODO add logic
	}

	public void addChatMessage(ChatMessage chatMessage) {
		this.chat.addMessage(chatMessage);

	}

	public void addBot(PlayerType playerType) {
		this.numOfBots++;
		Player bot = new Player("Bot " + this.numOfBots, playerType);
		//bot.start();
		this.addToSession(bot);
		//this.getPlayerList().add(bot);
		Debug.printMessage(this, "Bot " + this.numOfBots + " added to session");

	}

	public void addToSession(Player player) {
		final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		EndpointClient endpointClient = new EndpointClient(player);
		Session session = null;

		try {
			session = container.connectToServer(endpointClient, URI.create("ws://localhost:8081/packet"));

			//Login
			LoginRequestPacket loginRequestPacket = new LoginRequestPacket(player.getUsername(),
					"1234");
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


	public void changePlayer2AI(String username) {
		for (Player player : this.playerList) {
			if (player.getUsername().equals(username)) {
				player.setAI(true);
				player.setType(this.defaultAI);

				final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
				EndpointClient endpointClient = new EndpointClient(player);
				Session session = null;

				try {
					session = container.connectToServer(endpointClient,
							URI.create("ws://localhost:8081/packet"));

					//Login
					LoginRequestPacket loginRequestPacket = new LoginRequestPacket(player.getUsername(),
							"1234");
					Debug.printMessage(
							"LoginRequestPacket has been sent to the server to change remote player to AI");
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
		}


	}

	/**
	 * functions thats stops the current session by stopping the hostServer
	 */
	public void stopSession() {
		hostServer.stop();
	}

	public Chat getChat() {
		return this.chat;
	}

	public Game getGame() {
		return this.game;
	}

	public ArrayList<Player> getPlayerList() {
		return this.playerList;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	public void setInboundServerHandler(InboundServerHandler inboundServerHandler) {
		this.inboundServerHandler = inboundServerHandler;
	}

	public void setOutboundServerHandler(OutboundServerHandler outboundServerHandler) {
		this.outboundServerHandler = outboundServerHandler;
	}

	public OutboundServerHandler getOutboundServerHandler() {
		return outboundServerHandler;
	}

	public InboundServerHandler getInboundServerHandler() {
		return inboundServerHandler;
	}

	public GameServer getGameServer() {
		return gameServer;
	}

	public LinkedList<GameMode> getGameList() {
		return gameList;
	}

	public void setGameList(LinkedList<GameMode> gameList) {
		this.gameList = gameList;
	}

	/**
	 * function that helps to output the most relevant information of a session
	 *
	 * @return
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

