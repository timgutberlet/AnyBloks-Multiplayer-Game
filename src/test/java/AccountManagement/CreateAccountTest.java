package AccountManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import engine.handler.ThreadHandlerRestful;
import game.model.Debug;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.CreateAccountRequestPacket;
import net.server.DbServer;
import net.server.HashingHandler;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Tests if an account can be created via a Restful connection.
 *
 * @author tbuscher
 */
public class CreateAccountTest {
  ThreadHandlerRestful threadHandlerRestful;


  @Test
  public void testCreateAccount(){
    //Cant start this thread in beforeAll since beforeAll needs to be static
    threadHandlerRestful = new ThreadHandlerRestful();
    threadHandlerRestful.start();

    DbServer dbServer = null;
    try {
      dbServer = DbServer.getInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
    assertNotNull(dbServer);


    Client testClient = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
    String username = "testuserUpdate1";
    String password = "123456";
    String Ip = "localhost";

    String passwordHash = HashingHandler.sha256encode(password);
    CreateAccountRequestPacket carp = new CreateAccountRequestPacket(username, passwordHash);
    WrappedPacket wrappedPacket = new WrappedPacket(PacketType.CREATE_ACCOUNT_REQUEST_PACKET,
        carp);

    String targetAddress = "http://" + Ip + ":8082/";
    WebTarget targetPath = testClient.target(targetAddress).path("/register/");
    Response receivedAnswer = targetPath.request(MediaType.APPLICATION_JSON)
        .put(Entity.entity(wrappedPacket, MediaType.APPLICATION_JSON));

    if (receivedAnswer.getStatus() != 200) {
      Debug.printMessage("Something went wrong");
      Debug.printMessage(""+receivedAnswer.getStatus());
      Debug.printMessage(""+receivedAnswer.getStatusInfo());
    } else {
      Debug.printMessage(""+receivedAnswer.getStatus());
      Debug.printMessage("The account has been created.");

    }
    assertEquals(200, receivedAnswer.getStatus());
    assertTrue(dbServer.doesUsernameExist(username));

    threadHandlerRestful.interrupt();

  }

}
