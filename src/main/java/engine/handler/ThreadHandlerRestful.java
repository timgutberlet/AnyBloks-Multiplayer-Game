package engine.handler;

import game.model.GameSession;
import net.server.HostServer;
import net.tests.NoLogging;

/**
 * @author tgutberl
 * The Basic Thread can look like this
 *
 * Sollte im folgenden dann noch durch weitere Threadhandler erweiter werden, bitte aber lassen, bis eine Funktionierende Version mit Server steht
 */
public class ThreadHandlerRestful extends Thread{

  private HostServer hostServer;

  public ThreadHandlerRestful(){
    this.hostServer = null;
  }
  @Override
  public void run() {
    try {
      org.eclipse.jetty.util.log.Log.setLog(new NoLogging());
      this.hostServer = new HostServer();
      hostServer.startRestfulServer(8082);
    } catch (Exception e) {
      e.printStackTrace();
    }
    while (!Thread.currentThread().isInterrupted()){
    }
    System.out.println("Restful Server Interrupted");
    hostServer.stop();
  }
}
