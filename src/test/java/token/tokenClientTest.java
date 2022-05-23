package token;

import game.model.Debug;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.RestfulLoginPacket;
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
    Client testClient = ClientBuilder.newBuilder().register(JacksonFeature.class).build();

    String Ip = "localhost";
    String targetAddress = "http://" + Ip + ":8082/";

    RestfulLoginPacket restfulLoginPacket = new RestfulLoginPacket("testuser",
        "123456"); //Remember to send actually hashed PW!
    WrappedPacket toSend = new WrappedPacket(PacketType.RESTFUL_LOGIN_PACKET, restfulLoginPacket);

    WebTarget targetPath = testClient.target(targetAddress).path("/authentication/");
    Response receivedToken = targetPath.request(MediaType.APPLICATION_JSON)
        .put(Entity.entity(toSend, MediaType.APPLICATION_JSON));

    String token = receivedToken.readEntity(String.class);

    Debug.printMessage("---------");
    Debug.printMessage(token);

    Debug.printMessage(""+receivedToken.getStatus());
    Debug.printMessage(""+receivedToken.getMediaType());
  }


}
