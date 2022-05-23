package net.server;

import java.net.Inet4Address;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import net.transmission.EndpointServer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
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
  private static Server websocketServer;
  private Server restServer;

  /**
   * Starting the server itself.
   *
   * @param portNumber port to use
   */
  public void startRestfulServer(int portNumber) throws Exception {

    String IPAdress = Inet4Address.getLocalHost().getHostAddress();
    URI baseUri = UriBuilder.fromUri("http://" + IPAdress + "/").port(portNumber).build();
    ServerConfig config = new ServerConfig();

    restServer = JettyHttpContainerFactory.createServer(baseUri, config);

    restServer.start();
    restServer.join();

  }

  /**
   * Start websockets on the webserver-
   *
   * @param portNumber bind to specified port
   */
  public Server startWebsocket(int portNumber) throws Exception {

    websocketServer = new Server(portNumber);

    ServletContextHandler context = new ServletContextHandler();
    context.setContextPath("/");

    final ServletHolder defaultHolder = new ServletHolder("default", DefaultServlet.class);
    context.addServlet(defaultHolder, "/");
    websocketServer.setHandler(context);

    // activate websockets
    ServerContainer serverContainer = WebSocketServerContainerInitializer.configureContext(context);

    // add endpoints
    serverContainer.addEndpoint(EndpointServer.class);

    websocketServer.start();

    return websocketServer;
  }

  /**
   * Stop the websocket server.
   */
  public void stopWebsocket() {
    if (websocketServer == null || !websocketServer.isRunning()) {
      if (LOG.isWarnEnabled()) {
        LOG.warn("No Jetty server running");
      }

      return;
    }

    try {
      websocketServer.stop();

      if (LOG.isInfoEnabled()) {
        LOG.info("Stopped Jetty server");
      }
    } catch (Exception e) {
      LOG.error("Could not stop jetty server", e);
    } finally {
      websocketServer.destroy();
    }
  }

  /**
   * Stop the restful server
   */
  public void stopRestful() {
    if (restServer == null || !restServer.isRunning()) {
      if (LOG.isWarnEnabled()) {
        LOG.warn("No Jetty server running");
      }
      return;
    }

    try {
      restServer.stop();

      if (LOG.isInfoEnabled()) {
        LOG.info("Stopped Jetty server");
      }
    } catch (Exception e) {
      LOG.error("Could not stop jetty server", e);
    } finally {
      restServer.destroy();
    }
  }

}