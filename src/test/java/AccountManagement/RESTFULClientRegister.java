package AccountManagement;

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
import net.server.HashingHandler;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.jupiter.api.Test;

/**
 * @author tbuscher
 */
public class RESTFULClientRegister {

  @Test
  public void testCreateAccount() {
    Client testClient = ClientBuilder.newBuilder().register(JacksonFeature.class).build();

    String password = HashingHandler.sha256encode("123456");
    CreateAccountRequestPacket carp = new CreateAccountRequestPacket("testuser", password);
    WrappedPacket wrappedPacket = new WrappedPacket(PacketType.CREATE_ACCOUNT_REQUEST_PACKET,
        carp);

    String Ip = "localhost";
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
      Debug.printMessage("Everything worked");

    }
    Debug.printMessage("---------");


  }


}
