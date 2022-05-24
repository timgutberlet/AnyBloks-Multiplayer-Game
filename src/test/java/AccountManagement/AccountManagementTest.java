package AccountManagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import net.packet.account.CreateAccountRequestPacket;
import net.packet.account.DeleteAccountRequestPacket;
import net.packet.account.UpdateAccountRequestPacket;
import net.server.DbServer;
import net.server.HashingHandler;
import net.server.HostServer;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.jupiter.api.Test;

/**
 * tests whether the restful server allows users to register, and then change their passwords.
 *
 * @author tbuscher
 */
public class AccountManagementTest {



  @Test
  public void testUpdateAccount() {
    //Cant start this thread in beforeAll since beforeAll needs to be static
    ThreadHandlerRestful threadHandlerRestful = new ThreadHandlerRestful();
    threadHandlerRestful.start();

    try {
      TimeUnit.MILLISECONDS.sleep(2500);
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
    dbServer.prepareRemoteAccountManagementTest();


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
      Debug.printMessage("Something went wrong during the creation of the account");
      Debug.printMessage("The status of the request is: " + receivedAnswer.getStatus());
    } else {
      Debug.printMessage("The status of the request is: " + receivedAnswer.getStatus());
      Debug.printMessage("The account has been created.");

    }
    assertEquals(200, receivedAnswer.getStatus());
    assertTrue(dbServer.doesUsernameExist(username));
    Debug.printMessage("--------- Below the line the account is updated ------------");

    Client updateClient = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
    String updatedPassword = "1234567890";
    String updatedPasswordHash = HashingHandler.sha256encode(updatedPassword);

    UpdateAccountRequestPacket updateAccountRequestPacket = new UpdateAccountRequestPacket(
        passwordHash, username, updatedPasswordHash);
    WrappedPacket wrappedUpdateAccountRequestPacket = new WrappedPacket(
        PacketType.UPDATE_ACCOUNT_REQUEST_PACKET, updateAccountRequestPacket, username,
        passwordHash);
    WebTarget targetPathUpdate = testClient.target(targetAddress).path("/updateAccount/");
    Response receivedUpdateAnswer = targetPathUpdate.request(MediaType.APPLICATION_JSON)
        .put(Entity.entity(wrappedUpdateAccountRequestPacket, MediaType.APPLICATION_JSON));

    Debug.printMessage(
        "The response to the updateRequest has the status:" + receivedUpdateAnswer.getStatus()
            + ": " + receivedUpdateAnswer.getStatusInfo());
    assertEquals(200, receivedUpdateAnswer.getStatus());
    assertEquals(updatedPasswordHash, dbServer.getUserPasswordHash(username));

    Debug.printMessage("--------- Below the line the account is deleted ------------");

    Client deleteClient = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
    DeleteAccountRequestPacket deleteAccountRequestPacket = new DeleteAccountRequestPacket(username,
        updatedPasswordHash);

    WrappedPacket wrappedDeleteAccountRequestPacket = new WrappedPacket(
        PacketType.DELETE_ACCOUNT_REQUEST_PACKET, deleteAccountRequestPacket, username,
        updatedPasswordHash);
    WebTarget targetPathDelete = testClient.target(targetAddress).path("/deleteAccount/");
    Response receivedDeleteAnswer = targetPathDelete.request(MediaType.APPLICATION_JSON)
        .put(Entity.entity(wrappedDeleteAccountRequestPacket, MediaType.APPLICATION_JSON));
    Debug.printMessage(
        "The reponse to the deleteRequest has the status:" + receivedDeleteAnswer.getStatus()
            + ": " + receivedDeleteAnswer.getStatusInfo());
    assertEquals(200, receivedDeleteAnswer.getStatus());
    assertFalse(dbServer.doesUsernameExist(username));

    threadHandlerRestful.interrupt();
  }

}
