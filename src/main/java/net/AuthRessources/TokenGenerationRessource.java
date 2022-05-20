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
import net.packet.account.RestfulLoginPacket;
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
      String username ="";
      String passwordHash = "";
      RestfulLoginPacket restfulLoginPacket = null;
      if (wrappedPacket.getPacketType() != PacketType.RESTFUL_LOGIN_PACKET) {
        throw new Exception("This packet is not of the correct type");
      }

      System.out.println(wrappedPacket.getPacket());
      restfulLoginPacket = (RestfulLoginPacket) wrappedPacket.getPacket();
      username = restfulLoginPacket.getUsername();
      passwordHash = restfulLoginPacket.getPasswordHash();


      System.out.println(restfulLoginPacket.getUsername() + " " + restfulLoginPacket.getPasswordHash());

      // Authenticate user with db
      authenticate(username, passwordHash);

      // Generate a token
      String token = issueToken(username);

      // Add the token to the positive response
      return Response.ok(token).build();

    } catch (Exception e) {
      e.printStackTrace();
      return Response.status(Response.Status.FORBIDDEN).build();
    }
  }

  private void authenticate(String username, String password) throws Exception {
    DbServer dbServer = DbServer.getInstance();
    System.out.println(dbServer.doesUsernameExist(username));
    System.out.println(!dbServer.doesUsernameExist(username));
    if (!(dbServer.doesUsernameExist(username))) {
      throw new Exception("The username doesn't exist!");
    }
    String dbpasswordHash = dbServer.getUserPasswordHash(username);

    System.out.println(username + " " + password);
    System.out.println(username + " " + dbpasswordHash);
    if (!password.equals(dbpasswordHash)) {
      throw new Exception("The credentials are wrong!");
    }
  }

  public String issueToken(String username) {
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
    dbServer.insertAuthToken(username, token);

    return token;
  }


}

