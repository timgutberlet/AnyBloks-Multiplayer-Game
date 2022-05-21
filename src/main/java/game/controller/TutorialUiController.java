package game.controller;

import engine.controller.AbstractGameController;
import engine.handler.ThreadHandler;
import game.model.GameSession;
import java.util.ArrayList;

/**
 *
 * Controller for controlling the tutorial.
 * @author tgutberl
 */
public class TutorialUiController extends InGameUiController {

  /**
   * Arraylist containing the Instructions.
   */
  private final ArrayList<String> hints;
  /**
   * Number of turn that got played.
   */
  private int turn;
  /**
   * Constructor.
   * @param gameController gamecontroler
   * @param gameSession gamesession
   * @param threadHelp threadhelp
   */
  public TutorialUiController(AbstractGameController gameController,
      GameSession gameSession, ThreadHandler threadHelp) {
    super(gameController, gameSession.getGame(), gameSession, threadHelp);
    hints = new ArrayList<>();
  }
}
