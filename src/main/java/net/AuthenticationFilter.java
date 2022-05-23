package net;

import game.model.Debug;
import java.util.Arrays;
import java.util.Base64;
import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * @author tbuscher
 */
@Priority(Priorities.AUTHENTICATION)
public class AuthenticationFilter implements ContainerRequestFilter {

  /**
   * Check requests for proper Authentication. Throws exception.
   *
   * @param containerRequestContext {@link ContainerRequestFilter}
   */
  @Override
  public void filter(ContainerRequestContext containerRequestContext) {
    // get headers
    MultivaluedMap<String, String> headers = containerRequestContext.getHeaders();
    Debug.printMessage("" + headers);
    String encodedAuth = headers.getFirst("Authentication");
    String authString = Arrays.toString(Base64.getDecoder().decode(encodedAuth));
    Debug.printMessage("DECODED AUTH");
    Debug.printMessage(authString);
    Debug.printMessage("Hi from Auth####");
    containerRequestContext.abortWith(
        Response.status(Status.UNAUTHORIZED).entity("I dont like that PW").build());
  }

}
