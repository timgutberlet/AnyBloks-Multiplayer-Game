package game.controller;

import engine.controller.AbstractGameController;
import game.model.GameSession;
import java.util.ArrayList;

/**
 * @author tgutberl
 */
public class TutorialUiController extends InGameUiController {

  /**
   * Arraylist containing the Instructions
   */
  private final ArrayList<String> hints;
  /**
   * Number of turn that got played
   */
  private int turn;

  public TutorialUiController(AbstractGameController gameController,
      GameSession gameSession) {
    super(gameController, gameSession.getGame(), gameSession);
    hints = new ArrayList<>();
  }
}
