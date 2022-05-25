package net.tests.game;

import game.model.Debug;
import game.model.player.Player;
import game.model.player.PlayerType;
import java.io.IOException;
import java.net.URI;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.LoginRequestPacket;
import net.packet.game.InitSessionPacket;
import net.transmission.EndpointClient;

/**
 * Tests only.
 *
 * @author tgeilen
 */
public class testRejoinGame {


  public testRejoinGame() {

  }


  public static void main(String[] args) {

    //org.eclipse.jetty.util.log.Log.setLog(new NoLogging());

    final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    Player localPlayer = new Player("LocalPlayer", PlayerType.REMOTE_PLAYER);
    EndpointClient client = new EndpointClient(localPlayer);

    Session ses = null;

    try {

      ses = container.connectToServer(client, URI.create("ws://192.168.178.27:8081/packet"));

      //Init session
      InitSessionPacket initSessionPacket = new InitSessionPacket();
      WrappedPacket wrappedPacket = new WrappedPacket(PacketType.INIT_SESSION_PACKET,
          initSessionPacket);
      ses.getBasicRemote().sendObject(wrappedPacket);

      //Login
      LoginRequestPacket loginRequestPacket = new LoginRequestPacket(localPlayer.getUsername(),
          "1234", localPlayer.getType());
      Debug.printMessage("LoginRequestPacket has been sent to the server");
      wrappedPacket = new WrappedPacket(PacketType.LOGIN_REQUEST_PACKET,
          loginRequestPacket);
      ses.getBasicRemote().sendObject(wrappedPacket);


    } catch (DeploymentException ex) {
      ex.printStackTrace();
    } catch (EncodeException ex) {
      ex.printStackTrace();
    } catch (IOException ex) {
      ex.printStackTrace();
    }

  }
}


