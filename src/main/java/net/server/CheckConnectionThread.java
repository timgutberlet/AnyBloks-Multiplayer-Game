package net.server;

import game.model.Debug;
import game.model.GameSession;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import net.packet.CheckConnectionPacket;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.transmission.EndpointServer;

/**
 * @author tgeilen
 * @Date 16.05.22
 */
public class CheckConnectionThread extends Thread {

	private int requestCounter;

	private CheckConnectionPacket checkConnectionPacket;

	private Session session;

	private GameSession gameSession;

	private String username;

	private Boolean connectionCrashed = false;

	public static boolean turnRecieved;


	public CheckConnectionThread(GameSession gameSession, String username, EndpointServer serverEndpoint) {

		this.requestCounter = 0;

		turnRecieved = false;

		this.session = serverEndpoint.getUsername2Session().get(username);

		this.gameSession = gameSession;

		this.username = username;


	}

	public void run() {

		turnRecieved = false;

		while (this.requestCounter < 10 && !this.connectionCrashed && !turnRecieved) {
			this.checkConnectionPacket = new CheckConnectionPacket();

			WrappedPacket wrappedPacket = new WrappedPacket(PacketType.CHECK_CONNECTION_PACKET,
					this.checkConnectionPacket);

			try {
				Debug.printMessage(this,"Sending CheckConnection message to client");
				this.session.getBasicRemote().sendObject(wrappedPacket);

			} catch (Exception e) {
				this.connectionCrashed = true;
				Debug.printMessage(this, "THE SERVER LOST CONNECTION TO A CLIENT");
				this.gameSession.changePlayer2AI(this.username);
				e.printStackTrace();

			}

			try {
				TimeUnit.SECONDS.sleep(6);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}

	}

}
