package game.model;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import game.model.chat.Chat;
import game.model.gamemodes.GameMode;
import game.model.gamemodes.GameModeClassic;
import game.model.gamemodes.GameModeDuo;
import game.model.player.Player;
import game.model.player.PlayerType;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.LoginRequestPacket;
import net.packet.game.InitGamePacket;
import net.server.DbServer;
import net.server.HostServer;
import net.transmission.EndpointClient;
import org.junit.jupiter.api.Test;

/**
 * Test whether the server starts a game.
 *
 * @author tgeilen
 */
public class AddPlayersTest {

	static HostServer hostServer = new HostServer();

	/**
	 * Test method to ensure players are added on game start.
	 */
	@Test
	public void testAddPlayers() {
		//Starting a websocketServer
		try {
			hostServer.startWebsocket(8081);
			Debug.printMessage("[testChatServer] Server is running");
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//preparing accounts to sign into
		DbServer dbServer = null;
		try {
			dbServer = DbServer.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNotNull(dbServer);

		String[] users = new String[]{"User1", "User2"};
		ArrayList<Player> players = new ArrayList<>();
		ArrayList<EndpointClient> endpointClients = new ArrayList<>();
		ArrayList<Session> sessions = new ArrayList<>();
		String passwordHash = "unhashedTestPassword";
		String token = "TestToken";

		for (String user : users) {
			//Make sure the accounts are in the database
			dbServer.deleteAccount(user);
			dbServer.newAccount(user, passwordHash);
			dbServer.insertAuthToken(user, token);
			Player player = new Player(user, PlayerType.REMOTE_PLAYER);
			players.add(player);
			EndpointClient endpointClient = new EndpointClient(player);
			endpointClient.getGameSession().setAuthToken(token);
			endpointClients.add(endpointClient);
			//Connect the endpoints to the server
			try {
				Session session = ContainerProvider.getWebSocketContainer()
						.connectToServer(endpointClient, URI.create("ws://localhost:8081/packet"));
			} catch (DeploymentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			//Log in the accounts that have been connected
			WrappedPacket wrappedLoginPacket = new WrappedPacket(PacketType.LOGIN_REQUEST_PACKET,
					new LoginRequestPacket(user, token, PlayerType.REMOTE_PLAYER), user, token);
			endpointClient.sendToServer(wrappedLoginPacket);

		}

		//Adding bots to the game session
		users = new String[]{"Bot1", "Bot2"};
		for (String user : users) {
			//Make sure the accounts are in the database
			dbServer.deleteAccount(user);
			dbServer.newAccount(user, passwordHash);
			dbServer.insertAuthToken(user, token);
			Player player = new Player(user, PlayerType.AI_EASY);
			players.add(player);
			EndpointClient endpointClient = new EndpointClient(player);
			endpointClient.getGameSession().setAuthToken(token);
			endpointClients.add(endpointClient);
			//Connect the endpoints to the server
			try {
				Session session = ContainerProvider.getWebSocketContainer()
						.connectToServer(endpointClient, URI.create("ws://localhost:8081/packet"));
			} catch (DeploymentException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

			//Log in the accounts that have been connected
			WrappedPacket wrappedLoginPacket = new WrappedPacket(PacketType.LOGIN_REQUEST_PACKET,
					new LoginRequestPacket(user, token, PlayerType.REMOTE_PLAYER), user, token);
			endpointClient.sendToServer(wrappedLoginPacket);

		}

		LinkedList<GameMode> gameModes = new LinkedList<>();
		gameModes.add(new GameModeClassic());
		InitGamePacket initGamePacket = new InitGamePacket(gameModes,PlayerType.AI_EASY);

		WrappedPacket wrappedPacket = new WrappedPacket(PacketType.INIT_GAME_PACKET, initGamePacket);

		endpointClients.get(0).sendToServer(wrappedPacket);

		try {
			TimeUnit.MILLISECONDS.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}





		assertEquals(4,endpointClients.get(1).getGameSession().getPlayerList().size());




		//Stopping the websocketServer again
		hostServer.stopWebsocket();

		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
