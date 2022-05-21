package game.controller;

import engine.controller.AbstractGameController;
import engine.handler.ThreadHandler;
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
   * @param gameController  Gamecontroller class
   * @param game Game input
   * @param gameSession gameSession used for controlling game
   * @param threadHelp Thread for parallel input
   *
   * @author tgutberl
   */
  public LocalGameUiController(AbstractGameController gameController,
      Game game, GameSession gameSession, ThreadHandler threadHelp) {
    super(gameController, game, gameSession, threadHelp);
  }
}
