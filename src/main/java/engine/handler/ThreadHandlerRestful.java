package engine.handler;

import game.model.Debug;
import net.server.HostServer;
import net.tests.NoLogging;

/**
 * This Handler runs a thread, to communicate with the server, while also running a smooth Ui
 * parralel. The Basic Thread can look like this Sollte im folgenden dann noch durch weitere
 * Threadhandler erweiter werden, bitte aber lassen, bis eine Funktionierende Version mit Server
 * steht
 *
 * @author tgutberl
 */
public class ThreadHandlerRestful extends Thread {

  private HostServer hostServer;

  /**
   * Constructor for setting the gamession.
   */
  public ThreadHandlerRestful() {
    this.hostServer = null;
  }

  /**
   * Run Method for thread.
   */
  @Override
  public void run() {
    try {
      org.eclipse.jetty.util.log.Log.setLog(new NoLogging());
      this.hostServer = new HostServer();
      hostServer.startRestfulServer(8082);
    } catch (Exception e) {
      e.printStackTrace();
    }
    while (!Thread.currentThread().isInterrupted()) {
    }
    Debug.printMessage("Restful Server Interrupted");
    hostServer.stopRestful();
  }
}
