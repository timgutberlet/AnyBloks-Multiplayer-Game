package net.AuthRessources;

import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.UpdateAccountRequestPacket;
import net.server.DbServer;

/**
 * Class that provides the option to change the password of an existing account.
 *
 * @author tbuscher
 */
@Path(("/updateAccount/"))
public class UpdateAccountRessource {

  /**
   * This method registers and processes an update account request packet.
   * @param wrappedPacket
   * @return response on the update account request
   */
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)

  public Response update(WrappedPacket wrappedPacket) {
    System.out.println("Hi from updateAcc method");
    String errorMessage = "";

    try {
      if (wrappedPacket.getPacketType() != PacketType.UPDATE_ACCOUNT_REQUEST_PACKET) {
        errorMessage = "This packet is not of the correct type";
        throw new Exception();
      } else {

        UpdateAccountRequestPacket updateAccountRequestPacket = (UpdateAccountRequestPacket) wrappedPacket.getPacket();
        String username = updateAccountRequestPacket.getUsername();
        String passwordHash = updateAccountRequestPacket.getPasswordHash();
        String updatedPasswordHash = updateAccountRequestPacket.getUpdatedPasswordHash();
        System.out.println("oldPW: " + passwordHash);
        System.out.println("newPW: " + updatedPasswordHash);
        System.out.println("username : " + username);

        DbServer dbServer = DbServer.getInstance();
        //Make sure the is a user with that username
        if (!dbServer.doesUsernameExist(username)) {
          throw new Exception("The provided credentials appear to be false.");
        }

        //Ensure the user entered the right password
        if (!dbServer.getUserPasswordHash(username).equals(passwordHash)) {
          errorMessage = "The provided credentials appear to be false";
          throw new Exception();
        } else {
          //Save changes in DB with updatePassword()
          if (dbServer.updatePassword(username, updatedPasswordHash)) {
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

