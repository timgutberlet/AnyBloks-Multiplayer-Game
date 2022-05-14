package net.server;

import javax.ws.rs.ApplicationPath;
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
    // activate the request filter
    register(LoggingRequestFilter.class);    // make JSON usable
    register(JacksonFeature.class);

  }

}
