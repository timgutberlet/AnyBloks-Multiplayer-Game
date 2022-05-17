package net;



import static org.junit.jupiter.api.Assertions.assertEquals;

import game.model.player.Player;
import game.model.player.PlayerType;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.URI;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.LoginRequestPacket;
import net.server.HostServer;
import net.tests.NoLogging;
import net.transmission.EndpointClient;
import net.transmission.EndpointServer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Test whether login on server works correctly.
 *
 * @author tgeilen
 * @Date 17.05.22
 */
public class LoginToServerTest {

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


		} catch (
				UnknownHostException e) {
			e.printStackTrace();
		} catch (
				DeploymentException e) {
			e.printStackTrace();
		} catch (
				IOException e) {
			e.printStackTrace();
		}

	}

	@Test
	public void loginAccount(){
		LoginRequestPacket loginRequestPacket = new LoginRequestPacket("username","password",PlayerType.REMOTE_PLAYER);
		WrappedPacket wrappedPacket = new WrappedPacket(PacketType.LOGIN_REQUEST_PACKET,loginRequestPacket);
		this.client.sendToServer(wrappedPacket);

		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		ArrayList<Player> remotePlayerList =  EndpointServer.getGameSession().getPlayerList();
		assertEquals(1,remotePlayerList.size());
	}

}
