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
    System.out.println("Got here1");

    Server server = new Server(portNumber);
    System.out.println("dindt Got here");

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

  /**
   * Stop jetty instance.
   */
  public void stop() {
    if(server == null || !server.isRunning()) {
      if(LOG.isWarnEnabled()) {
        LOG.warn("No Jetty server running");
      }

      return;
    }

    try {
      server.stop();

      if(LOG.isInfoEnabled()) {
        LOG.info("Stopped Jetty server");
      }
    } catch (Exception e) {
      LOG.error("Could not stop jetty server", e);
    } finally {
      server.destroy();
    }
  }

}