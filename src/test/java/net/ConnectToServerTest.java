package net;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import game.model.player.Player;
import game.model.player.PlayerType;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.URI;
import java.net.UnknownHostException;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import net.server.HostServer;
import net.tests.NoLogging;
import net.transmission.EndpointClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Test of the creation of a gameSession on a server.
 *
 * @author tgeilen
 * @Date 17.05.22
 */
public class ConnectToServerTest {

	static HostServer hostServer = new HostServer();

	@BeforeAll
	public static void beforeAll(){

		//Starting the server
		try {
			org.eclipse.jetty.util.log.Log.setLog(new NoLogging());

			hostServer.startWebsocket(8081);
			//Debug.printMessage("[testChatServer] Server is running");
			//TimeUnit.SECONDS.sleep(3);
		} catch (Exception e) {
			e.printStackTrace();
		}



	}

	@Test
	public void joinServer(){
		final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		Player localPlayer = new Player("LocalPlayer", PlayerType.AI_EASY);
		EndpointClient client = new EndpointClient(localPlayer);

		Session session = null;

		try {

			String IPAdress = Inet4Address.getLocalHost().getHostAddress();

			session = container.connectToServer(client, URI.create("ws://"+IPAdress+":8081/packet"));


		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (DeploymentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		assertNotNull(session);

		try {
			session.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
