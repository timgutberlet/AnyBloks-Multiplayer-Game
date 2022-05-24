package net.AuthRessources;

import game.model.Debug;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.CreateAccountRequestPacket;
import net.server.DbServer;

/**
 * Class to be used in REST Server to create Accounts.
 *
 * @author tbuscher
 */
@Path(("/register/"))
public class AccountRegisterRessource {

  /**
   * This method registers and processes a create account request packet.
   *
   * @param wrappedPacket
   * @return response on the create account request
   */
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)

  public Response register(WrappedPacket wrappedPacket) {
    Debug.printMessage("Hi from reg method");
    String errorMessage = "";
    try {
      String username = "";
      String passwordHash = "";
      if (wrappedPacket.getPacketType() != PacketType.CREATE_ACCOUNT_REQUEST_PACKET) {
        errorMessage = "This packet is not of the correct type";
        throw new Exception();
      }

      CreateAccountRequestPacket createAccountRequestPacket = (CreateAccountRequestPacket) wrappedPacket.getPacket();
      username = createAccountRequestPacket.getUsername();
      passwordHash = createAccountRequestPacket.getPasswordHash();

      DbServer dbServer = DbServer.getInstance();
      //Make sure username is not used yet
      if (dbServer.doesUsernameExist(username)) {
        errorMessage = "The username already exists, please use another one!";
        throw new Exception();
      }

      //Create Account in DB
      dbServer.newAccount(username, passwordHash);

      // Return ok
      return Response.ok().build();

    } catch (Exception e) {
      //Set response to 403 forbidden, and explain why
      Response response = Response.status(403, errorMessage).build();
      return response;
    }
  }

}

