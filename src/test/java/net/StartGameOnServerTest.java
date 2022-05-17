package net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import game.model.gamemodes.GMClassic;
import game.model.gamemodes.GameMode;
import game.model.player.Player;
import game.model.player.PlayerType;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.LoginRequestPacket;
import net.packet.game.GameStartPacket;
import net.packet.game.InitGamePacket;
import net.packet.game.InitSessionPacket;
import net.server.HostServer;
import net.tests.NoLogging;
import net.transmission.EndpointClient;
import net.transmission.EndpointServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * @author tgeilen
 * @Date 17.05.22
 */
public class StartGameOnServerTest {

	static HostServer hostServer = new HostServer();
	static EndpointClient client;

	@BeforeAll
	public static void beforeAll() {

		//Starting the server
		try {
			org.eclipse.jetty.util.log.Log.setLog(new NoLogging());

			hostServer.startWebsocket(8081);
			//Debug.printMessage("[testChatServer] Server is running");
			//TimeUnit.SECONDS.sleep(3);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//Create and connect client
		try {
			final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			Player localPlayer = new Player("LocalPlayer", PlayerType.AI_EASY);
			client = new EndpointClient(localPlayer);

			Session session = null;

			String IPAdress = Inet4Address.getLocalHost().getHostAddress();

			session = container.connectToServer(client, URI.create("ws://" + IPAdress + ":8081/packet"));

			TimeUnit.SECONDS.sleep(1);

		} catch (
				UnknownHostException e) {
			e.printStackTrace();
		} catch (
				DeploymentException e) {
			e.printStackTrace();
		} catch (
				IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		InitSessionPacket initSessionPacket = new InitSessionPacket();
		WrappedPacket wrappedPacket = new WrappedPacket(PacketType.INIT_SESSION_PACKET,initSessionPacket);

		client.sendToServer(wrappedPacket);

		//send login request
		LoginRequestPacket loginRequestPacket = new LoginRequestPacket("username","password",PlayerType.REMOTE_PLAYER);
		wrappedPacket = new WrappedPacket(PacketType.LOGIN_REQUEST_PACKET,loginRequestPacket);
		client.sendToServer(wrappedPacket);

	}

	@Test
	public void startGame(){



		LinkedList<GameMode> gameModes = new LinkedList<>();
		GameMode gameMode = new GMClassic();
		gameModes.add(gameMode);
		gameModes.add(new GMClassic());

		InitGamePacket initGamePacket = new InitGamePacket(gameModes);
		WrappedPacket wrappedPacket = new WrappedPacket(PacketType.INIT_GAME_PACKET,initGamePacket);

		client.sendToServer(wrappedPacket);

		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		;
		ArrayList<Player> remotePlayerList =  client.getGameSession().getPlayerList();
		assertEquals(gameMode.getNeededPlayers(),remotePlayerList.size());

	}


}
