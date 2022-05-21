package engine.handler;

import game.model.GameSession;

/**
 * @author tgutberl The Basic Thread can look like this
 * <p>
 * Sollte im folgenden dann noch durch weitere Threadhandler erweiter werden, bitte aber lassen, bis
 * eine Funktionierende Version mit Server steht
 */
public class ThreadHandler extends Thread {

  /**
   * Gamessesion used for setting moves
   */
  private final GameSession gameSession;

  /**
   * Construcotr for setting the gamession
   *
   * @param gameSession session
   */
  public ThreadHandler(GameSession gameSession) {
    this.gameSession = gameSession;
  }

  /**
   * Run Method for thread
   */
  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()) {
      this.gameSession.getGame().makeMoveServer();
      //System.out.println("Next Move");
    }
    System.out.println("Interrupted");
  }
}
