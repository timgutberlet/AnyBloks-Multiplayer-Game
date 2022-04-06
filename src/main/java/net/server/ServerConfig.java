package net.server;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.ExtendedConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import net.LoggingRequestFilter;
import javax.ws.rs.ApplicationPath;

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
