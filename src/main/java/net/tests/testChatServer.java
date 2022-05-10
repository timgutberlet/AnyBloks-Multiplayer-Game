package net.tests;

import static net.packet.abstr.PacketType.GAME_UPDATE_PACKET;

import game.model.Debug;
import game.model.Game;
import game.model.GameSession;
import game.model.gamemodes.GMClassic;
import game.model.player.Player;
import game.model.player.PlayerType;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.TimeUnit;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import net.packet.abstr.WrappedPacket;
import net.packet.game.GameUpdatePacket;
import net.server.HostServer;
import net.transmission.EndpointClient;

/**
 * Tests only
 *
 * @author tgeilen
 */
public class testChatServer {

	static HostServer hostServer = new HostServer();


	public static void main(String[] args) {
		try {
			//org.eclipse.jetty.util.log.Log.setLog(new NoLogging());
			hostServer.startWebsocket(8081);
			Debug.printMessage("[testChatServer] Server is running");
			TimeUnit.SECONDS.sleep(3);
		} catch (Exception e) {
			e.printStackTrace();
		}

		//hostServer.stop();
		//Debug.printMessage("[testChatServer] Server has stopped");
	}




}

