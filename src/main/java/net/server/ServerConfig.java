package net.server;

import java.util.HashMap;
import javax.ws.rs.ApplicationPath;
import net.AuthRessources.AccountRegisterRessource;
import net.AuthRessources.DeleteAccountRessource;
import net.AuthRessources.TokenGenerationRessource;
import net.AuthRessources.UpdateAccountRessource;
import net.LoggingRequestFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * default config for the HostServer.
 *
 * @author tbuscher
 */
@ApplicationPath("/")
public class ServerConfig extends ResourceConfig {

  /**
   * Config used for the server.
   */

  public ServerConfig() {
    // activate the request filters
    //register(AuthenticationFilter.class);
    HashMap<String, Object> propertiesToSet = new HashMap<>();
    propertiesToSet.put("jersey.config.server.wadl.disableWadl", "true");
    addProperties(propertiesToSet);

    //Create tokens on login
    register(TokenGenerationRessource.class);

    //Create Account in DB
    register(AccountRegisterRessource.class);

    //Change Account in DB
    register(UpdateAccountRessource.class);

    //Delete Account in DB
    register(DeleteAccountRessource.class);

    //Logging
    register(LoggingRequestFilter.class);

    // make JSON usable
    register(JacksonFeature.class);

  }

}
