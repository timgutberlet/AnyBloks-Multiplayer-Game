package token;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import org.glassfish.jersey.jackson.JacksonFeature;
import org.junit.jupiter.api.Test;

/**
 * tests whether the restful server allows users to register, and then change their passwords. Note
 * that the test class "runTokenServer" has to run, else there is no server listening!
 *
 * @author tbuscher
 */
public class AccountManagementTest {

  @Test
  public void testUpdateAccount() {
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
      System.out.println("Something went wrong");
      System.out.println(receivedAnswer.getStatus());
      System.out.println(receivedAnswer.getStatusInfo());
    } else {
      System.out.println(receivedAnswer.getStatus());
      System.out.println("The account has been created.");

    }
    assertEquals(200, receivedAnswer.getStatus());
    assertTrue(dbServer.doesUsernameExist(username));
    System.out.println("--------- Below the line the account is updated ------------");

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

    System.out.println(
        "The reponse to the updateRequest has the status:" + receivedUpdateAnswer.getStatus()
            + ": " + receivedUpdateAnswer.getStatusInfo());
    assertEquals(200, receivedUpdateAnswer.getStatus());
    assertEquals(updatedPasswordHash, dbServer.getUserPasswordHash(username));

    System.out.println("--------- Below the line the account is deleted ------------");

    Client deleteClient = ClientBuilder.newBuilder().register(JacksonFeature.class).build();
    DeleteAccountRequestPacket deleteAccountRequestPacket = new DeleteAccountRequestPacket(username,
        updatedPasswordHash);

    WrappedPacket wrappedDeleteAccountRequestPacket = new WrappedPacket(
        PacketType.DELETE_ACCOUNT_REQUEST_PACKET, deleteAccountRequestPacket, username,
        updatedPasswordHash);
    WebTarget targetPathDelete = testClient.target(targetAddress).path("/deleteAccount/");
    Response receivedDeleteAnswer = targetPathDelete.request(MediaType.APPLICATION_JSON)
        .put(Entity.entity(wrappedDeleteAccountRequestPacket, MediaType.APPLICATION_JSON));
    System.out.println(
        "The reponse to the deleteRequest has the status:" + receivedDeleteAnswer.getStatus()
            + ": " + receivedDeleteAnswer.getStatusInfo());
    assertEquals(200, receivedDeleteAnswer.getStatus());
    assertFalse(dbServer.doesUsernameExist(username));


  }

}
