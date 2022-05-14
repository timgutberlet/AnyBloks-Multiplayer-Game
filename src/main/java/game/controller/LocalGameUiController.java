package game.controller;

import engine.controller.AbstractGameController;
import game.model.Game;
import game.model.GameSession;

/**
 * @author tgutberl
 */
public class LocalGameUiController extends InGameUiController {

  public LocalGameUiController(AbstractGameController gameController,
      Game game, GameSession gameSession) {
    super(gameController, game, gameSession);
  }
}
