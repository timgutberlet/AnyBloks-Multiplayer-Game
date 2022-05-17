package net.tests.game;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

import game.model.Debug;
import game.model.gamemodes.GMClassic;
import game.model.gamemodes.GameMode;
import game.model.player.Player;
import game.model.player.PlayerType;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Base64;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import javax.websocket.ClientEndpointConfig;
import javax.websocket.ClientEndpointConfig.Configurator;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.CreateAccountRequestPacket;
import net.packet.account.LoginRequestPacket;
import net.packet.chat.ChatMessagePacket;
import net.packet.game.InitGamePacket;
import net.packet.game.InitSessionPacket;
import net.server.HashingHandler;
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

    //org.eclipse.jetty.util.log.Log.setLog(new NoLogging());

    final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    Player localPlayer = new Player("LocalPlayer", PlayerType.AI_EASY);
    EndpointClient client = new EndpointClient(localPlayer);

    Session ses ;

    try {
      //Create Config that adds a header with username + password
      //Thereby adhering to Basic Authentication
      //ClientEndpointConfig.Builder configBuilder = ClientEndpointConfig.Builder.create();
      //configBuilder.configurator(new Configurator(){
        /**
         * Method implementing the basic authentication
         *
         * @param headers to be sent with every packet
         */
      //public void beforeRequest(java.util.Map<String, List<String>> headers){
      //    String username = "remotePlayer";
      //    //TODO: get actual password + username
      //    String passwordHash = "remoteSessionPassword";
      //    String authString = Base64.getEncoder().encodeToString(("Basic " + username + ":" + passwordHash).getBytes());
      //    List<String> authList = new ArrayList<String>();
      //    authList.add(authString);

      //    headers.put("Authentication", authList);
       // }
      //});
      //ClientEndpointConfig endpointClientConfig = configBuilder.build();

      //ses = container.connectToServer(client, endpointClientConfig, URI.create("ws://134.155.206.121:8081/packet"));
      //ses = container.connectToServer(client, URI.create("ws://localhost:8081/packet"));

      //play on remote machine (get IP via testInetAdress)

     // ses = container.connectToServer(client, URI.create("ws://192.168.178.27:8081/packet"));
      //ses = container.connectToServer(client, URI.create("ws://134.155.205.170:8081/packet"));


      String IPAdress = Inet4Address.getLocalHost().getHostAddress();

      ses = container.connectToServer(client, URI.create("ws://" + IPAdress + ":8081/packet"));

      //Init session
      InitSessionPacket initSessionPacket = new InitSessionPacket();
      WrappedPacket wrappedPacket = new WrappedPacket(PacketType.INIT_SESSION_PACKET,
          initSessionPacket);
      ses.getBasicRemote().sendObject(wrappedPacket);

      //Create Account
      String passwordHash = HashingHandler.sha256encode("123456");
      CreateAccountRequestPacket createAccReq = new CreateAccountRequestPacket(
          localPlayer.getUsername(),
          passwordHash);
      wrappedPacket = new WrappedPacket(PacketType.CREATE_ACCOUNT_REQUEST_PACKET,
          createAccReq);
      //... and send it
      client.sendToServer(wrappedPacket);

      //Sleep so updates can be made in DB
      try {
        TimeUnit.MILLISECONDS.sleep(5000);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      //Login
      LoginRequestPacket loginRequestPacket = new LoginRequestPacket(localPlayer.getUsername(),
          "1234", localPlayer.getType());
      Debug.printMessage("LoginRequestPacket has been sent to the server");
      wrappedPacket = new WrappedPacket(PacketType.LOGIN_REQUEST_PACKET,
          loginRequestPacket);
      ses.getBasicRemote().sendObject(wrappedPacket);

      TimeUnit.SECONDS.sleep(2);
      //Start game
      LinkedList<GameMode> gameModeLinkedList = new LinkedList<>();
      gameModeLinkedList.add(new GMClassic());
      gameModeLinkedList.add(new GMClassic());
      InitGamePacket initGamePacket = new InitGamePacket(gameModeLinkedList);
      wrappedPacket = new WrappedPacket(PacketType.INIT_GAME_PACKET, initGamePacket);
      ses.getBasicRemote().sendObject(wrappedPacket);
      Debug.printMessage("InitGamePacket has been sent to the server");

      int counter = 0;
      while (counter < 0) {
        TimeUnit.SECONDS.sleep(1);

        //wrappedPacket = new WrappedPacket(PacketType.CHAT_MESSAGE_PACKET, chatMessagePacket);

        ses.getBasicRemote().sendObject(wrappedPacket);
        Debug.printMessage("Chat Message sent");
        counter++;
      }
      //ses.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}


