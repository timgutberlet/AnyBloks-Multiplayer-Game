package game.controller;

import engine.controller.AbstractGameController;
import game.model.Game;
import game.model.GameSession;

/**
 * Class that is called, when a local game is played. The Abstract Gamecontroller class was
 * first thought to be used for multiple gamecontontroller, but we changed it after.
 *
 * @author tgutberl
 */
public class LocalGameUiController extends InGameUiController {

  /**
   * Constructor.
   *
   * @param gameController Gamecontroller class
   * @param game           Game input
   * @param gameSession    gameSession used for controlling game
   * @author tgutberl
   */
  public LocalGameUiController(AbstractGameController gameController,
      Game game, GameSession gameSession) {
    super(gameController, game, gameSession);
  }
}
