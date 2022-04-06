package net.server;

import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import net.transmission.EndpointServer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.jsr356.ClientContainer;
import org.eclipse.jetty.websocket.jsr356.server.ServerContainer;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Class that starts and activates a server for a game host.
 *
 * @author tbuscher
 */

public class HostServer {

  private static final Logger LOG = LoggerFactory.getLogger(HostServer.class);

  private Server server;

  /**
   * Starting the server itself.
   *
   * @param portNumber port to use
   */
  public void startServer(int portNumber) throws Exception {

    URI baseUri = UriBuilder.fromUri("http://localhost/").port(portNumber).build();
    ServerConfig config = new ServerConfig();

    server = JettyHttpContainerFactory.createServer(baseUri, config);

    server.start();
    server.join();

  }

  /**
   * Start websockets on the webserver
   *
   * @param portNumber bind to specified port
   */
  public void startWebsocket(int portNumber) throws Exception {
    Server server = new Server(portNumber);

    ServletContextHandler context = new ServletContextHandler();
    context.setContextPath("/");

    final ServletHolder defaultHolder = new ServletHolder("default", DefaultServlet.class);
    context.addServlet(defaultHolder, "/");
    server.setHandler(context);

    // activate websockets
    ServerContainer serverContainer = WebSocketServerContainerInitializer.configureContext(context);

    // add endpoints
    serverContainer.addEndpoint(EndpointServer.class);

    server.start();
  }


}