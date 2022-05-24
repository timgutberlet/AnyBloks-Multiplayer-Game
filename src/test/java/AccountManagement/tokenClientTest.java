package AccountManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import engine.handler.ThreadHandlerRestful;
import game.model.Debug;
import java.util.concurrent.TimeUnit;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.RestfulLoginPacket;
import net.server.DbServer;
import net.server.HostServer;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.jupiter.api.Test;

/**
 * Testing whether a client can fetch a token.
 *
 * @author tbuscher
 */
public class tokenClientTest {


  @Test
  public void testTokenReception() {
    //Cant start this thread in beforeAll since beforeAll needs to be static
    ThreadHandlerRestful threadHandlerRestful = new ThreadHandlerRestful();
    threadHandlerRestful.start();

    try {
      TimeUnit.MILLISECONDS.sleep(1500);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    DbServer dbServer = null;
    try {
      dbServer = DbServer.getInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
    assertNotNull(dbServer);

    dbServer.prepareTokenGenerationTest();
    dbServer.prepareTokenGenerationTest();

    Client testClient = ClientBuilder.newBuilder().register(JacksonFeature.class).build();

    String Ip = "localhost";
    String targetAddress = "http://" + Ip + ":8082/";

    //Sending 123456 as a password"Hash"is fine, as the db is prepared exactly for this
    RestfulLoginPacket restfulLoginPacket = new RestfulLoginPacket("testuser",
        "123456");
    WrappedPacket toSend = new WrappedPacket(PacketType.RESTFUL_LOGIN_PACKET, restfulLoginPacket);

    WebTarget targetPath = testClient.target(targetAddress).path("/authentication/");
    Response receivedToken = targetPath.request(MediaType.APPLICATION_JSON)
        .put(Entity.entity(toSend, MediaType.APPLICATION_JSON));

    Debug.printMessage("--------------------------------------------------------------------");
    Debug.printMessage(
        "The status of the request to get the token is: " + receivedToken.getStatus());
    //This only worked if the status of the response is 200 for "Ok"
    assertEquals(200, receivedToken.getStatus());
    String token = receivedToken.readEntity(String.class);

    //The token cant be null
    assertNotNull(token);
    //But has to be of the length 103 in every case
    assertEquals(103, token.length());
    Debug.printMessage("The generated token is: " + token);

    threadHandlerRestful.interrupt();
  }


}
