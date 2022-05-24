package net.transmission;

import game.model.Debug;
import java.net.URI;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.CreateAccountRequestPacket;
import net.server.HostServer;

/**
 * Test only.
 *
 * @author tbuscher
 */
public class UseServerCom {

  static HostServer hostServer = new HostServer();

  /**
   * run the local setting tests.
   *
   * @param args String[]
   */
  public static void main(String[] args) {

    try {
      Debug.printMessage("Got here Caller function");

      hostServer.startWebsocket(8080);
      Debug.printMessage("Made it past start");

      final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
      EndpointClient client = new EndpointClient();
      Debug.printMessage("Gonna connect");

      Session ses = container.connectToServer(client, URI.create("ws://localhost:8080/packet"));
      Debug.printMessage("Past connection here");

      WrappedPacket packet = new WrappedPacket(PacketType.CREATE_ACCOUNT_REQUEST_PACKET,
          new CreateAccountRequestPacket("testuser", "unhashed"));

      ses.getBasicRemote().sendObject(packet);
      ses.close();
      hostServer.stopWebsocket();

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
