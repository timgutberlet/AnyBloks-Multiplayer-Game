package game.controller;

import engine.controller.AbstractGameController;
import engine.handler.ThreadHandler;
import game.model.Game;
import game.model.GameSession;

/**
 * @author tgutberl
 */
public class LocalGameUiController extends InGameUiController {

  public LocalGameUiController(AbstractGameController gameController,
      Game game, GameSession gameSession, ThreadHandler threadHelp) {
    super(gameController, game, gameSession, threadHelp);
  }
}
