package game.controller;

import engine.controller.AbstractGameController;
import engine.controller.AbstractUiController;
import javafx.scene.Group;

/**
 * Multiplayer Controller, to manage Hosting and Joining Games
 *
 * @author tgutberl
 */
public class MultiplayerUiController extends AbstractUiController {

  /**
   * @param gameController most recent Gamecontroller
   * @author tgutberl
   */
  public MultiplayerUiController(AbstractGameController gameController) {
    super(gameController);
  }

  @Override
  public void init(Group root) {

  }

  @Override
  public void update(AbstractGameController gameController) {

  }

  @Override
  public void onExit() {

  }
}
