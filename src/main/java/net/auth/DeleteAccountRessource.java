package net.auth;

import game.model.Debug;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.DeleteAccountRequestPacket;
import net.server.DbServer;

/**
 * Class that enables users to delete a remote account.
 *
 * @author tbuscher
 */
@Path(("/deleteAccount/"))
public class DeleteAccountRessource {

  /**
   * This method registers and processes a delete account request packet.
   *
   * @param wrappedPacket wrappedPacket
   * @return response on the delete account request
   */
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)

  public Response update(WrappedPacket wrappedPacket) {
    Debug.printMessage("Hi from deleteAcc method");
    String errorMessage = "";
    try {
      if (wrappedPacket.getPacketType() != PacketType.DELETE_ACCOUNT_REQUEST_PACKET) {
        errorMessage = "This packet is not of the correct type";
        throw new Exception();
      } else {

        DeleteAccountRequestPacket deleteAccountRequestPacket =
            (DeleteAccountRequestPacket) wrappedPacket.getPacket();
        String username = deleteAccountRequestPacket.getUsername();
        String passwordHash = deleteAccountRequestPacket.getPasswordHash();

        DbServer dbServer = DbServer.getInstance();
        //Make sure there is a user with that username
        if (!dbServer.doesUsernameExist(username)) {
          throw new Exception("The provided credentials appear to be false.");
        }
        //Ensure the user entered the right password
        if (!dbServer.getUserPasswordHash(username).equals(passwordHash)) {
          errorMessage = "The provided credentials appear to be false";
          throw new Exception();
        } else {
          //With the proper credentials the user is deleted.
          if (dbServer.deleteAccount(username)) {
            // Return ok is the update works.
            return Response.ok().build();
          } else {
            throw new Exception("An error occurred, please try again.");
          }
        }


      }
    } catch (Exception e) {
      //Set response to 403 forbidden, and explain why
      Response response = Response.status(403, errorMessage).build();
      return response;
    }
  }

}

