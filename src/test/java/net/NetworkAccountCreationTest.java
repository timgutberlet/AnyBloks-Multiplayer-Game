package net;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.CreateAccountRequestPacket;
import net.server.DbServer;
import net.server.HashingHandler;
import net.server.HostServer;
import net.transmission.EndpointClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Class testing the creation of a new user account over the network. This includes: proper
 * passwordHashing, proper passwordHash coming out of the DB, the user account being in the DB.
 *
 * @author tbuscher
 */
public class NetworkAccountCreationTest {

  public static HostServer hostServer;

  /**
   * Starting the server before testing.
   */
  @BeforeAll
  public static void beforeAll() {
    hostServer = new HostServer();
    try {
      hostServer.startWebsocket(8085);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Shutting the server down after the test.
   */
  @AfterAll
  public static void afterAll() {
    hostServer.stop();

  }

  @Test
  public void testNetworkAccountCreation() {
    //Clear out the database
    DbServer dbServer = null;
    try {
      dbServer = DbServer.getInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
    dbServer.resetDb();

    //Set up a client...
    final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    Player localPlayer = new Player("LocalPlayer", PlayerType.AI_EASY);
    EndpointClient client = new EndpointClient(localPlayer);
    Session ses = null;
    try {
      //... and connect it to the server
      ses = container.connectToServer(client, URI.create("ws://localhost:8085/packet"));
      //Set up a CreateAccountRequestPacket...
      String passwordHash = HashingHandler.sha256encode("123456");
      CreateAccountRequestPacket createAccReq = new CreateAccountRequestPacket("TestUsername",
          passwordHash);
      WrappedPacket wrappedPacket = new WrappedPacket(PacketType.CREATE_ACCOUNT_REQUEST_PACKET,
          createAccReq);
      //... and send it
      ses.getBasicRemote().sendObject(wrappedPacket);
    } catch (DeploymentException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (EncodeException e) {
      e.printStackTrace();
    }

    //Sleep so updates can be made in DB
    try {
      TimeUnit.MILLISECONDS.sleep(500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    //If the CreateAccountRequestPacket was properly handled there will be an account
    // with the username "TestUsername":
    boolean doesUsernameExist = dbServer.doesUsernameExist("TestUsername");
    assertTrue(doesUsernameExist);

    //The Hash for 123456 in sha256 is:
    //"8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92"
    //This should be set in the DB as the passwordHash for user "TestUsername"
    assertEquals("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92",
        dbServer.getUserPasswordHash("TestUsername"));
  }


}
