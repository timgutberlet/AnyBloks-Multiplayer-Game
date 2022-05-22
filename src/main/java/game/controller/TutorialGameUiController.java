package game.controller;

import engine.controller.AbstractGameController;
import game.model.Game;
import game.model.GameSession;
import java.util.ArrayList;

/**
 * Controller for controlling the tutorial.
 *
 * @author tgutberl
 */
public class TutorialGameUiController extends InGameUiController {

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
   *
   * @param gameController gamecontroler
   * @param gameSession    gamesession
   */
  public TutorialGameUiController(AbstractGameController gameController,
      Game game, GameSession gameSession) {
    super(gameController, game, gameSession);
    hints = new ArrayList<>();
  }
}
