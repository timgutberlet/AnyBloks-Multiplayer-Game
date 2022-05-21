package game.controller;

import engine.controller.AbstractGameController;
import game.model.Game;
import game.model.GameSession;

/**
 * Class that is called, when a local game is played.
 *
 * @author tgutberl
 */
public class LocalGameUiController extends InGameUiController {

  /**
   * Construcor
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
