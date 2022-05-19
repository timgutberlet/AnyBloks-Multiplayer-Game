package token;

import game.model.player.PlayerType;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.LoginRequestPacket;
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

    LoginRequestPacket loginRequestPacket = new LoginRequestPacket("testuser"
        , "123455", PlayerType.REMOTE_PLAYER);
    WrappedPacket toSend = new WrappedPacket(PacketType.LOGIN_REQUEST_PACKET, loginRequestPacket);

    WebTarget targetPath = testClient.target(targetAddress).path("/authentication/");
    Response receivedToken = targetPath.request(MediaType.APPLICATION_JSON)
        .put(Entity.entity(toSend, MediaType.APPLICATION_JSON));

    String token = receivedToken.readEntity(String.class);

    System.out.println("---------");
    System.out.println(token);

    System.out.println(receivedToken.getStatus());
    System.out.println(receivedToken.getMediaType());
  }


}
