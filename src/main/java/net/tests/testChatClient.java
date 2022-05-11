package net.tests;

import game.model.Debug;
import game.model.Game;
import game.model.GameSession;
import game.model.gamemodes.GMClassic;
import java.io.IOException;
import java.net.URI;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.LoginRequestPacket;
import net.packet.chat.ChatMessagePacket;
import net.packet.game.PlayerOrderPacket;
import net.server.HostServer;
import net.transmission.EndpointClient;
import org.eclipse.jetty.util.log.Logger;

/**
 * Tests only
 *
 * @author tgeilen
 */
public class testChatClient {


	private static ChatMessagePacket chatMessagePacket;

	public testChatClient() {

	}


	public static void main(String[] args) {

		org.eclipse.jetty.util.log.Log.setLog(new NoLogging());
		ChatMessagePacket chatMessagePacket = new ChatMessagePacket(
				LocalDateTime.now().toString() + " Hello World", "user1");
		//WrappedPacket wrappedPacket = new WrappedPacket(PacketType.CHAT_MESSAGE_PACKET, chatMessagePacket);

		final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		EndpointClient client = new EndpointClient();

		Session ses = null;

		try {
			ses = container.connectToServer(client, URI.create("ws://localhost:8081/packet"));

			LoginRequestPacket loginRequestPacket = new LoginRequestPacket(LocalDateTime.now().toString(),
					"1234");
			Debug.printMessage("LoginRequestPacket has been sent to the server");
			WrappedPacket wrappedPacket = new WrappedPacket(PacketType.LOGIN_REQUEST_PACKET,
					loginRequestPacket);
			ses.getBasicRemote().sendObject(wrappedPacket);
			int counter = 0;
			while (counter < 20) {
				TimeUnit.SECONDS.sleep(1);

				wrappedPacket = new WrappedPacket(PacketType.CHAT_MESSAGE_PACKET, chatMessagePacket);

				ses.getBasicRemote().sendObject(wrappedPacket);
				Debug.printMessage("Chat Message sent");
				counter++;
			}
			//ses.close();
		} catch (DeploymentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}


