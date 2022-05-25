package net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import game.model.Debug;
import game.model.chat.Chat;
import game.model.chat.ChatMessage;
import game.model.player.Player;
import game.model.player.PlayerType;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.LoginRequestPacket;
import net.packet.chat.ChatMessagePacket;
import net.server.DbServer;
import net.server.HostServer;
import net.transmission.EndpointClient;
import org.junit.jupiter.api.Test;

/**
 * Test whether the server correctly broadcasts chatMessages.
 *
 * @author tbuscher
 */
public class ChatTest {

  static HostServer hostServer = new HostServer();

  /**
   * Test method to ensure the chat runs as intended.
   */
  @Test
  public void testChat() {
    //Starting a websocketServer
    try {
      hostServer.startWebsocket(8081);
      Debug.printMessage("[testChatServer] Server is running");
      TimeUnit.MILLISECONDS.sleep(1000);
    } catch (Exception e) {
      e.printStackTrace();
    }

    //preparing accounts to sign into
    DbServer dbServer = null;
    try {
      dbServer = DbServer.getInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
    assertNotNull(dbServer);

    String[] users = new String[]{"chatUser1", "chatUser2"};
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<EndpointClient> endpointClients = new ArrayList<>();
    ArrayList<Session> sessions = new ArrayList<>();
    String passwordHash = "unhashedTestPassword";
    String token = "TestToken";

    for (String user : users) {
      //Make sure the accounts are in the database
      dbServer.deleteAccount(user);
      dbServer.newAccount(user, passwordHash);
      dbServer.insertAuthToken(user, token);
      Player player = new Player(user, PlayerType.REMOTE_PLAYER);
      players.add(player);
      EndpointClient endpointClient = new EndpointClient(player);
      endpointClient.getGameSession().setAuthToken(token);
      endpointClients.add(endpointClient);
      //Connect the endpoints to the server
      try {
        Session session = ContainerProvider.getWebSocketContainer()
            .connectToServer(endpointClient, URI.create("ws://localhost:8081/packet"));
      } catch (DeploymentException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }

      //Log in the accounts that have been connected
      WrappedPacket wrappedLoginPacket = new WrappedPacket(PacketType.LOGIN_REQUEST_PACKET,
          new LoginRequestPacket(user, token, PlayerType.REMOTE_PLAYER), user, token);
      endpointClient.sendToServer(wrappedLoginPacket);

    }
      Chat chat = new Chat();
      chat.addMessage(new ChatMessage("Player1","Hello World"));

      endpointClients.get(0).getGameSession().getClientHandler().broadcastChatMessage(chat);

    try {
      TimeUnit.MILLISECONDS.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    Chat chat2 = endpointClients.get(1).getGameSession().getChat();



      assertEquals(1,chat2.getChatMessages().size());




    //Stopping the websocketServer again
    hostServer.stopWebsocket();

    try {
      TimeUnit.MILLISECONDS.sleep(1000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}
