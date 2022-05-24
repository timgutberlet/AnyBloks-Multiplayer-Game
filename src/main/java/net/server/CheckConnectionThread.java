package net.server;

import game.model.Debug;
import game.model.GameSession;
import java.util.concurrent.TimeUnit;
import javax.websocket.Session;
import net.packet.CheckConnectionPacket;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.transmission.EndpointServer;

/**
 * a new thread to send check connection packets.
 *
 * @author tgeilen
 * @Date 16.05.22
 */
public class CheckConnectionThread extends Thread {

  public static boolean turnReceived;
  private final Session session;
  private final GameSession gameSession;
  private final String username;
  private int requestCounter;
  private CheckConnectionPacket checkConnectionPacket;
  private Boolean connectionCrashed = false;


  /**
   * initializes a new check connection thread out of a game session, the considered username and
   * the serverEndpoint.
   *
   * @param gameSession gameSession
   * @param username username
   * @param serverEndpoint serverEndpoint
   */
  public CheckConnectionThread(GameSession gameSession, String username,
      EndpointServer serverEndpoint) {

    this.requestCounter = 0;

    turnReceived = false;

    this.session = serverEndpoint.getUsername2Session().get(username);

    this.gameSession = gameSession;

    this.username = username;


  }

  /**
   * starts the thread.
   */
  public void run() {
    int threshold = 10;

    turnReceived = false;

    while (this.requestCounter < threshold && !this.connectionCrashed && !turnReceived) {
      this.checkConnectionPacket = new CheckConnectionPacket();

      WrappedPacket wrappedPacket = new WrappedPacket(PacketType.CHECK_CONNECTION_PACKET,
          this.checkConnectionPacket);

      try {
        Debug.printMessage(this,
            "_________Sending CheckConnection message to " + this.username + "_________  "
                + this.requestCounter);
        this.session.getBasicRemote().sendObject(wrappedPacket);

      } catch (Exception e) {
        this.connectionCrashed = true;
        Debug.printMessage(this, "THE SERVER LOST CONNECTION TO " + this.username);
        this.gameSession.changePlayer2Ai(this.username);
        e.printStackTrace();

      }

      try {
        TimeUnit.SECONDS.sleep(6);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      this.requestCounter++;

    }

    if (this.requestCounter >= threshold) {
      Debug.printMessage(this,
          "THE SERVER LOST CONNECTION TO " + this.username + "WITHOUT EXCEPTION");
      this.gameSession.changePlayer2Ai(this.username);
    }

  }

}
