package net.AuthRessources;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import net.packet.abstr.PacketType;
import net.packet.abstr.WrappedPacket;
import net.packet.account.LoginRequestPacket;
import net.server.DbServer;

/**
 * Class to be used in REST Server to provide tokens
 *
 * @author tbuscher
 */
@Path(("/authentication/"))
public class TokenGenerationRessource {

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)

  public Response authenticateUser(WrappedPacket wrappedPacket) {
    System.out.println("Hi from auth method");

    try {
      if (wrappedPacket.getPacketType() != PacketType.LOGIN_REQUEST_PACKET) {
        throw new Exception("This packet is not of the correct type");
      }

      LoginRequestPacket loginPacket = (LoginRequestPacket) wrappedPacket.getPacket();
      String username = loginPacket.getUsername();
      String passwordHash = loginPacket.getToken();

      // Authenticate user with db
      authenticate(username, passwordHash);

      // Generate a token
      String token = issueToken(username);

      // Add the token to the positive response
      return Response.ok(token).build();

    } catch (Exception e) {
      return Response.status(Response.Status.FORBIDDEN).build();
    }
  }

  private void authenticate(String username, String password) throws Exception {
    DbServer dbServer = DbServer.getInstance();
    if (!dbServer.doesUsernameExist(username)) {
      throw new Exception("The username doesn't exist!");
    }
    String dbpasswordHash = dbServer.getUserPasswordHash(username);
    if (!password.equals(dbpasswordHash)) {
      throw new Exception("The credentials are wrong!");
    }
  }

  private String issueToken(String username) {
    boolean passed = true;

    //delete any old authTokens that might exist for the user
    DbServer dbServer = null;
    try {
      dbServer = DbServer.getInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
    if (dbServer.doesUserHaveAuthToken(username)) {
      dbServer.deleteAuthToken(username);
    }
    String token = "";
    try {
      Algorithm algorithm = Algorithm.HMAC256("notblocks3");
      token = JWT.create()
          .withIssuer("server")
          .sign(algorithm);

    } catch (JWTCreationException exception) {
      passed = false;
    }

    //Insert the new token into the DB
    dbServer.testAuthToken(username, token);

    return token;
  }


}

