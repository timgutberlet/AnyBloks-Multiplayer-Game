package net.tests.chat;

import game.model.Debug;
import game.model.player.Player;
import game.model.player.PlayerType;
import java.io.IOException;
import java.net.URI;
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
import net.tests.NoLogging;
import net.transmission.EndpointClient;

/**
 * Tests only
 *
 * @author tgeilen
 */
public class testChatClient2 {


  private static ChatMessagePacket chatMessagePacket;

  public testChatClient2() {

  }


  public static void main(String[] args) {

    Player player = new Player("user2",PlayerType.REMOTE_PLAYER);

    org.eclipse.jetty.util.log.Log.setLog(new NoLogging());




    final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    EndpointClient client = new EndpointClient(player);

    Session ses;

    try {
      ses = container.connectToServer(client, URI.create("ws://localhost:8081/packet"));

      LoginRequestPacket loginRequestPacket = new LoginRequestPacket(LocalDateTime.now().toString(),
          "1234", PlayerType.REMOTE_PLAYER);
      Debug.printMessage("LoginRequestPacket has been sent to the server");
      WrappedPacket wrappedPacket = new WrappedPacket(PacketType.LOGIN_REQUEST_PACKET,
          loginRequestPacket);
      ses.getBasicRemote().sendObject(wrappedPacket);
      int counter = 0;
      while (counter < 20) {
        TimeUnit.SECONDS.sleep((int) Math.floor(Math.random() * 5));

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


