package net.transmission;

import java.net.URI;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import net.packet.account.CreateAccountRequestPacket;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.server.HostServer;

/**
 * Test only.
 *
 * @author tbuscher
 */
public class UseServerCom {

  static HostServer hostServer = new HostServer();

  public static void main(String[] args) {

    try {
      System.out.println("Got here Caller function");

      hostServer.startWebsocket(8080);
      System.out.println("Made it past start");

      final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
      EndpointClient client = new EndpointClient();
      System.out.println("Gonna connect");

      Session ses = container.connectToServer(client, URI.create("ws://localhost:8080/packet"));
      System.out.println("Past connection here");

      WrappedPacket packet = new WrappedPacket(PacketType.CREATE_ACCOUNT_REQUEST_PACKET,
          new CreateAccountRequestPacket("testuser", "unhashed"));

      ses.getBasicRemote().sendObject(packet);
      ses.close();
      hostServer.stop();

    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
