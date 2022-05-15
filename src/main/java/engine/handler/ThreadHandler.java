package engine.handler;

import game.model.GameSession;

/**
 * @author tgutberl
 * The Basic Thread can look like this
 *
 * Sollte im folgenden dann noch durch weitere Threadhandler erweiter werden, bitte aber lassen, bis eine Funktionierende Version mit Server steht
 */
public class ThreadHandler extends Thread{

  private GameSession gameSession;

  public ThreadHandler(GameSession gameSession){
    this.gameSession = gameSession;
  }
  @Override
  public void run() {
    while (!Thread.currentThread().isInterrupted()){
      this.gameSession.getGame().makeMove();
      System.out.println("Caluclating");
    }
    System.out.println("Interrupted");
  }
}
