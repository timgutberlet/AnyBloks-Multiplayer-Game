package net.tests.game;

import game.model.Debug;
import java.net.InetAddress;
import java.util.concurrent.TimeUnit;
import net.server.HostServer;
import net.tests.NoLogging;

/**
 * Tests only
 *
 * @author tgeilen
 */
public class testGameServer {

  static HostServer hostServer = new HostServer();


  public static void main(String[] args) {
    try {
      //org.eclipse.jetty.util.log.Log.setLog(new NoLogging());
      InetAddress.getLocalHost().toString();
      hostServer.startWebsocket(8081);
      Debug.printMessage("[testChatServer] Server is running");
      //TimeUnit.SECONDS.sleep(3);
    } catch (Exception e) {
      e.printStackTrace();
    }

    //hostServer.stop();
    //Debug.printMessage("[testChatServer] Server has stopped");
  }


}

