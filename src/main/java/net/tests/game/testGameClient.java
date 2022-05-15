package net.tests.game;

import game.model.Debug;
import game.model.gamemodes.GMClassic;
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
import net.packet.game.InitGamePacket;
import net.packet.game.InitSessionPacket;
import net.tests.NoLogging;
import net.transmission.EndpointClient;

/**
 * Tests only
 *
 * @author tgeilen
 */
public class testGameClient {


  public testGameClient() {

  }


  public static void main(String[] args) {

    org.eclipse.jetty.util.log.Log.setLog(new NoLogging());
    ChatMessagePacket chatMessagePacket = new ChatMessagePacket(
        LocalDateTime.now() + " Hello World", "user1");
    //WrappedPacket wrappedPacket = new WrappedPacket(PacketType.CHAT_MESSAGE_PACKET, chatMessagePacket);

    final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    Player localPlayer = new Player("LocalPlayer", PlayerType.AI_EASY);
    EndpointClient client = new EndpointClient(localPlayer);

    Session ses = null;

    try {
      ses = container.connectToServer(client, URI.create("ws://localhost:8081/packet"));

      //Init session
      InitSessionPacket initSessionPacket = new InitSessionPacket();
      WrappedPacket wrappedPacket = new WrappedPacket(PacketType.INIT_SESSION_PACKET,
          initSessionPacket);
      ses.getBasicRemote().sendObject(wrappedPacket);

      //Login
      LoginRequestPacket loginRequestPacket = new LoginRequestPacket(localPlayer.getUsername(),
          "1234");
      Debug.printMessage("LoginRequestPacket has been sent to the server");
      wrappedPacket = new WrappedPacket(PacketType.LOGIN_REQUEST_PACKET,
          loginRequestPacket);
      ses.getBasicRemote().sendObject(wrappedPacket);

      TimeUnit.SECONDS.sleep(2);
      //Start game
      InitGamePacket initGamePacket = new InitGamePacket(new GMClassic());
      wrappedPacket = new WrappedPacket(PacketType.INIT_GAME_PACKET, initGamePacket);
      ses.getBasicRemote().sendObject(wrappedPacket);
      Debug.printMessage("InitGamePacket has been sent to the server");

      int counter = 0;
      while (counter < 0) {
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


